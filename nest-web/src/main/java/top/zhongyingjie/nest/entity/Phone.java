package top.zhongyingjie.nest.entity;

/**
 * 道具城请求手机信息传输对象
 *
 * @author Kong
 */
public class Phone {
    private String osVersion;
    private String deviceModel;
    private String sDeviceID;

    public Phone() {
        this.osVersion = "Android-33";
        this.deviceModel = "2201122C";
        this.sDeviceID = "832ed1ab5036d8bec44e4cc6c12c439d7544bb25eab1ffa9acec1abe6bd913ca";
    }

    public Phone(String deviceId) {
        this.osVersion = "Android-33";
        this.deviceModel = "2201122C";
        this.sDeviceID = deviceId;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSDeviceID() {
        return sDeviceID;
    }

    public void setSDeviceID(String sDeviceID) {
        this.sDeviceID = sDeviceID;
    }
}
