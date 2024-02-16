package fun.dfwh.nest.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class DjcSignUtil {

    private static String f11865b = "RSA";

    private static String f11866c = "RSA/ECB/PKCS1Padding";

    private static String JOIN = "+";

    public static String getDjcSign(String openId,String versionCode,String deviceId){
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
            log.error(e.getMessage(),e);
            log.error("get djcSign error");
        }
        return "";
    }

    public static byte[] a(String str, String str2) throws Exception {
        if (str == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(str2.getBytes(StandardCharsets.UTF_8), "AES"));
        return cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] a(byte[] bArr) throws Exception {
        Cipher cipher = Cipher.getInstance(f11866c);
        cipher.init(1, a());
        return cipher.doFinal(bArr);
    }

    private static PublicKey a() {
        byte[] bArr;
        try {
            InputStream open = ClassPathResource.class.getClassLoader().getResourceAsStream("djc_rsa_public_key_new.der");
            //InputStream open = new ClassPathResource("djc_rsa_public_key_new.der").getInputStream();
            try {
                bArr = new byte[open.available()];
                do {
                    try {
                    } catch (Exception e) {
                        e = e;
                        System.out.println("Got exception while is -> bytearr conversion: " + e);
                        return KeyFactory.getInstance(f11865b).generatePublic(new X509EncodedKeySpec(bArr));
                    }
                } while (open.read(bArr) != -1);
            } catch (Exception e2) {
                bArr = null;
            }
            return KeyFactory.getInstance(f11865b).generatePublic(new X509EncodedKeySpec(bArr));
        } catch (Exception e3) {
            return null;
        }
    }

    public static String b(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            str = hexString.length() == 1 ? str + "0" + hexString : str + hexString;
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(getDjcSign("DECC4DC6C39B1FC86E109B26F31C9105","147","832ed1ab5036d8bec44e4cc6c12c439d7544bb25eab1ffa9acec1abe6bd913ca"));
    }





}
