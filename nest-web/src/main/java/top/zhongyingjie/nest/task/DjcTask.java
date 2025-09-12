package top.zhongyingjie.nest.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zhongyingjie.common.annotation.IgnoreParameterNumber;
import top.zhongyingjie.nest.async.AsyncService;
import top.zhongyingjie.nest.constant.GoodsId;
import top.zhongyingjie.nest.domain.FightLogInfo;
import top.zhongyingjie.nest.domain.FightUserInfo;
import top.zhongyingjie.nest.pojo.DjcResult;
import top.zhongyingjie.nest.service.DjcRequestService;
import top.zhongyingjie.nest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 道具城任务
 *
 * @author Kong
 */
@Component
public class DjcTask {

    private static final Logger log = LoggerFactory.getLogger(DjcTask.class);

    private static final int WAIT_TIME_5 = 500;

    private static final int WAIT_TIME_10 = 1000;

    private static final int WAIT_TIME_20 = 2000;

    private static final int WAIT_TIME_50 = 5000;

    private static final int RETRY_NUMBER = 3;

    private static final int BROWSE_NUMBER = 3;

    private static final int GOODS_NUMBER = 3;

    @Autowired
    private DjcRequestService djcRequestService;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private UserService userService;

    /**
     * 定时任务
     */
    //@Scheduled(cron="0 0 1 * * ?")
    public void timingTask() {
        signTask(null);
    }

    /**
     * 任务流
     *
     * @param account 账号
     * @return 是否执行完毕
     */
    public boolean signTask(Integer account) {
        // 读取用户列表
        List<FightUserInfo> allFightUserInfo = new ArrayList<>(0);
        if (Objects.isNull(account)) {
            allFightUserInfo.addAll(userService.getAllUserInfo());
        } else {
            allFightUserInfo.add(userService.getUserInfoByQQNum(account));
        }
        for (FightUserInfo fightUserInfo : allFightUserInfo) {
            Integer qqNum = fightUserInfo.getQqNum();
            String openId = fightUserInfo.getOpenId();
            String accessToken = fightUserInfo.getAccessToken();
            String deviceId = fightUserInfo.getDeviceId();
            // 查询是否签到
            querySign(openId, accessToken, qqNum, deviceId);
            // 间隔1秒
            sleep(WAIT_TIME_5);
            //执行礼包达人任务
            // 1.获取绑定列表roleCode roleName
            Map<String, String> roleInfoMap = djcRequestService.roleBindList(openId, accessToken);
            String roleCode = roleInfoMap.get("roleCode");
            String roleName = roleInfoMap.get("roleName");
            doPackageReceive(openId, accessToken, deviceId, qqNum, roleInfoMap, roleCode);
            bugGoodDjc(openId, accessToken, qqNum, roleCode, roleName);
            sleep(WAIT_TIME_10);
            doReportAndBrowse(openId, accessToken, qqNum);
            sleep(WAIT_TIME_5);
            doLimitTask(openId, accessToken, qqNum);
            //有理想任务
            doDemandTask(openId, accessToken, qqNum, roleCode, roleInfoMap);
            //间隔5秒
            sleep(WAIT_TIME_50);
            //领取任务奖励
            //查询任务列表
            List<String> taskRuleIdList = new ArrayList<>();
            List<String> chestRuleIdList = new ArrayList<>();
            sleep(WAIT_TIME_20);
            queryTaskList(taskRuleIdList, chestRuleIdList, openId, accessToken, qqNum);
            getTaskAward(taskRuleIdList, chestRuleIdList, openId, accessToken, qqNum);
            queryJD(openId, accessToken, qqNum);
        }
        return false;
    }

    private void doLimitTask(String openId, String accessToken, Integer qqNum) {
        // 查询任务列表
        DjcResult djcResult = djcRequestService.queryTaskList(openId, accessToken);
        if (djcResult.getDjcResult()) {
            JSONObject data = djcResult.getJsonObject().getJSONObject("data");
            JSONArray list = data.getJSONObject("list").getJSONArray("limit_time");
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jsonObject = list.getJSONObject(i);
                    String iruleId = jsonObject.getStr("iruleId");
                    //限时任务
                    DjcResult limitTimeTask = djcRequestService.limitTimeTask(openId, accessToken, iruleId);
                    log.info("限时任务执行结束,结果[{}]", limitTimeTask.getDjcResult());
                    asyncService.addLog(new FightLogInfo(qqNum,
                            "执行限时任务[" + iruleId + "],结果:[" + limitTimeTask.getDjcResult() + "],消息:["
                                    + limitTimeTask.getMsg() + "]", new Date()));
                }
            }
        }
    }

    private void queryJD(String openId, String accessToken, Integer qqNum) {
        int page = 1;
        int total = 0;
        //查询今日聚豆记录
        while (true) {
            DjcResult jdDetaislDjcResult = djcRequestService
                    .queryJdDetails(openId, accessToken, Integer.toString(page));
            log.info("查询今日聚豆明细任务结束,结果[{}],消息[{}]", jdDetaislDjcResult.getDjcResult(), jdDetaislDjcResult.getMsg());
            if (jdDetaislDjcResult.getDjcResult()) {
                JSONObject jsonObject = jdDetaislDjcResult.getJsonObject();
                JSONObject data = jsonObject.getJSONObject("data");
                Integer totalPage = data.getInt("totalPage");
                JSONArray dataList = data.getJSONArray("data");
                int size = dataList.size();
                for (int i = 0; i < size; i++) {
                    JSONObject details = dataList.getJSONObject(i);
                    String externTranType = details.getStr("ExternTranType");
                    String tranAmt = details.getStr("TranAmt");
                    if ("1".equals(externTranType)) {
                        //收入
                        total = total + Integer.parseInt(tranAmt);
                    } else {
                        //支出
                        total = total - Integer.parseInt(tranAmt);
                    }
                }
                if (page == totalPage) {
                    log.info("今日聚豆总计:[{}]", total);
                    asyncService.addLog(new FightLogInfo(qqNum, "今日聚豆总计:[" + total + "]", new Date()));
                    break;
                }
                page++;
                sleep(WAIT_TIME_10);
            } else {
                asyncService.addLog(new FightLogInfo(qqNum,
                        "今日聚豆总计查询失败:[" + jdDetaislDjcResult.getMsg() + "]", new Date()));
                break;
            }
        }
    }

    private void getTaskAward(List<String> taskRuleIdList,
                              List<String> chestRuleIdList, String openId, String accessToken, Integer qqNum) {
        //领取任务奖励
        for (String iRuleId : taskRuleIdList) {
            sleep(WAIT_TIME_5);
            DjcResult getTaskAwardDjcResult = djcRequestService.getTaskAward(openId, accessToken, iRuleId);
            log.info("领取每日任务奖励任务结束,结果[{}],消息[{}]",
                    getTaskAwardDjcResult.getDjcResult(), getTaskAwardDjcResult.getMsg());
            if (getTaskAwardDjcResult.getDjcResult()) {
                log.info("任务奖励领取成功:[iRuleId:{}]", iRuleId);
                asyncService.addLog(new FightLogInfo(qqNum, "任务奖励领取成功:[iRuleId:" + iRuleId + "]", new Date()));
            } else {
                log.info("任务奖励领取失败:[iRuleId:{}]", iRuleId);
                asyncService.addLog(new FightLogInfo(qqNum, "任务奖励领取失败:[iRuleId:" + iRuleId + "]", new Date()));
            }
        }
        //领取活跃宝箱
        for (String ruleId : chestRuleIdList) {
            sleep(WAIT_TIME_5);
            DjcResult getTaskAwardDjcResult = djcRequestService.getTaskAward(openId, accessToken, ruleId);
            log.info("领取每日活跃宝箱任务结束,结果[{}],消息[{}]",
                    getTaskAwardDjcResult.getDjcResult(), getTaskAwardDjcResult.getMsg());
            if (getTaskAwardDjcResult.getDjcResult()) {
                log.info("活跃宝箱领取成功:[iRuleId:{}]", ruleId);
                asyncService.addLog(new FightLogInfo(qqNum, "活跃宝箱领取成功:[iRuleId:" + ruleId + "]", new Date()));
            } else {
                log.info("活跃宝箱领取失败:[iRuleId:{}]", ruleId);
                asyncService.addLog(new FightLogInfo(qqNum, "活跃宝箱领取失败:[iRuleId:" + ruleId + "]", new Date()));
            }
        }
    }

    private void queryTaskList(List<String> taskRuleIdList, List<String> chestRuleIdList,
                               String openId, String accessToken, Integer qqNum) {
        DjcResult queryTaskListDjcResult = djcRequestService.queryTaskList(openId, accessToken);
        log.info("查询任务列表任务结束,结果[{}],消息:[{}]", queryTaskListDjcResult.getDjcResult(), queryTaskListDjcResult.getMsg());
        if (queryTaskListDjcResult.getDjcResult()) {
            JSONObject data = queryTaskListDjcResult.getJsonObject().getJSONObject("data");
            JSONObject list = data.getJSONObject("list");
            JSONArray limitTime = list.getJSONArray("limit_time");
            if (limitTime != null && limitTime.size() > 0) {
                for (int i = 0; i < limitTime.size(); i++) {
                    JSONObject jsonObject = limitTime.getJSONObject(i);
                    Integer iStatus = jsonObject.getInt("iStatus");
                    if (1 == iStatus) {
                        String sTask = jsonObject.getStr("sTask");
                        log.info("任务已完成[{}]", sTask);
                        asyncService.addLog(new FightLogInfo(qqNum, "任务已完成" + sTask, new Date()));
                        String iruleId = jsonObject.getStr("iruleId");
                        taskRuleIdList.add(iruleId);
                    }
                }
            }
            JSONArray day = list.getJSONArray("day");
            if (day != null && day.size() > 0) {
                for (int i = 0; i < day.size(); i++) {
                    JSONObject jsonObject = day.getJSONObject(i);
                    Integer iStatus = jsonObject.getInt("iStatus");
                    if (1 == iStatus) {
                        String sTask = jsonObject.getStr("sTask");
                        log.info("任务已完成[{}]", sTask);
                        asyncService.addLog(new FightLogInfo(qqNum, "任务已完成" + sTask, new Date()));
                        String iruleId = jsonObject.getStr("iruleId");
                        taskRuleIdList.add(iruleId);
                    }
                }
            }
            JSONObject chestList = data.getJSONObject("chest_list");
            if (chestList != null) {
                for (Map.Entry<String, Object> entry : chestList) {
                    String key = entry.getKey();
                    chestRuleIdList.add(key);
                }
            }
        }
    }

    private void doDemandTask(String openId, String accessToken,
                              Integer qqNum, String roleCode, Map<String, String> roleInfoMap) {
        //查询是否已许愿道具,存在则删除后再许愿
        int currentPage = 1;
        while (true) {
            DjcResult queryUserDemandsDjcResult = djcRequestService.queryUserDemands(openId, accessToken, currentPage);
            log.info("查询心愿任务结束,结果[{}],消息:[{}]", queryUserDemandsDjcResult.getDjcResult(),
                    queryUserDemandsDjcResult.getMsg());
            if (queryUserDemandsDjcResult.getDjcResult()) {
                JSONObject data = queryUserDemandsDjcResult.getJsonObject().getJSONObject("data");
                Integer total = data.getInt("total");
                if (total == 0) {
                    break;
                }
                JSONArray list = data.getJSONArray("list");
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = list.getJSONObject(i);
                    String sKeyId = jsonObject.getStr("sKeyId");
                    String iGoodsId = jsonObject.getStr("iGoodsId");
                    if (GoodsId.GAI_MING_KA.equals(iGoodsId)) {
                        //删除道具
                        DjcResult delDemandDjcResult = djcRequestService.delDemand(openId, accessToken, sKeyId);
                        log.info("心愿删除任务结束,结果[{}],消息:[{}]",
                                delDemandDjcResult.getDjcResult(), delDemandDjcResult.getMsg());
                        asyncService.addLog(new FightLogInfo(qqNum,
                                "执行心愿删除任务,结果:[" + delDemandDjcResult.getDjcResult() + "],消息:["
                                        + delDemandDjcResult.getMsg() + "]", new Date()));
                        break;
                    }
                }
                if (size * currentPage < total) {
                    currentPage++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        sleep(WAIT_TIME_10);
        //许愿
        if (!"".equals(roleCode)) {
            DjcResult addDemandDjcResult = djcRequestService
                    .addDemand(openId, accessToken, GoodsId.GAI_MING_KA, roleInfoMap);
            log.info("添加心愿任务结束,结果[{}],消息:[{}]", addDemandDjcResult.getDjcResult(), addDemandDjcResult.getMsg());
            asyncService.addLog(new FightLogInfo(qqNum,
                    "执行添加心愿任务,结果:[" + addDemandDjcResult.getDjcResult() + "],消息:["
                            + addDemandDjcResult.getMsg() + "]", new Date()));
        }
    }

    private void querySign(String openId, String accessToken, Integer qqNum, String deviceId) {
        DjcResult queryIsSign = djcRequestService.commAmsAmeSvr(openId, "96938", accessToken, deviceId);
        log.info("查询是否签到任务执行结束,结果:[{}],消息:[{}]", queryIsSign.getDjcResult(), queryIsSign.getMsg());
        if (queryIsSign.getDjcResult()) {
            doSign(qqNum, queryIsSign, openId, accessToken, deviceId);
        } else {
            //可能已过期 发送邮件通知
            MailUtil.send("422846044@qq.com", "自动任务请求失败", "自动任务请求失败", false);
            throw new RuntimeException("执行失败");
        }
    }

    private void doReportAndBrowse(String openId, String accessToken, Integer qqNum) {
        //每日打卡activity_detail
        DjcResult reportSignTask = djcRequestService.reportBrowseTask(openId, accessToken, "activity_center");
        log.info("完成每日打卡任务,结果:[{}]", reportSignTask.getDjcResult());
        asyncService.addLog(new FightLogInfo(qqNum, "完成每日打卡任务,结果:[" + reportSignTask.getDjcResult() + "],消息:["
                + reportSignTask.getMsg() + "]", new Date()));
        //每日3次浏览任务活动
        for (int i = 0; i < RETRY_NUMBER; i++) {
            sleep(WAIT_TIME_10);
            DjcResult reportBrowseTask = djcRequestService.reportBrowseTask(openId, accessToken, "activity_detail");
            log.info("完成每日浏览任务第{}次,结果:[{}]", i + 1, reportBrowseTask.getDjcResult());
            asyncService.addLog(new FightLogInfo(qqNum,
                    "完成每日浏览任务第" + (i + 1) + "次,结果:[" + reportBrowseTask.getDjcResult() + "],消息:["
                    + reportBrowseTask.getMsg() + "]", new Date()));
        }
    }

    private void bugGoodDjc(String openId, String accessToken, Integer qqNum, String roleCode, String roleName) {
        //查询可兑换次数
        String actId = "2598";
        List<String> goodsIdList = Arrays.asList("123", "121", "119");
        for (String goodsId : goodsIdList) {
            int times = 0;
            for (int i = 0; i < BROWSE_NUMBER; i++) {
                DjcResult queryGoodTimeDjcResult = djcRequestService
                        .queryGoodTime(openId, accessToken, actId, goodsId);
                if (queryGoodTimeDjcResult.getDjcResult()) {
                    JSONArray list = queryGoodTimeDjcResult.getJsonObject().getJSONArray("list");
                    JSONObject useObject = list.getJSONObject(0);
                    int used = useObject.getInt("used");
                    if (used == 0) {
                        //第一次兑换
                        times = GOODS_NUMBER;
                    } else {
                        times = useObject.getInt("left");
                    }
                    log.info("剩余次数[{}]", times);
                    break;
                }
            }
            for (int i = 0; i < times; i++) {
                sleep(WAIT_TIME_10);
                //执行兑换有礼任务
                DjcResult bugGoodDjcResult = djcRequestService
                        .bugGood(openId, accessToken, actId, goodsId, roleCode, roleName);
                log.info("兑换礼包任务执行完成,结果:[{}]", bugGoodDjcResult.getDjcResult());
                if (bugGoodDjcResult.getDjcResult()) {
                    log.info("兑换[{}]成功", bugGoodDjcResult.getJsonObject().getStr("goods_name"));
                    asyncService.addLog(new FightLogInfo(qqNum,
                            "兑换[" + bugGoodDjcResult.getJsonObject().getStr("goods_name") + "]成功", new Date()));
                }
            }
        }

    }

    @IgnoreParameterNumber
    private void doPackageReceive(String openId, String accessToken,
                                  String deviceId, Integer qqNum, Map<String, String> roleInfoMap, String roleCode) {
        if ("".equals(roleCode)) {
            log.info("角色信息获取失败");
            asyncService.addLog(new FightLogInfo(qqNum, "角色信息获取失败", new Date()));
        } else {
            DjcResult packageReceive = djcRequestService.packageReceive(openId, accessToken, roleInfoMap, deviceId);
            log.info("礼包达人任务执行结束,结果:[{}],消息:[{}]", packageReceive.getDjcResult(), packageReceive.getMsg());
            asyncService.addLog(new FightLogInfo(qqNum, "执行礼包达人任务,结果:[" + packageReceive.getDjcResult() + "],消息:["
                    + packageReceive.getMsg() + "]", new Date()));
        }
    }

    private void doSign(Integer qqNum, DjcResult queryIsSign, String openId, String accessToken, String deviceId) {
        asyncService.addLog(new FightLogInfo(qqNum, "查询是否签到成功", new Date()));
        int signTime = 0;
        //提取签到信息
        JSONArray dataArr = queryIsSign.getJsonObject().getJSONObject("modRet").getJSONArray("data");
        signTime += dataArr.size();
        String fupdateDate = "";
        if (signTime != 0) {
            //非当月第一次签到
            JSONObject signDateInfo = dataArr.getJSONObject(dataArr.size() - 1);
            fupdateDate = signDateInfo.getStr("FupdateDate");
        }
        //已签到结束流程
        if (!DateUtil.format(new Date(), "yyyy-MM-dd").equals(fupdateDate)) {
            //发送签到请求
            DjcResult sign = djcRequestService.commAmsAmeSvr(openId, "96939", accessToken, deviceId);
            log.info("签到任务执行结束,结果:[{}],消息:[{}]", sign.getDjcResult(), sign.getMsg());
            //签到失败
            if (!sign.getDjcResult()) {
                asyncService.addLog(new FightLogInfo(qqNum, "签到失败", new Date()));
                log.info("签到失败");
            } else {
                //签到成功,领取奖励
                signTime++;
                asyncService.addLog(new FightLogInfo(qqNum, "签到成功", new Date()));
                DjcResult receiveSignAward = djcRequestService.commAmsAmeSvr(openId, "324410", accessToken, deviceId);
                log.info("签到奖励领取任务执行结束,结果:[{}],消息:[{}]", receiveSignAward.getDjcResult(), receiveSignAward.getMsg());
            }
        }
        sleep(WAIT_TIME_10);
        getSignAward(openId, accessToken, deviceId, qqNum, signTime);
    }

    private void getSignAward(String openId, String accessToken, String deviceId, Integer qqNum, int signTime) {
        //查询规则列表 判断是否有累计签到奖励
        DjcResult querySignRuleDjcResult = djcRequestService.querySignRule(openId, accessToken);
        log.info("查询累计签到奖励任务执行结束,结果:[{}],消息:[{}]",
                querySignRuleDjcResult.getDjcResult(), querySignRuleDjcResult.getMsg());
        if (querySignRuleDjcResult.getDjcResult()) {
            JSONArray data = querySignRuleDjcResult.getJsonObject().getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                int iCanUse = jsonObject.getInt("iCanUse");
                String iFlowId = jsonObject.getStr("iFlowId");
                String iDays = jsonObject.getStr("iDays");
                if (1 == iCanUse && signTime >= Integer.parseInt(iDays)) {
                    sleep(WAIT_TIME_5);
                    //可领取
                    DjcResult djcResult = djcRequestService.commAmsAmeSvr(openId, iFlowId, accessToken, deviceId);
                    log.info("领取累计签到奖励任务结束,结果[{}],消息[{}]", djcResult.getDjcResult(), djcResult.getMsg());
                    asyncService.addLog(new FightLogInfo(qqNum,
                            "领取累计签到奖励任务结束,结果:[" + djcResult.getDjcResult() + "],消息:["
                            + djcResult.getMsg() + "]", new Date()));
                    if (djcResult.getDjcResult()) {
                        JSONObject modRet = djcResult.getJsonObject().getJSONObject("modRet");
                        String iPackageNum = modRet.getStr("iPackageNum");
                        String sMsg = modRet.getStr("sMsg");
                        sMsg = sMsg.replace(" ", iPackageNum);
                        log.info(sMsg);
                    }
                }
            }
        }
    }

    /**
     * 线程睡眠任务，模拟等待
     *
     * @param time 时长
     */
    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("线程睡眠出错:{}", e.getMessage(), e);
        }
    }


}
