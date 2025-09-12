package top.zhongyingjie.nest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * 道具城签到工具类
 *
 * @author Kong
 */
public final class DjcSignUtil {

    private static final Logger log = LoggerFactory.getLogger(DjcSignUtil.class);

    private static final String F11865B = "RSA";

    private static final String F11866C = "RSA/ECB/PKCS1Padding";

    private static final String JOIN = "+";

    private DjcSignUtil() {

    }

    /**
     * 获取签到请求链接
     *
     * @param openId      openId
     * @param versionCode 版本code
     * @param deviceId    设备id
     * @return 签到请求链接
     */
    public static String getDjcSign(String openId, String versionCode, String deviceId) {
        StringBuilder sb = new StringBuilder();
        sb.append(openId);
        sb.append(JOIN);
        sb.append(deviceId);
        sb.append(JOIN);
        sb.append(System.currentTimeMillis());
        sb.append(JOIN);
        sb.append(versionCode);
        try {
            return b(a(a(sb.toString(), "se35d32s63r7m23m")));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("get djcSign error");
        }
        return "";
    }

    /**
     * 加密
     *
     * @param str  原文
     * @param str2 算法
     * @return 数组
     * @throws Exception 异常
     */
    public static byte[] a(String str, String str2) throws Exception {
        if (str == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(str2.getBytes(StandardCharsets.UTF_8), "AES"));
        return cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 加密
     *
     * @param bArr 字符数组
     * @return 数组
     * @throws Exception 异常
     */
    public static byte[] a(byte[] bArr) throws Exception {
        Cipher cipher = Cipher.getInstance(F11866C);
        cipher.init(1, a());
        return cipher.doFinal(bArr);
    }

    private static PublicKey a() {
        byte[] bArr;
        try (InputStream open = ClassPathResource.class.getClassLoader()
                .getResourceAsStream("djc_rsa_public_key_new.der")) {
            //InputStream open = new ClassPathResource("djc_rsa_public_key_new.der").getInputStream();
            try {
                bArr = new byte[open.available()];
                do {
                    // 内容略过
                    log.debug("测试");
                } while (open.read(bArr) != -1);
            } catch (Exception e2) {
                bArr = null;
            }
            return KeyFactory.getInstance(F11865B).generatePublic(new X509EncodedKeySpec(bArr));
        } catch (Exception e3) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param bArr 字符数组
     * @return 原文
     */
    public static String b(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str;
    }
}
