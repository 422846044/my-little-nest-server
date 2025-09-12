package top.zhongyingjie.common.constant;

/**
 * 错误码枚举
 *
 * @author Kong
 */
public enum ErrorCode {

    SUCCESS(200, "ok"),

    FAIL(500, "error"),

    USERNAME_PASSWORD_ERROR(1001, "用户名或密码错误！！"),

    TOKEN_NOT_FOUND(2001, "没有token，请重新登录!!!"),

    TOKEN_INVALID(2001, "token验证失败，请重新登录！！");

    private final int code;
    private final String message;

    /**
     * 使用构造函数给每个枚举常量赋值
     *
     * @param code 错误码
     * @param message 错误信息
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

