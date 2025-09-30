package top.zhongyingjie.nest.code.example.signature.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.code.example.signature.SignatureUtil;
import top.zhongyingjie.nest.code.example.signature.dto.SignedRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端验签服务
 *
 * @author Kong
 */
public class ServerSignatureService {

    private final Map<String, String> appSecrets;
    private final ObjectMapper objectMapper;
    private final Map<String, Long> usedNonces; // 防重放攻击缓存

    public ServerSignatureService() {
        this.appSecrets = new ConcurrentHashMap<>();
        this.objectMapper = new ObjectMapper();
        this.usedNonces = new ConcurrentHashMap<>();

        // 初始化应用密钥（实际应该从数据库加载）
        appSecrets.put("appId01", "your_secret_key_123456");
        appSecrets.put("appId02", "another_secret_key_789012");
    }

    /**
     * 验证请求签名
     *
     * @param signedRequest 签名请求对象
     * @return 统一返回对象
     */
    public Result<Map<String, Object>> verifyRequest(SignedRequest signedRequest) {
        try {
            // 检查必要参数
            if (signedRequest.getAppId() == null ||
                    signedRequest.getTimestamp() == 0 ||
                    signedRequest.getNonce() == null ||
                    signedRequest.getSignature() == null) {
                return Result.serverError("缺少必要参数");
            }

            // 检查应用是否存在
            String appSecret = appSecrets.get(signedRequest.getAppId());
            if (appSecret == null) {
                return Result.serverError("应用ID不存在");
            }

            // 检查nonce是否已使用（防重放攻击）
            if (isNonceUsed(signedRequest.getNonce(), signedRequest.getTimestamp())) {
                return Result.serverError("请求已过期或重复");
            }

            // 将业务对象转换为Map
            Map<String, Object> bizParams = objectMapper.convertValue(
                    signedRequest.getBizContent(), new TypeReference<Map<String, Object>>() {
                    });

            // 验证签名
            boolean isValid = SignatureUtil.verifySignature(
                    bizParams,
                    signedRequest.getAppId(),
                    appSecret,
                    signedRequest.getTimestamp(),
                    signedRequest.getNonce(),
                    signedRequest.getSignature(),
                    300 // 5分钟超时
            );

            if (!isValid) {
                return Result.serverError("签名验证失败");
            }

            // 标记nonce为已使用
            markNonceAsUsed(signedRequest.getNonce(), signedRequest.getTimestamp());

            return Result.success(bizParams).setMessage("验证成功");

        } catch (Exception e) {
            return Result.serverError("验签过程发生错误: " + e.getMessage());
        }
    }

    /**
     * 检查nonce是否已使用
     */
    private boolean isNonceUsed(String nonce, long timestamp) {
        Long usedTime = usedNonces.get(nonce);
        if (usedTime == null) {
            return false;
        }

        // 清理过期的nonce记录（超过10分钟）
        cleanExpiredNonces();

        return true;
    }

    /**
     * 标记nonce为已使用
     */
    private void markNonceAsUsed(String nonce, long timestamp) {
        usedNonces.put(nonce, timestamp);
    }

    /**
     * 清理过期的nonce记录
     */
    private void cleanExpiredNonces() {
        long currentTime = System.currentTimeMillis() / 1000;
        long expireTime = 10 * 60; // 10分钟

        usedNonces.entrySet().removeIf(entry ->
                (currentTime - entry.getValue()) > expireTime);
    }

    /**
     * 添加应用密钥
     *
     * @param appId     appId
     * @param appSecret app密钥
     */
    public void addAppSecret(String appId, String appSecret) {
        appSecrets.put(appId, appSecret);
    }
}
