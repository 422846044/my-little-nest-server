package top.zhongyingjie.nest.code.example.signature.dto;

/**
 * 业务参数示例
 *
 * @author Kong
 */
public class BizRequest {

    private String userId;

    private String action;

    public BizRequest() {
    }

    public BizRequest(String userId, String action) {
        this.userId = userId;
        this.action = action;
    }

    // Getter和Setter方法
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}