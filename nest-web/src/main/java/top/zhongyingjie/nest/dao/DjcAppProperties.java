package top.zhongyingjie.nest.dao;

import top.zhongyingjie.nest.constant.DjcReqUrlEnum;
import top.zhongyingjie.nest.entity.Phone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class DjcAppProperties {
    @Value("${sys.ameVersion}")
    public String ameVersion;
    @Value("${sys.appVersion}")
    public String appVersion;
    @Value("${sys.sServiceType}")
    public String sServiceType;
    @Value("${sys.iActivityId}")
    public String iActivityId;
    @Value("${sys.sServiceDepartment}")
    public String sServiceDepartment;
    @Value("${sys.set_info}")
    public String set_info;
    @Value("${sys.weexVersion}")
    public String weexVersion;
    @Value("${sys.platform}")
    public String platform;
    @Value("${sys.appSource}")
    public String appSource;
    @Value("${sys.appId}")
    public String appId;
    @Value("${sys.ch}")
    public String ch;
    @Value("${sys.sVersionName}")
    public String sVersionName;
    @Value("${sys.signVersion}")
    public String signVersion;

    private String commAmeAmsUrl = "";

    private String loginUrl = "";

    public String setParam(String url, Map<String,String> params){
        StringBuilder sb = new StringBuilder(url);
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    public String getCommAmeAmsUrl(Phone phone){
        if("".equals(commAmeAmsUrl)){
            String urlStr = DjcReqUrlEnum.COMM_AMS_AME_URL.getUrlStr();
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("ameVersion",ameVersion);
            paramMap.put("appVersion",appVersion);
            paramMap.put("sDeviceID", phone.getSDeviceID());
            paramMap.put("sServiceType",sServiceType);
            paramMap.put("iActivityId",iActivityId);
            paramMap.put("sServiceDepartment",sServiceDepartment);
            paramMap.put("set_info",set_info);
            paramMap.put("weexVersion",weexVersion);
            paramMap.put("platform",platform);
            paramMap.put("appSource",appSource);
            paramMap.put("deviceModel",phone.getDeviceModel());
            paramMap.put("ch",ch);
            paramMap.put("osVersion",phone.getOsVersion());
            paramMap.put("sVersionName",sVersionName);
            commAmeAmsUrl = setParam(urlStr, paramMap);
        }
        return commAmeAmsUrl;
    }

    public String getDjcMainUrl(){
        return DjcReqUrlEnum.DJC_MAIN.getUrlStr();
    }

    public String getLoginUrl(){
        if("".equals(loginUrl)){
            String urlStr = DjcReqUrlEnum.LOGIN_URL.getUrlStr();
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("appVersion",appVersion);
            paramMap.put("appSource",appSource);
            paramMap.put("sVersionName",sVersionName);
            loginUrl = setParam(urlStr, paramMap);
        }
        return loginUrl;
    }
}
