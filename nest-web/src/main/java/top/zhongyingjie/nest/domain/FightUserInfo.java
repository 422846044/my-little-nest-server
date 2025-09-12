package top.zhongyingjie.nest.domain;

import java.io.Serializable;

/**
 * 大乐斗用户信息表
 *
 * @TableName user_info
 */
public class FightUserInfo implements Serializable {

    private static final long serialVersionUID = -4630825223748639350L;

    /**
     * qq号
     */
    private Integer qqNum;

    /**
     * openId
     */
    private String openId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 设备id
     */
    private String deviceId;

    public Integer getQqNum() {
        return qqNum;
    }

    public void setQqNum(Integer qqNum) {
        this.qqNum = qqNum;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FightUserInfo other = (FightUserInfo) that;
        return (this.getQqNum() == null ? other.getQqNum() == null : this.getQqNum().equals(other.getQqNum()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getAccessToken() == null
                ? other.getAccessToken() == null : this.getAccessToken().equals(other.getAccessToken()))
            && (this.getDeviceId() == null
                ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getQqNum() == null) ? 0 : getQqNum().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getAccessToken() == null) ? 0 : getAccessToken().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", qqNum=").append(qqNum);
        sb.append(", openId=").append(openId);
        sb.append(", accessToken=").append(accessToken);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
