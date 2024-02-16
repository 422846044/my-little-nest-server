package fun.dfwh.nest.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_info
 */
@Data
public class FightUserInfo implements Serializable {
    /**
     * 
     */
    private Integer qqNum;

    /**
     * 
     */
    private String openId;

    /**
     * 
     */
    private String accessToken;

    /**
     * 
     */
    private String deviceId;

    private static final long serialVersionUID = 1L;

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
            && (this.getAccessToken() == null ? other.getAccessToken() == null : this.getAccessToken().equals(other.getAccessToken()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()));
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