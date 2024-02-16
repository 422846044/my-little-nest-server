package fun.dfwh.nest.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import fun.dfwh.nest.async.AsyncService;
import fun.dfwh.nest.constant.GoodsId;
import fun.dfwh.nest.domain.FightLogInfo;
import fun.dfwh.nest.domain.FightUserInfo;
import fun.dfwh.nest.pojo.DjcResult;
import fun.dfwh.nest.service.DjcRequestService;
import fun.dfwh.nest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class DjcTask {

    @Autowired
    private DjcRequestService djcRequestService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private UserService userService;

    @Scheduled(cron="0 0 1 * * ?")
    public void timingTask(){
        signTask(null);
    }
    public boolean signTask(Integer account){
        //读取用户列表
        List<FightUserInfo> allFightUserInfo =new ArrayList<>(0);
        if(Objects.isNull(account)){
            allFightUserInfo.addAll(userService.getAllUserInfo());
        }else {
            allFightUserInfo.add(userService.getUserInfoByQQNum(account));
        }
        for (FightUserInfo fightUserInfo : allFightUserInfo) {
            Integer qqNum = fightUserInfo.getQqNum();
            String openId = fightUserInfo.getOpenId();
            String accessToken = fightUserInfo.getAccessToken();
            String deviceId = fightUserInfo.getDeviceId();
            //查询是否签到
            DjcResult queryIsSign = djcRequestService.commAmsAmeSvr(openId, "96938", accessToken,deviceId);
            log.info("查询是否签到任务执行结束,结果:[{}],消息:[{}]",queryIsSign.getDjcResult(),queryIsSign.getMsg());
            if(queryIsSign.getDjcResult()) {
                asyncService.addLog(new FightLogInfo(qqNum,"查询是否签到成功",new Date()));
                int signTime = 0;
                //提取签到信息
                JSONArray dataArr = queryIsSign.getJsonObject().getJSONObject("modRet").getJSONArray("data");
                signTime+=dataArr.size();
                String fupdateDate = "";
                if(signTime!=0){
                    //非当月第一次签到
                    JSONObject signDateInfo = dataArr.getJSONObject(dataArr.size() - 1);
                    fupdateDate = signDateInfo.getStr("FupdateDate");
                }
                //已签到结束流程
                if(!DateUtil.format(new Date(),"yyyy-MM-dd").equals(fupdateDate)){
                    //发送签到请求
                    DjcResult sign = djcRequestService.commAmsAmeSvr(openId, "96939", accessToken,deviceId);
                    log.info("签到任务执行结束,结果:[{}],消息:[{}]",sign.getDjcResult(),sign.getMsg());
                    //签到失败
                    if(!sign.getDjcResult()){
                        asyncService.addLog(new FightLogInfo(qqNum,"签到失败",new Date()));
                        log.info("签到失败");
                    }else {
                        //签到成功,领取奖励
                        signTime++;
                        asyncService.addLog(new FightLogInfo(qqNum,"签到成功",new Date()));
                        DjcResult receiveSignAward = djcRequestService.commAmsAmeSvr(openId, "324410", accessToken,deviceId);
                        log.info("签到奖励领取任务执行结束,结果:[{}],消息:[{}]",receiveSignAward.getDjcResult(),receiveSignAward.getMsg());
                    }
                }
                sleep(1000);
                //查询规则列表 判断是否有累计签到奖励
                DjcResult querySignRuleDjcResult = djcRequestService.querySignRule(openId,accessToken);
                log.info("查询累计签到奖励任务执行结束,结果:[{}],消息:[{}]",querySignRuleDjcResult.getDjcResult(),querySignRuleDjcResult.getMsg());
                if(querySignRuleDjcResult.getDjcResult()){
                    JSONArray data = querySignRuleDjcResult.getJsonObject().getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        int iCanUse = jsonObject.getInt("iCanUse");
                        String iFlowId = jsonObject.getStr("iFlowId");
                        String iDays = jsonObject.getStr("iDays");
                        String iAmount = jsonObject.getStr("iAmount");
                        String iRuleId = jsonObject.getStr("iRuleId");
                        if(1==iCanUse&&signTime>=Integer.parseInt(iDays)){
                            sleep(500);
                            //可领取
                            DjcResult DjcResult = djcRequestService.commAmsAmeSvr(openId, iFlowId, accessToken,deviceId);
                            log.info("领取累计签到奖励任务结束,结果[{}],消息[{}]",DjcResult.getDjcResult(),DjcResult.getMsg());
                            asyncService.addLog(new FightLogInfo(qqNum,"领取累计签到奖励任务结束,结果:["+DjcResult.getDjcResult()+"],消息:["
                                    + DjcResult.getMsg()+"]",new Date()));
                            if(DjcResult.getDjcResult()){
                                JSONObject modRet = DjcResult.getJsonObject().getJSONObject("modRet");
                                String iPackageNum = modRet.getStr("iPackageNum");
                                String sMsg = modRet.getStr("sMsg");
                                sMsg=sMsg.replace(" ",iPackageNum);
                                log.info(sMsg);
                            }
                        }
                    }
                }
            }else{
                //可能已过期  发送邮件通知
                MailUtil.send("422846044@qq.com","自动任务请求失败","自动任务请求失败",false);
                return false;
            }




            //间隔1秒
            sleep(500);
            //执行礼包达人任务
            //1.获取绑定列表roleCode roleName
            Map<String,String> roleInfoMap = djcRequestService.roleBindList(openId,accessToken);
            String roleCode = roleInfoMap.get("roleCode");
            String roleName = roleInfoMap.get("roleName");
            if("".equals(roleCode)){
                log.info("角色信息获取失败");
                asyncService.addLog(new FightLogInfo(qqNum,"角色信息获取失败",new Date()));
            }else{
                DjcResult packageReceive = djcRequestService.packageReceive(openId,accessToken,roleInfoMap,deviceId);
                log.info("礼包达人任务执行结束,结果:[{}],消息:[{}]",packageReceive.getDjcResult(),packageReceive.getMsg());
                asyncService.addLog(new FightLogInfo(qqNum,"执行礼包达人任务,结果:["+packageReceive.getDjcResult()+"],消息:["
                        + packageReceive.getMsg()+"]",new Date()));
            }
            //查询可兑换次数
            String actId = "2598";
            List<String> goodsIdList = Arrays.asList("123","121","119");
            for (String goodsId : goodsIdList) {
                int times = 0;
                for (int i = 0; i < 3; i++) {
                    DjcResult queryGoodTimeDjcResult = djcRequestService.queryGoodTime(openId,accessToken,actId,goodsId);
                    if(queryGoodTimeDjcResult.getDjcResult()) {
                        JSONArray list = queryGoodTimeDjcResult.getJsonObject().getJSONArray("list");
                        JSONObject useObject = list.getJSONObject(0);
                        int used = useObject.getInt("used");
                        if(used==0){
                            //第一次兑换
                            times=5;
                        }else{
                            times = useObject.getInt("left");
                        }
                        log.info("剩余次数[{}]",times);
                        break;
                    }
                }
                for (int i = 0; i < times; i++) {
                    sleep(1000);
                    //执行兑换有礼任务
                    DjcResult bugGoodDjcResult = djcRequestService.bugGood(openId,accessToken,actId,goodsId,roleCode,roleName);
                    log.info("兑换礼包任务执行完成,结果:[{}]",bugGoodDjcResult.getDjcResult());
                    if(bugGoodDjcResult.getDjcResult()){
                        log.info("兑换[{}]成功",bugGoodDjcResult.getJsonObject().getStr("goods_name"));
                        asyncService.addLog(new FightLogInfo(qqNum,"兑换["+bugGoodDjcResult.getJsonObject().getStr("goods_name")+"]成功",new Date()));
                    }
                }
            }

            sleep(1000);
            //每日打卡activity_detail
            DjcResult reportSignTask = djcRequestService.reportBrowseTask(openId,accessToken,"activity_center");
            log.info("完成每日打卡任务,结果:[{}]",reportSignTask.getDjcResult());
            asyncService.addLog(new FightLogInfo(qqNum,"完成每日打卡任务,结果:["+reportSignTask.getDjcResult()+"],消息:["
                    + reportSignTask.getMsg()+"]",new Date()));
            //每日3次浏览任务活动
            for (int i = 0; i < 3; i++) {
                sleep(1000);
                DjcResult reportBrowseTask = djcRequestService.reportBrowseTask(openId,accessToken,"activity_detail");
                log.info("完成每日浏览任务第{}次,结果:[{}]",i+1,reportBrowseTask.getDjcResult());
                asyncService.addLog(new FightLogInfo(qqNum,"完成每日浏览任务第"+(i+1)+"次,结果:["+reportBrowseTask.getDjcResult()+"],消息:["
                        + reportBrowseTask.getMsg()+"]",new Date()));
            }

            sleep(500);
            //查询任务列表
            DjcResult DjcResult = djcRequestService.queryTaskList(openId,accessToken);
            if(DjcResult.getDjcResult()){
                JSONObject data = DjcResult.getJsonObject().getJSONObject("data");
                JSONArray list = data.getJSONObject("list").getJSONArray("limit_time");
                if(list !=null&&list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject jsonObject = list.getJSONObject(i);
                        String iruleId = jsonObject.getStr("iruleId");
                        //限时任务
                        DjcResult limitTimeTask = djcRequestService.limitTimeTask(openId,accessToken,iruleId);
                        log.info("限时任务执行结束,结果[{}]",limitTimeTask.getDjcResult());
                        asyncService.addLog(new FightLogInfo(qqNum,"执行限时任务["+iruleId+"],结果:["+limitTimeTask.getDjcResult()+"],消息:["
                                + limitTimeTask.getMsg()+"]",new Date()));
                    }
                }
            }
            //有理想任务
            //查询是否已许愿道具 存在则删除后再许愿
            int currentPage = 1;
            while (true){
                DjcResult queryUserDemandsDjcResult = djcRequestService.queryUserDemands(openId,accessToken,currentPage);
                log.info("查询心愿任务结束,结果[{}],消息:[{}]",queryUserDemandsDjcResult.getDjcResult(),queryUserDemandsDjcResult.getMsg());
                if(queryUserDemandsDjcResult.getDjcResult()){
                    JSONObject data = queryUserDemandsDjcResult.getJsonObject().getJSONObject("data");
                    Integer total = data.getInt("total");
                    if(total==0) break;
                    JSONArray list = data.getJSONArray("list");
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = list.getJSONObject(i);
                        String sKeyId = jsonObject.getStr("sKeyId");
                        //26436
                        String iGoodsId = jsonObject.getStr("iGoodsId");
                        if(GoodsId.GAI_MING_KA.equals(iGoodsId)){
                            //删除道具
                            DjcResult delDemandDjcResult = djcRequestService.delDemand(openId,accessToken,sKeyId);
                            log.info("心愿删除任务结束,结果[{}],消息:[{}]",delDemandDjcResult.getDjcResult(),delDemandDjcResult.getMsg());
                            asyncService.addLog(new FightLogInfo(qqNum,"执行心愿删除任务,结果:["+delDemandDjcResult.getDjcResult()+"],消息:["
                                    + delDemandDjcResult.getMsg()+"]",new Date()));
                            break;
                        }
                    }
                    if(size*currentPage<total){
                        currentPage++;
                    }else {
                        break;
                    }
                }else{
                    break;
                }
            }
            //间隔1秒
            sleep(1000);
            //许愿
            if(!"".equals(roleCode)){
                DjcResult addDemandDjcResult = djcRequestService.addDemand(openId,accessToken,GoodsId.GAI_MING_KA,roleInfoMap);
                log.info("添加心愿任务结束,结果[{}],消息:[{}]",addDemandDjcResult.getDjcResult(),addDemandDjcResult.getMsg());
                asyncService.addLog(new FightLogInfo(qqNum,"执行添加心愿任务,结果:["+addDemandDjcResult.getDjcResult()+"],消息:["
                        + addDemandDjcResult.getMsg()+"]",new Date()));
            }

            //间隔5秒
            sleep(5000);

            //领取任务奖励
            //查询任务列表
            List<String> taskRuleIdList = new ArrayList<>();
            List<String> chestRuleIdList = new ArrayList<>();
            sleep(2000);
            DjcResult queryTaskListDjcResult = djcRequestService.queryTaskList(openId,accessToken);
            log.info("查询任务列表任务结束,结果[{}],消息:[{}]",queryTaskListDjcResult.getDjcResult(),queryTaskListDjcResult.getMsg());
            if(queryTaskListDjcResult.getDjcResult()){
                JSONObject data = queryTaskListDjcResult.getJsonObject().getJSONObject("data");
                JSONObject list = data.getJSONObject("list");
                JSONArray limitTime = list.getJSONArray("limit_time");
                if(limitTime !=null&&limitTime.size()>0){
                    for (int i = 0; i < limitTime.size(); i++) {
                        JSONObject jsonObject = limitTime.getJSONObject(i);
                        Integer iStatus = jsonObject.getInt("iStatus");
                        if(1==iStatus){
                            String sTask = jsonObject.getStr("sTask");
                            log.info("任务已完成:[{}]",sTask);
                            asyncService.addLog(new FightLogInfo(qqNum,"任务已完成:"+sTask,new Date()));
                            String iruleId = jsonObject.getStr("iruleId");
                            taskRuleIdList.add(iruleId);
                        }
                    }
                }
                JSONArray day = list.getJSONArray("day");
                if(day !=null&&day.size()>0){
                    for (int i = 0; i < day.size(); i++) {
                        JSONObject jsonObject = day.getJSONObject(i);
                        Integer iStatus = jsonObject.getInt("iStatus");
                        if(1==iStatus){
                            String sTask = jsonObject.getStr("sTask");
                            log.info("任务已完成:[{}]",sTask);
                            asyncService.addLog(new FightLogInfo(qqNum,"任务已完成:"+sTask,new Date()));
                            String iruleId = jsonObject.getStr("iruleId");
                            taskRuleIdList.add(iruleId);
                        }
                    }
                }
                JSONObject chestList = data.getJSONObject("chest_list");
                if(chestList !=null){
                    for (Map.Entry<String, Object> entry : chestList) {
                        String key = entry.getKey();
                        chestRuleIdList.add(key);
                    }
                }
            }
            //领取任务奖励
            for (String iRuleId : taskRuleIdList) {
                sleep(500);
                DjcResult getTaskAwardDjcResult = djcRequestService.getTaskAward(openId,accessToken,iRuleId);
                log.info("领取每日任务奖励任务结束,结果[{}],消息[{}]",getTaskAwardDjcResult.getDjcResult(),getTaskAwardDjcResult.getMsg());
                if(getTaskAwardDjcResult.getDjcResult()){
                    log.info("任务奖励领取成功:[iRuleId:{}]",iRuleId);
                    asyncService.addLog(new FightLogInfo(qqNum,"任务奖励领取成功:[iRuleId:"+iRuleId+"]",new Date()));
                }else {
                    log.info("任务奖励领取失败:[iRuleId:{}]",iRuleId);
                    asyncService.addLog(new FightLogInfo(qqNum,"任务奖励领取失败:[iRuleId:"+iRuleId+"]",new Date()));
                }
            }
            //领取活跃宝箱
            for (String ruleId : chestRuleIdList) {
                sleep(500);
                DjcResult getTaskAwardDjcResult = djcRequestService.getTaskAward(openId,accessToken,ruleId);
                log.info("领取每日活跃宝箱任务结束,结果[{}],消息[{}]",getTaskAwardDjcResult.getDjcResult(),getTaskAwardDjcResult.getMsg());
                if(getTaskAwardDjcResult.getDjcResult()){
                    log.info("活跃宝箱领取成功:[iRuleId:{}]",ruleId);
                    asyncService.addLog(new FightLogInfo(qqNum,"活跃宝箱领取成功:[iRuleId:"+ruleId+"]",new Date()));
                }else {
                    log.info("活跃宝箱领取失败:[iRuleId:{}]",ruleId);
                    asyncService.addLog(new FightLogInfo(qqNum,"活跃宝箱领取失败:[iRuleId:"+ruleId+"]",new Date()));
                }
            }

            int page = 1;
            int total=0;
            //查询今日聚豆记录
            while (true){
                DjcResult JdDetaislDjcResult = djcRequestService.queryJdDetails(openId,accessToken,Integer.toString(page));
                log.info("查询今日聚豆明细任务结束,结果[{}],消息[{}]",JdDetaislDjcResult.getDjcResult(),JdDetaislDjcResult.getMsg());
                if(JdDetaislDjcResult.getDjcResult()){
                    JSONObject jsonObject = JdDetaislDjcResult.getJsonObject();
                    JSONObject data = jsonObject.getJSONObject("data");
                    Integer totalPage = data.getInt("totalPage");
                    JSONArray dataList = data.getJSONArray("data");
                    int size = dataList.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject details = dataList.getJSONObject(i);
                        String externTranType = details.getStr("ExternTranType");
                        String tranAmt = details.getStr("TranAmt");
                        if("1".equals(externTranType)){
                            //收入
                            total=total+Integer.parseInt(tranAmt);
                        }else {
                            //支出
                            total=total-Integer.parseInt(tranAmt);
                        }
                    }
                    if(page==totalPage){
                        log.info("今日聚豆总计:[{}]",total);
                        asyncService.addLog(new FightLogInfo(qqNum,"今日聚豆总计:["+total+"]",new Date()));
                        break;
                    }
                    page++;
                    sleep(1000);
                }else {
                    asyncService.addLog(new FightLogInfo(qqNum,"今日聚豆总计查询失败:["+JdDetaislDjcResult.getMsg()+"]",new Date()));
                    break;
                }
            }

        }
        //String openId="44FE831769B1CF9FEA8A291A1842CF81",accessToken="8DE4ADE624AADE34FFC444091ADF944F";
        //String openId="DECC4DC6C39B1FC86E109B26F31C9105",accessToken="5F3B21973D80F3989EFD10F4B007F10B";
        //Integer qqNum = 353816033;

        return false;
    }

    public boolean test(){
        asyncService.addLog(new FightLogInfo(324525,"432",new Date()));
        return true;
    }

    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("线程睡眠出错:{}",e);
        }
    }


}
