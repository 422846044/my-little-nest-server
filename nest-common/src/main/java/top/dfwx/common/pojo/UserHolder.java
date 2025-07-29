package top.dfwx.common.pojo;


public class UserHolder {
    private static final ThreadLocal<UserLoginInfo> TL = new ThreadLocal<>();
    public static void setUserLoginInfo(UserLoginInfo userLoginInfo){
        TL.set(userLoginInfo);
    }
    public static UserLoginInfo getUserLoginInfo(){
        return TL.get();
    }

    /**
     * 防止内存泄漏
     */
    public static void removeUserLoginInfo(){
        TL.remove();
    }
}
