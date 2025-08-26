package top.zhongyingjie.nest.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import top.zhongyingjie.nest.constant.SignRuleId;
import top.zhongyingjie.nest.dao.DjcAppProperties;
import top.zhongyingjie.nest.entity.Phone;
import top.zhongyingjie.nest.pojo.DjcResult;
import top.zhongyingjie.nest.utils.DjcSignUtil;
import top.zhongyingjie.nest.utils.DjcTokenUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class DjcRequestService {

    @Autowired
    private DjcAppProperties djcAppProperties;

    public DjcResult commAmsAmeSvr(String openId, String iFlowId, String accessToken, String deviceId){
        Phone phone = new Phone(deviceId);
        String commAmeAmsUrl = djcAppProperties.getCommAmeAmsUrl(phone);
        String djcSign = DjcSignUtil.getDjcSign(openId, djcAppProperties.appVersion,phone.getSDeviceID());
        String cookie = "djc_appSource="+ djcAppProperties.appSource+"; "
                +"djc_appVersion="+ djcAppProperties.appVersion+"; "
                +"acctype=qc; "
                +"appid=1101958653; "
                +"openid="+openId+"; "
                +"access_token="+accessToken;

        Map<String, String> paramMap = new HashMap();
        paramMap.put("appVersion", djcAppProperties.appVersion);
        paramMap.put("appSource", djcAppProperties.appSource);
        paramMap.put("g_tk", DjcTokenUtil.getGToken());
        paramMap.put("iFlowId",iFlowId);
        paramMap.put("sServiceType", djcAppProperties.sServiceType);
        paramMap.put("sServiceDepartment", djcAppProperties.sServiceDepartment);
        paramMap.put("iActivityId", djcAppProperties.iActivityId);
        paramMap.put("sDjcSign", djcSign);
        paramMap.put("sDeviceID", phone.getSDeviceID());
        String linkStr = "";
        DateTime date = DateUtil.date();
        int month = date.month()+1;
        if(month<10) linkStr="0";
        String monthStr = date.year()+linkStr+ month;
        paramMap.put("month", monthStr);
        paramMap.put("osVersion", phone.getOsVersion());
        paramMap.put("sign_version", djcAppProperties.signVersion);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        StringBuilder content = new StringBuilder();
        for (String key : paramMap.keySet()) {
            content.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        content.deleteCharAt(content.length()-1);
        RequestBody body = RequestBody.create(mediaType, content.toString());
        Request request = new Request.Builder()
                .url(commAmeAmsUrl)
                .method("POST",body)
                .addHeader("Cookie",cookie)
                .addHeader("User-Agent", "TencentDaojucheng=v4.7.1.0&appSource=android&appVersion=147&ch=10000&sDeviceID=832ed1ab5036d8bec44e4cc6c12c439d7544bb25eab1ffa9acec1abe6bd913ca&firmwareVersion=13&phoneBrand=Xiaomi&phoneVersion=2201122C&displayMetrics=1440 * 3036&cpu=0&net=wifi&sVersionName=v4.7.1.0&plNo=152")
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
            log.info("消息返回:[{}]",jsonObject.toString());
            String msg = jsonObject.getJSONObject("modRet").getStr("sMsg");
            return DjcResult.msg(msg).DjcResult("0".equals(ret)).jsonObject(jsonObject);
        } catch (Exception e) {
            log.error("commAmsAmeSvr请求发生错误:[{}]",e);
            return DjcResult.requestFail();
        }
    }

    public DjcResult packageReceive(String openId,String accessToken,Map<String,String> roleInfoMap,String deviceId) {
        String roleCode = roleInfoMap.get("roleCode");
        String roleName = roleInfoMap.get("roleName");
        String partition = roleInfoMap.get("partition");
        String systemID = roleInfoMap.get("systemID");
        String channelID = roleInfoMap.get("channelID");
        String channelKey = roleInfoMap.get("channelKey");
        Phone phone = new Phone(deviceId);
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.package.receive");
        paramMap.put("iAppId",djcAppProperties.appId);
        paramMap.put("_app_id",djcAppProperties.appId);
        paramMap.put("output_format","json");
        paramMap.put("optype","receive_usertask_game");
        String ruleId = SignRuleId.RULE_ID[DateUtil.thisDayOfWeek()];
        paramMap.put("iruleId",ruleId);
        paramMap.put("bizcode","yxzj");
        paramMap.put("_biz_code","yxzj");
        paramMap.put("roleCode",roleCode);
        paramMap.put("systemID",systemID);
        paramMap.put("channelID",channelID);
        paramMap.put("sRoleName",roleName);
        paramMap.put("sPartition",partition);
        paramMap.put("channelKey",channelKey);
        paramMap.put("sDeviceID",phone.getSDeviceID());
        paramMap.put("appVersion",djcAppProperties.appVersion);
        paramMap.put("osVersion",phone.getOsVersion());
        paramMap.put("p_tk",DjcTokenUtil.getPToken(accessToken));
        paramMap.put("ch", djcAppProperties.ch);
        paramMap.put("sVersionName", djcAppProperties.sVersionName);
        paramMap.put("appSource",djcAppProperties.appSource);
        paramMap.put("sDjcSign",DjcSignUtil.getDjcSign(openId,djcAppProperties.appVersion,phone.getSDeviceID()));
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult queryGoodTime(String openId,String accessToken,String actId,String goodsId) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","limit.query.user");
        paramMap.put("iAppId",djcAppProperties.appId);
        paramMap.put("_app_id","1003");
        paramMap.put("biz","fight");
        paramMap.put("iActionId",actId);
        paramMap.put("propid",goodsId);
        //限量类型
        paramMap.put("type","2");
        paramMap.put("bizs","fight");
        paramMap.put("actids",actId);
        paramMap.put("propids",goodsId);
        paramMap.put("types","2");
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult bugGood(String openId,String accessToken,String actId,String goodsId,String roleCode,String roleName) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","buy.plug.swoole.judou");
        paramMap.put("iAppId",djcAppProperties.appId);
        paramMap.put("_app_id","1003");
        paramMap.put("biz","fight");
        paramMap.put("_biz_code","fight");
        paramMap.put("_output_fmt","1");
        paramMap.put("_plug_id","9800");
        paramMap.put("_from","app");
        paramMap.put("iActionId",actId);
        paramMap.put("iGoodsSeqId",goodsId);
        paramMap.put("iActionType","26");
        paramMap.put("lRoleId",roleCode);
        paramMap.put("rolename",roleName);
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public Map<String, String> roleBindList(String openId, String accessToken) {
        String roleName="",roleCode="",partition="",systemID="1",channelID="1",channelKey="qq";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.role.bind_list");
        paramMap.put("_biz_code","yxzj");
        //限量类型
        paramMap.put("type","1");
        Map<String,String> map = new HashMap<>();
        DjcResult DjcResult = doGetMain(getCookie(openId, accessToken), paramMap);
        JSONObject jsonObject = DjcResult.getJsonObject();
        int ret = jsonObject.getInt("ret");
        if(0==ret){
            JSONArray data = jsonObject.getJSONArray("data");
            if(data.size()>0){
                JSONObject sRoleInfo = data.getJSONObject(0).getJSONObject("sRoleInfo");
                roleCode = sRoleInfo.getStr("roleCode");
                roleName = sRoleInfo.getStr("roleName");
                partition = sRoleInfo.getStr("partition");
                systemID = sRoleInfo.getStr("systemID");
                channelKey = sRoleInfo.getStr("channelKey");
                channelID = sRoleInfo.getStr("channelID");
            }
        }
        map.put("roleCode",roleCode);
        map.put("roleName",roleName);
        map.put("partition",partition);
        map.put("systemID",systemID);
        map.put("channelID",channelID);
        map.put("channelKey",channelKey);
        return map;

    }

    public DjcResult reportBrowseTask(String openId, String accessToken,String taskType) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.task.report");
        paramMap.put("iAppId",djcAppProperties.appId);
        paramMap.put("_app_id",djcAppProperties.appId);
        paramMap.put("task_type",taskType);
        return doGetMain(getCookie(openId,accessToken),paramMap);
    }

    public DjcResult queryTaskList(String openId, String accessToken) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=welink.usertask.swoole&optype=get_usertask_list&_app_id=1001&output_format=json&iAppId=1001
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","welink.usertask.swoole");
        paramMap.put("iAppId",djcAppProperties.appId);
        paramMap.put("_app_id",djcAppProperties.appId);
        paramMap.put("optype","get_usertask_list");
        return doGetMain(getCookie(openId,accessToken),paramMap);
    }

    public DjcResult limitTimeTask(String openId, String accessToken, String iruleId) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=welink.usertask.swoole&iAppId=1001&_app_id=1001&optype=report_usertask_rushtime&ruleid=600029
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","welink.usertask.swoole");
        paramMap.put("iAppId",djcAppProperties.appId);
        paramMap.put("_app_id",djcAppProperties.appId);
        paramMap.put("optype","report_usertask_rushtime");
        paramMap.put("ruleid",iruleId);
        return doGetMain(getCookie(openId,accessToken),paramMap);
    }

    public DjcResult doGetMain(String cookie, Map<String, String> paramMap) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        StringBuilder getUrl = new StringBuilder(djcAppProperties.getDjcMainUrl());
        for (String key : paramMap.keySet()) {
            getUrl.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        getUrl.deleteCharAt(getUrl.length()-1);

        Request request = new Request.Builder().url(getUrl.toString())
                .addHeader("Cookie",cookie)
                .get().build();
        try {
            Response response = client.newCall(request).execute();
            String resBodyStr = response.body().string();
            log.info("请求响应:[{}]",resBodyStr);
            JSONObject jsonObject = JSONUtil.parseObj(resBodyStr);
            int ret = jsonObject.getInt("ret");
            return DjcResult.msg("").DjcResult(ret==0).jsonObject(jsonObject);
        } catch (IOException e) {
            log.error("请求发生错误:{}",e);
            return DjcResult.requestFail();
        }
    }

    public String getCookie(String openId,String accessToken){
        return "djc_appSource="+ djcAppProperties.appSource+"; "
                +"djc_appVersion="+ djcAppProperties.appVersion+"; "
                +"acctype=qc; "
                +"appid=1101958653; "
                +"openid="+openId+"; "
                +"access_token="+accessToken;
    }

    public DjcResult queryUserDemands(String openId, String accessToken, int currentPage) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=app.demand.user.demand&_app_id=1001&pn=1&ps=5
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.demand.user.demand");
        paramMap.put("_app_id","1001");
        paramMap.put("_biz_code","yxzj");
        paramMap.put("ps","5");
        paramMap.put("pn",Integer.toString(currentPage));
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult delDemand(String openId, String accessToken, String sKeyId) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=app.demand.delete&iAppId=1001&_app_id=1001&sKeyId=YXZJ-DEMAND-33-20231006161949493958
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.demand.delete");
        paramMap.put("iAppId","1001");
        paramMap.put("_app_id","1001");
        paramMap.put("sKeyId",sKeyId);
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult addDemand(String openId, String accessToken, String goodsId,Map<String,String> roleInfoMap) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=app.demand.create&iAppId=1001&_app_id=1001&iActionId=3&iGoodsId=26436&sBizCode=yxzj&partition=1238&iZoneId=1
        String roleCode = roleInfoMap.get("roleCode");
        String partition = roleInfoMap.get("partition");
        String channelID = roleInfoMap.get("channelID");
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.demand.create");
        paramMap.put("iAppId","1001");
        paramMap.put("_app_id","1001");
        paramMap.put("iActionId","3");
        paramMap.put("iGoodsId",goodsId);
        paramMap.put("sBizCode","yxzj");
        paramMap.put("partition",partition);
        paramMap.put("iZoneId",channelID);
        paramMap.put("sRoleId",roleCode);
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult getTaskAward(String openId, String accessToken, String iRuleId) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=welink.usertask.swoole&optype=receive_usertask&_app_id=1001&iAppId=1001&iruleId=100001
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","welink.usertask.swoole");
        paramMap.put("optype","receive_usertask");
        paramMap.put("_app_id","1001");
        paramMap.put("iAppId","1001");
        paramMap.put("iruleId",iRuleId);
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult queryJdDetails(String openId, String accessToken,String page) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=app.bean.water&starttime=20230430000000&endtime=20230930182909
        Date date = new Date();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.bean.water");
        String starttime = DateUtil.format(date,"yyyyMMdd");
        paramMap.put("starttime",starttime+"000000");
        String endtime = DateUtil.format(date,"yyyyMMddHHmmss");
        paramMap.put("endtime",endtime);
        paramMap.put("page",page);
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }

    public DjcResult querySignRule(String openId, String accessToken) {
        //https://djcapp.game.qq.com/daoju/igw/main/?_service=app.sign.rules&iAppId=1001&_app_id=1001
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("_service","app.sign.rules");
        paramMap.put("iAppId","1001");
        paramMap.put("_app_id","1001");
        return doGetMain(getCookie(openId, accessToken),paramMap);
    }
}
