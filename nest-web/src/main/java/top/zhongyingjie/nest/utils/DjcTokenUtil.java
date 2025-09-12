package top.zhongyingjie.nest.utils;

/**
 * 道具城token工具类
 *
 * @author Kong
 */
public final class DjcTokenUtil {

    private static final int INT_5381 = 5381;

    private static final int INT_22 = 22;

    private static final int INT_32 = 32;

    private static final int INT_5 = 5;

    private DjcTokenUtil() {

    }

    public static String getGToken() {
        return Integer.toString(e());
    }

    /**
     * 获取pt
     *
     * @param accessToken 访问令牌
     * @return pt
     */
    public static String getPToken(String accessToken) {
        return Integer.toString(d(accessToken));
    }

    /**
     * 加密
     *
     * @return 数值
     */
    public static int e() {
        try {
            char[] charArray = "a1b2c3".toCharArray();
            int i = INT_5381;
            int length = charArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                i += (Integer.MAX_VALUE & (i << INT_5)) + String.valueOf(charArray).charAt(i2);
            }
            return i & Integer.MAX_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param accessToken 访问令牌
     * @return 数值
     */
    public static int d(String accessToken) {
        char[] charArray;
        try {
            charArray = accessToken.substring(INT_22, INT_32).toCharArray();
            int i = INT_5381;
            int length = charArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                i += (Integer.MAX_VALUE & (i << INT_5)) + String.valueOf(charArray).charAt(i2);
            }
            return i & Integer.MAX_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
