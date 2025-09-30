package top.zhongyingjie.nest.code.example.signature.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 签名请求对象
 *
 * @author Kong
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignedRequest {

    private String appId;

    private long timestamp;

    private String nonce;

    private String signature;

    private Object bizContent;

    // 构造方法
    public SignedRequest() {

    }

    public SignedRequest(String appId, long timestamp, String nonce,
                         String signature, Object bizContent) {
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
        this.bizContent = bizContent;
    }

    // Getter和Setter方法
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getBizContent() {
        return bizContent;
    }

    public void setBizContent(Object bizContent) {
        this.bizContent = bizContent;
    }
}
