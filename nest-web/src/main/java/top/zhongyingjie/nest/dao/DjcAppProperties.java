package top.zhongyingjie.nest.dao;

import top.zhongyingjie.nest.constant.DjcReqUrlEnum;
import top.zhongyingjie.nest.entity.Phone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 道具城app属性类
 *
 * @author Kong
 */
@Component
public class DjcAppProperties {

    @Value("${sys.ameVersion}")
    private String ameVersion;

    @Value("${sys.appVersion}")
    private String appVersion;

    @Value("${sys.sServiceType}")
    private String sServiceType;

    @Value("${sys.iActivityId}")
    private String iActivityId;

    @Value("${sys.sServiceDepartment}")
    private String sServiceDepartment;

    @Value("${sys.set_info}")
    private String setInfo;

    @Value("${sys.weexVersion}")
    private String weexVersion;

    @Value("${sys.platform}")
    private String platform;

    @Value("${sys.appSource}")
    private String appSource;

    @Value("${sys.appId}")
    private String appId;

    @Value("${sys.ch}")
    private String ch;

    @Value("${sys.sVersionName}")
    private String sVersionName;

    @Value("${sys.signVersion}")
    private String signVersion;

    private String commAmeAmsUrl = "";

    private String loginUrl = "";

    public String getAmeVersion() {
        return ameVersion;
    }

    public void setAmeVersion(String ameVersion) {
        this.ameVersion = ameVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSServiceType() {
        return sServiceType;
    }

    public void setSServiceType(String sServiceType) {
        this.sServiceType = sServiceType;
    }

    public String getIActivityId() {
        return iActivityId;
    }

    public void setIActivityId(String iActivityId) {
        this.iActivityId = iActivityId;
    }

    public String getSServiceDepartment() {
        return sServiceDepartment;
    }

    public void setSServiceDepartment(String sServiceDepartment) {
        this.sServiceDepartment = sServiceDepartment;
    }

    public String getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(String setInfo) {
        this.setInfo = setInfo;
    }

    public String getWeexVersion() {
        return weexVersion;
    }

    public void setWeexVersion(String weexVersion) {
        this.weexVersion = weexVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppSource() {
        return appSource;
    }

    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getSVersionName() {
        return sVersionName;
    }

    public void setSVersionName(String sVersionName) {
        this.sVersionName = sVersionName;
    }

    public String getSignVersion() {
        return signVersion;
    }

    public void setSignVersion(String signVersion) {
        this.signVersion = signVersion;
    }

    public String getCommAmeAmsUrl() {
        return commAmeAmsUrl;
    }

    public void setCommAmeAmsUrl(String commAmeAmsUrl) {
        this.commAmeAmsUrl = commAmeAmsUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * 给url设置参数
     *
     * @param url    url
     * @param params 参数映射表
     * @return 请求链接
     */
    public String setParam(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 获取请求链接
     *
     * @param phone 手机信息
     * @return 请求链接
     */
    public String getCommAmeAmsUrl(Phone phone) {
        if ("".equals(commAmeAmsUrl)) {
            String urlStr = DjcReqUrlEnum.COMM_AMS_AME_URL.getUrlStr();
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("ameVersion", ameVersion);
            paramMap.put("appVersion", appVersion);
            paramMap.put("sDeviceID", phone.getSDeviceID());
            paramMap.put("sServiceType", sServiceType);
            paramMap.put("iActivityId", iActivityId);
            paramMap.put("sServiceDepartment", sServiceDepartment);
            paramMap.put("set_info", setInfo);
            paramMap.put("weexVersion", weexVersion);
            paramMap.put("platform", platform);
            paramMap.put("appSource", appSource);
            paramMap.put("deviceModel", phone.getDeviceModel());
            paramMap.put("ch", ch);
            paramMap.put("osVersion", phone.getOsVersion());
            paramMap.put("sVersionName", sVersionName);
            commAmeAmsUrl = setParam(urlStr, paramMap);
        }
        return commAmeAmsUrl;
    }

    public String getDjcMainUrl() {
        return DjcReqUrlEnum.DJC_MAIN.getUrlStr();
    }

    /**
     * 获取登录链接
     *
     * @return 登录链接
     */
    public String getLoginUrl() {
        if ("".equals(loginUrl)) {
            String urlStr = DjcReqUrlEnum.LOGIN_URL.getUrlStr();
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appVersion", appVersion);
            paramMap.put("appSource", appSource);
            paramMap.put("sVersionName", sVersionName);
            loginUrl = setParam(urlStr, paramMap);
        }
        return loginUrl;
    }
}
