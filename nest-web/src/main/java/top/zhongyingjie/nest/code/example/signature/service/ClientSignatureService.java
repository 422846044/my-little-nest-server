package top.zhongyingjie.nest.code.example.signature.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.zhongyingjie.nest.code.example.signature.SignatureUtil;
import top.zhongyingjie.nest.code.example.signature.dto.SignedRequest;

import java.util.Map;

/**
 * 客户端签名服务
 *
 * @author Kong
 */
public class ClientSignatureService {

    private final String appId;

    private final String appSecret;

    private final ObjectMapper objectMapper;

    public ClientSignatureService(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 构建带签名的请求
     *
     * @param bizContent 业务内容
     * @return 签名请求对象
     */
    public SignedRequest buildSignedRequest(Object bizContent) {
        long timestamp = System.currentTimeMillis() / 1000; // 秒级时间戳
        String nonce = SignatureUtil.generateNonce(16);

        // 将业务对象转换为Map
        Map<String, Object> params = objectMapper.convertValue(
                bizContent, new TypeReference<Map<String, Object>>() {
                });

        String signature = SignatureUtil.generateSignature(
                params, appId, appSecret, timestamp, nonce);

        return new SignedRequest(appId, timestamp, nonce, signature, bizContent);
    }

    /**
     * 验证响应签名（如果服务端返回签名）
     *
     * @param responseData 响应数据
     * @param signature    签名
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @return 是否有效
     */
    public boolean verifyResponseSignature(Map<String, Object> responseData,
                                           String signature,
                                           long timestamp,
                                           String nonce) {
        return SignatureUtil.verifySignature(
                responseData, appId, appSecret, timestamp, nonce, signature, 300);
    }
}
