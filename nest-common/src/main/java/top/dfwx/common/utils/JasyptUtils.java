package top.dfwx.common.utils;


import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * jasypt工具类
 *
 * @author atulan_zyj
 * @date 2024/4/22
 */
public class JasyptUtils {

    private static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";

    private static final String PBEWITHHMACSHA512ANDAES_256 = "PBEWITHHMACSHA512ANDAES_256";

    /**
     * jasypt2.x 加密（PBEWithMD5AndDES）
     *
     * @return java.lang.String
     * @param         plainText      待加密的原文
     * @param         factor         加密秘钥
     */
    public static String encryptWithMD5(String plainText, String factor) {
        // 1. 创建加解密工具实例
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 2. 加解密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setPassword(factor);
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }

    /**
     * jasypt2.x 解密（PBEWithMD5AndDES）
     *
     * @return java.lang.String
     * @param         encryptedText      待解密密文
     * @param         factor             解密秘钥
     */
    public static String decryptWithMD5(String encryptedText, String factor) {
        // 1. 创建加解密工具实例
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 2. 加解密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setPassword(factor);
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }

    /**
     * jasypt3.x 加密（PBEWITHHMACSHA512ANDAES_256）
     *
     * @return java.lang.String
     * @param         plainText  待加密的原文
     * @param         factor     加密秘钥
     */
    public static String encryptWithSHA512(String plainText, String factor) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(factor);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        // 为减少配置文件的书写，以下都是Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }

    /**
     * jasypt3.x 解密（PBEWITHHMACSHA512ANDAES_256）
     *
     * @return java.lang.String
     * @param         encryptedText  待解密密文
     * @param         factor         解密秘钥
     */
    public static String decryptWithSHA512(String encryptedText, String factor) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(factor);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        // 为减少配置文件的书写，以下都是Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }

    public static void main(String[] args) {
        String factor = "z422846044";
        String plainText = "QfT58RuuQd30m5dnjZDG368d9TbsRA_rlMs2f2Hv";
        //String encryptWithMD5Str = encryptWithMD5(plainText, factor);
        //String decryptWithMD5Str = decryptWithMD5(encryptWithMD5Str, factor);

        String encryptWithSHA512Str = encryptWithSHA512(plainText, factor);
        String decryptWithSHA512Str = decryptWithSHA512(encryptWithSHA512Str, factor);
        //System.out.println("采用MD5加密前原文密文：" + encryptWithMD5Str);
        //System.out.println("采用MD5解密后密文原文:" + decryptWithMD5Str);
        System.out.println();
        System.out.println("采用SHA512加密前原文密文：" + encryptWithSHA512Str);
        System.out.println("采用SHA512解密后密文原文:" + decryptWithSHA512Str);
    }
}
