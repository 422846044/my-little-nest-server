package top.zhongyingjie.nest.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName log_info
 * @author Kong
 */
public class FightLogInfo implements Serializable {

    private static final long serialVersionUID = 8622953179089880872L;

    /**
     * 日志id
     */
    private Long logId;

    /**
     * qq号
     */
    private Integer qqNum;

    public FightLogInfo() {
    }

    public FightLogInfo(Integer qqNum, String logContent, Date createTime) {
        this.qqNum = qqNum;
        this.logContent = logContent;
        this.createTime = createTime;
    }

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getQqNum() {
        return qqNum;
    }

    public void setQqNum(Integer qqNum) {
        this.qqNum = qqNum;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        FightLogInfo other = (FightLogInfo) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
            && (this.getQqNum() == null ? other.getQqNum() == null : this.getQqNum().equals(other.getQqNum()))
            && (this.getLogContent() == null
                ? other.getLogContent() == null : this.getLogContent().equals(other.getLogContent()))
            && (this.getCreateTime() == null
                ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null
                ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getQqNum() == null) ? 0 : getQqNum().hashCode());
        result = prime * result + ((getLogContent() == null) ? 0 : getLogContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", qqNum=").append(qqNum);
        sb.append(", logContent=").append(logContent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
