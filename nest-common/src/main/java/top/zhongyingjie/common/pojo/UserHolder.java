package top.zhongyingjie.common.pojo;

/**
 * 用户持久化对象
 *
 * @author Kong
 */
public class UserHolder {

    private static final ThreadLocal<UserLoginInfo> TL = new ThreadLocal<>();

    /**
     * 设置用户信息
     *
     * @param userLoginInfo 用户信息
     */
    public static void setUserLoginInfo(UserLoginInfo userLoginInfo) {
        TL.set(userLoginInfo);
    }

    public static UserLoginInfo getUserLoginInfo() {
        return TL.get();
    }

    /**
     * 防止内存泄漏
     */
    public static void removeUserLoginInfo() {
        TL.remove();
    }
}
