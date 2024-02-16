package fun.dfwh.nest.entity;

import lombok.Data;

@Data
public class Phone {
    private String osVersion;
    private String deviceModel;
    private String sDeviceID;

    public Phone(){
        this.osVersion = "Android-33";
        this.deviceModel = "2201122C";
        this.sDeviceID = "832ed1ab5036d8bec44e4cc6c12c439d7544bb25eab1ffa9acec1abe6bd913ca";
    }

    public Phone(String deviceId){
        this.osVersion = "Android-33";
        this.deviceModel = "2201122C";
        this.sDeviceID = deviceId;
    }
}
