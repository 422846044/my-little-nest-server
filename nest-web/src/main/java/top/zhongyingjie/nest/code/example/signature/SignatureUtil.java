package top.zhongyingjie.nest.code.example.signature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 签名工具类
 *
 * @author Kong
 */
public class SignatureUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * 生成签名
     *
     * @param params    参数
     * @param appId     appId
     * @param appSecret app密钥
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 签名
     */
    public static String generateSignature(Map<String, Object> params,
                                           String appId, String appSecret, long timestamp, String nonce) {
        try {
            // 1. 复制并添加系统参数
            Map<String, Object> signParams = new HashMap<>(params);
            signParams.put("app_id", appId);
            signParams.put("timestamp", timestamp);
            signParams.put("nonce", nonce);

            // 2. 按参数名排序
            List<Map.Entry<String, Object>> sortedEntries = signParams.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            // 3. 拼接参数字符串
            String paramStr = sortedEntries.stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            // 4. 使用HMAC-SHA256生成签名
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    appSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(paramStr.getBytes(StandardCharsets.UTF_8));

            // 转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("生成签名失败", e);
        }
    }

    /**
     * 验证签名
     *
     * @param params    参数
     * @param appId     appId
     * @param appSecret app密钥
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 签名
     * @param timeout   超时时间
     * @return 是否有效
     */
    public static boolean verifySignature(Map<String, Object> params,
                                          String appId,
                                          String appSecret,
                                          long timestamp,
                                          String nonce,
                                          String signature,
                                          long timeout) {
        // 1. 检查时间戳是否在有效期内
        long currentTime = System.currentTimeMillis() / 1000;
        if (Math.abs(currentTime - timestamp) > timeout) {
            return false;
        }

        // 2. 重新生成签名进行对比
        String expectedSignature = generateSignature(params, appId, appSecret, timestamp, nonce);

        // 3. 使用MessageDigest.isEqual防止时序攻击
        return MessageDigest.isEqual(
                signature.getBytes(StandardCharsets.UTF_8),
                expectedSignature.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * 生成随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String generateNonce(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}
