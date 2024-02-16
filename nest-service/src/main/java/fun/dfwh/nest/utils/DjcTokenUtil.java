package fun.dfwh.nest.utils;

public class DjcTokenUtil {
    public static String getGToken(){
        return Integer.toString(e());
    }

    public static String getPToken(String accessToken){
        return Integer.toString(d(accessToken));
    }

    public static int e() {
        try {
            char[] charArray = "a1b2c3".toCharArray();
            int i = 5381;
            int length = charArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                i += (Integer.MAX_VALUE & (i << 5)) + String.valueOf(charArray).charAt(i2);
            }
            return i & Integer.MAX_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int d(String accessToken) {
        char[] charArray;
        try {
            charArray = accessToken.substring(22, 32).toCharArray();
            int i = 5381;
            int length = charArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                i += (Integer.MAX_VALUE & (i << 5)) + String.valueOf(charArray).charAt(i2);
            }
            return i & Integer.MAX_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(d("8DE4ADE624AADE34FFC444091ADF944F"));
    }
}
