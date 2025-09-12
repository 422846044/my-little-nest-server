package top.zhongyingjie.nest.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zhongyingjie.common.annotation.IgnoreParameterNumber;
import top.zhongyingjie.nest.constant.SignRuleId;
import top.zhongyingjie.nest.dao.DjcAppProperties;
import top.zhongyingjie.nest.entity.Phone;
import top.zhongyingjie.nest.pojo.DjcResult;
import top.zhongyingjie.nest.utils.DjcSignUtil;
import top.zhongyingjie.nest.utils.DjcTokenUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 道具城请求服务
 *
 * @author Kong
 */
@Service
public class DjcRequestService {

    private static final Logger log = LoggerFactory.getLogger(DjcRequestService.class);

    @Autowired
    private DjcAppProperties djcAppProperties;

    private static final int MAX_ONE_DIGIT_MONTH = 9;

    /**
     * 向commAmsAmeSvr服务请求
     *
     * @param openId      openId
     * @param iFlowId     isFlowId
     * @param accessToken 访问令牌
     * @param deviceId    设备id
     * @return 请求结果
     */
    public DjcResult commAmsAmeSvr(String openId, String iFlowId, String accessToken, String deviceId) {
        Phone phone = new Phone(deviceId);
        String commAmeAmsUrl = djcAppProperties.getCommAmeAmsUrl(phone);
        String djcSign = DjcSignUtil.getDjcSign(openId, djcAppProperties.getAppVersion(), phone.getSDeviceID());
        String cookie = getCookieFromProperties(openId, accessToken);
        Map<String, String> paramMap = getParamMap(iFlowId, djcSign, phone);
        String linkStr = "";
        DateTime date = DateUtil.date();
        int month = date.month() + 1;
        if (month <= MAX_ONE_DIGIT_MONTH) {
            linkStr = "0";
        }
        String monthStr = date.year() + linkStr + month;
        paramMap.put("month", monthStr);
        paramMap.put("osVersion", phone.getOsVersion());
        paramMap.put("sign_version", djcAppProperties.getSignVersion());
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        StringBuilder content = new StringBuilder();
        for (String key : paramMap.keySet()) {
            content.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        content.deleteCharAt(content.length() - 1);
        RequestBody body = RequestBody.create(mediaType, content.toString());
        Request request = new Request.Builder()
                .url(commAmeAmsUrl)
                .method("POST", body)
                .addHeader("Cookie", cookie)
                .addHeader("User-Agent", "TencentDaojucheng=v4.7.1.0&appSource=android&appVersion=147"
                        + "&ch=10000&sDeviceID=832ed1ab5036d8bec44e4cc6c12c439d7544bb25eab1ffa9acec1abe6bd913ca"
                        + "&firmwareVersion=13&phoneBrand=Xiaomi&phoneVersion=2201122C&displayMetrics=1440 * 3036"
                        + "&cpu=0&net=wifi&sVersionName=v4.7.1.0&plNo=152")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "comm.ams.game.qq.com")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String bodyStr = response.body().string();
            JSONObject jsonObject = JSONUtil.parseObj(bodyStr);
            String ret = jsonObject.getStr("ret");
            log.info("消息返回:[{}]", jsonObject.toString());
            String msg = jsonObject.getJSONObject("modRet").getStr("sMsg");
            return DjcResult.msg(msg).djcResult("0".equals(ret)).jsonObject(jsonObject);
        } catch (Exception e) {
            log.error("commAmsAmeSvr请求发生错误:[{}]", e.getMessage(), e);
            return DjcResult.requestFail();
        }
    }

    @NotNull
    private Map<String, String> getParamMap(String iFlowId, String djcSign, Phone phone) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appVersion", djcAppProperties.getAppVersion());
        paramMap.put("appSource", djcAppProperties.getAppSource());
        paramMap.put("g_tk", DjcTokenUtil.getGToken());
        paramMap.put("iFlowId", iFlowId);
        paramMap.put("sServiceType", djcAppProperties.getSServiceType());
        paramMap.put("sServiceDepartment", djcAppProperties.getSServiceDepartment());
        paramMap.put("iActivityId", djcAppProperties.getIActivityId());
        paramMap.put("sDjcSign", djcSign);
        paramMap.put("sDeviceID", phone.getSDeviceID());
        return paramMap;
    }

    @NotNull
    private String getCookieFromProperties(String openId, String accessToken) {
        String cookie = "djc_appSource=" + djcAppProperties.getAppSource() + "; "
                + "djc_appVersion=" + djcAppProperties.getAppVersion() + "; "
                + "acctype=qc; "
                + "appid=1101958653; "
                + "openid=" + openId + "; "
                + "access_token=" + accessToken;
        return cookie;
    }

    /**
     * 发送领取礼包请求
     *
     * @param openId      openId
     * @param accessToken 访问凭证
     * @param roleInfoMap 角色信息映射表
     * @param deviceId    设备id
     * @return 请求结果
     */
    public DjcResult packageReceive(String openId, String accessToken,
                                    Map<String, String> roleInfoMap, String deviceId) {
        String roleCode = roleInfoMap.get("roleCode");
        String roleName = roleInfoMap.get("roleName");
        String partition = roleInfoMap.get("partition");
        String systemID = roleInfoMap.get("systemID");
        String channelID = roleInfoMap.get("channelID");
        String channelKey = roleInfoMap.get("channelKey");
        Phone phone = new Phone(deviceId);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.package.receive");
        paramMap.put("iAppId", djcAppProperties.getAppId());
        paramMap.put("_app_id", djcAppProperties.getAppId());
        paramMap.put("output_format", "json");
        paramMap.put("optype", "receive_usertask_game");
        String ruleId = SignRuleId.RULE_ID[DateUtil.thisDayOfWeek()];
        paramMap.put("iruleId", ruleId);
        paramMap.put("bizcode", "yxzj");
        paramMap.put("_biz_code", "yxzj");
        paramMap.put("roleCode", roleCode);
        paramMap.put("systemID", systemID);
        paramMap.put("channelID", channelID);
        paramMap.put("sRoleName", roleName);
        paramMap.put("sPartition", partition);
        paramMap.put("channelKey", channelKey);
        paramMap.put("sDeviceID", phone.getSDeviceID());
        paramMap.put("appVersion", djcAppProperties.getAppVersion());
        paramMap.put("osVersion", phone.getOsVersion());
        paramMap.put("p_tk", DjcTokenUtil.getPToken(accessToken));
        paramMap.put("ch", djcAppProperties.getCh());
        paramMap.put("sVersionName", djcAppProperties.getSVersionName());
        paramMap.put("appSource", djcAppProperties.getAppSource());
        paramMap.put("sDjcSign", DjcSignUtil.getDjcSign(openId,
                djcAppProperties.getAppVersion(), phone.getSDeviceID()));
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 查询商品时间
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param actId       活动id
     * @param goodsId     商品id
     * @return 请求结果
     */
    public DjcResult queryGoodTime(String openId, String accessToken, String actId, String goodsId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "limit.query.user");
        paramMap.put("iAppId", djcAppProperties.getAppId());
        paramMap.put("_app_id", "1003");
        paramMap.put("biz", "fight");
        paramMap.put("iActionId", actId);
        paramMap.put("propid", goodsId);
        //限量类型
        paramMap.put("type", "2");
        paramMap.put("bizs", "fight");
        paramMap.put("actids", actId);
        paramMap.put("propids", goodsId);
        paramMap.put("types", "2");
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 购买商品
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param actId       活动id
     * @param goodsId     商品id
     * @param roleCode    角色代码
     * @param roleName    角色名称
     * @return 请求结果
     */
    @IgnoreParameterNumber
    public DjcResult bugGood(String openId, String accessToken,
                             String actId, String goodsId, String roleCode, String roleName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "buy.plug.swoole.judou");
        paramMap.put("iAppId", djcAppProperties.getAppId());
        paramMap.put("_app_id", "1003");
        paramMap.put("biz", "fight");
        paramMap.put("_biz_code", "fight");
        paramMap.put("_output_fmt", "1");
        paramMap.put("_plug_id", "9800");
        paramMap.put("_from", "app");
        paramMap.put("iActionId", actId);
        paramMap.put("iGoodsSeqId", goodsId);
        paramMap.put("iActionType", "26");
        paramMap.put("lRoleId", roleCode);
        paramMap.put("rolename", roleName);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 获取角色绑定列表信息
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @return 角色信息映射表
     */
    public Map<String, String> roleBindList(String openId, String accessToken) {
        String roleName = "", roleCode = "", partition = "", systemID = "1", channelID = "1", channelKey = "qq";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.role.bind_list");
        paramMap.put("_biz_code", "yxzj");
        //限量类型
        paramMap.put("type", "1");
        Map<String, String> map = new HashMap<>();
        DjcResult djcResult = doGetMain(getCookie(openId, accessToken), paramMap);
        JSONObject jsonObject = djcResult.getJsonObject();
        int ret = jsonObject.getInt("ret");
        if (0 == ret) {
            JSONArray data = jsonObject.getJSONArray("data");
            if (!data.isEmpty()) {
                JSONObject sRoleInfo = data.getJSONObject(0).getJSONObject("sRoleInfo");
                roleCode = sRoleInfo.getStr("roleCode");
                roleName = sRoleInfo.getStr("roleName");
                partition = sRoleInfo.getStr("partition");
                systemID = sRoleInfo.getStr("systemID");
                channelKey = sRoleInfo.getStr("channelKey");
                channelID = sRoleInfo.getStr("channelID");
            }
        }
        map.put("roleCode", roleCode);
        map.put("roleName", roleName);
        map.put("partition", partition);
        map.put("systemID", systemID);
        map.put("channelID", channelID);
        map.put("channelKey", channelKey);
        return map;
    }

    /**
     * 上报浏览任务记录
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param taskType    任务类型
     * @return 请求响应
     */
    public DjcResult reportBrowseTask(String openId, String accessToken, String taskType) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.task.report");
        paramMap.put("iAppId", djcAppProperties.getAppId());
        paramMap.put("_app_id", djcAppProperties.getAppId());
        paramMap.put("task_type", taskType);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 查询任务列表
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @return 请求结果
     */
    public DjcResult queryTaskList(String openId, String accessToken) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "welink.usertask.swoole");
        paramMap.put("iAppId", djcAppProperties.getAppId());
        paramMap.put("_app_id", djcAppProperties.getAppId());
        paramMap.put("optype", "get_usertask_list");
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 查询任务限制时间
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param iRuleId     角色id
     * @return 请求结果
     */
    public DjcResult limitTimeTask(String openId, String accessToken, String iRuleId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "welink.usertask.swoole");
        paramMap.put("iAppId", djcAppProperties.getAppId());
        paramMap.put("_app_id", djcAppProperties.getAppId());
        paramMap.put("optype", "report_usertask_rushtime");
        paramMap.put("ruleid", iRuleId);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 通用请求方法
     *
     * @param cookie   cookie
     * @param paramMap 参数映射表
     * @return 请求结果
     */
    public DjcResult doGetMain(String cookie, Map<String, String> paramMap) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        StringBuilder getUrl = new StringBuilder(djcAppProperties.getDjcMainUrl());
        for (String key : paramMap.keySet()) {
            getUrl.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        getUrl.deleteCharAt(getUrl.length() - 1);

        Request request = new Request.Builder().url(getUrl.toString())
                .addHeader("Cookie", cookie)
                .get().build();
        try {
            Response response = client.newCall(request).execute();
            String resBodyStr = response.body().string();
            log.info("请求响应:[{}]", resBodyStr);
            JSONObject jsonObject = JSONUtil.parseObj(resBodyStr);
            int ret = jsonObject.getInt("ret");
            return DjcResult.msg("").djcResult(ret == 0).jsonObject(jsonObject);
        } catch (IOException e) {
            log.error("请求发生错误:{}", e);
            return DjcResult.requestFail();
        }
    }

    /**
     * 获取cookie
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @return cookie
     */
    public String getCookie(String openId, String accessToken) {
        return "djc_appSource=" + djcAppProperties.getAppSource() + "; "
                + "djc_appVersion=" + djcAppProperties.getAppVersion() + "; "
                + "acctype=qc; "
                + "appid=1101958653; "
                + "openid=" + openId + "; "
                + "access_token=" + accessToken;
    }

    /**
     * 查询用户心愿
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param currentPage 当前页码
     * @return 请求结果
     */
    public DjcResult queryUserDemands(String openId, String accessToken, int currentPage) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.demand.user.demand");
        paramMap.put("_app_id", "1001");
        paramMap.put("_biz_code", "yxzj");
        paramMap.put("ps", "5");
        paramMap.put("pn", Integer.toString(currentPage));
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 删除心愿
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param sKeyId      sKeyId
     * @return 请求结果
     */
    public DjcResult delDemand(String openId, String accessToken, String sKeyId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.demand.delete");
        paramMap.put("iAppId", "1001");
        paramMap.put("_app_id", "1001");
        paramMap.put("sKeyId", sKeyId);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 添加心愿
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param goodsId     商品id
     * @param roleInfoMap 角色信息映射表
     * @return 请求结果
     */
    public DjcResult addDemand(String openId, String accessToken, String goodsId, Map<String, String> roleInfoMap) {
        String roleCode = roleInfoMap.get("roleCode");
        String partition = roleInfoMap.get("partition");
        String channelID = roleInfoMap.get("channelID");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.demand.create");
        paramMap.put("iAppId", "1001");
        paramMap.put("_app_id", "1001");
        paramMap.put("iActionId", "3");
        paramMap.put("iGoodsId", goodsId);
        paramMap.put("sBizCode", "yxzj");
        paramMap.put("partition", partition);
        paramMap.put("iZoneId", channelID);
        paramMap.put("sRoleId", roleCode);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 获取任务奖励
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param iRuleId     角色id
     * @return 请求结果
     */
    public DjcResult getTaskAward(String openId, String accessToken, String iRuleId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "welink.usertask.swoole");
        paramMap.put("optype", "receive_usertask");
        paramMap.put("_app_id", "1001");
        paramMap.put("iAppId", "1001");
        paramMap.put("iruleId", iRuleId);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 查询聚豆明细
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @param page        页数
     * @return 请求结果
     */
    public DjcResult queryJdDetails(String openId, String accessToken, String page) {
        Date date = new Date();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.bean.water");
        String starttime = DateUtil.format(date, "yyyyMMdd");
        paramMap.put("starttime", starttime + "000000");
        String endtime = DateUtil.format(date, "yyyyMMddHHmmss");
        paramMap.put("endtime", endtime);
        paramMap.put("page", page);
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }

    /**
     * 查询签到规则
     *
     * @param openId      openId
     * @param accessToken 访问令牌
     * @return 请求结果
     */
    public DjcResult querySignRule(String openId, String accessToken) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("_service", "app.sign.rules");
        paramMap.put("iAppId", "1001");
        paramMap.put("_app_id", "1001");
        return doGetMain(getCookie(openId, accessToken), paramMap);
    }
}
