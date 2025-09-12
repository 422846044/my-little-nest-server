package top.zhongyingjie.common.enums;

/**
 * 响应码枚举
 *
 * @author Kong
 */
public enum ResponseCodeEnum {

    SUCCESS(true, 200, "请求成功"),

    BAD_REQUEST(false, 400, "请求错误"),

    UNAUTHORIZED(false, 401, "用户未认证"),

    REFRESH_TOKEN_INVALID(false, 402, "长时间未登录"),

    FORBIDDEN(false, 403, "暂无权限访问该资源"),

    NOT_FOUND(false, 404, "请求url找不到"),

    MISS_REQUEST_HEADER(false, 411, "请求头缺失"),

    MISS_REQUEST_PARAMETER(false, 412, "请求参数缺失"),

    REQUEST_METHOD_NOT_SUPPORTED(false, 421, "请求方法不支持"),

    USER_LOGIN_ERROR(false, 510, "认证失败,账号或密码错误"),

    INTERNAL_SERVER_ERROR(false, 500, "服务器未知异常,无法完成请求"),

    LIMIT_ERROR(false, 511, "操作频繁，请稍候再试"),

    CAPTCHA_EXPIRE(false, 512, "验证码已过期"),

    CAPTCHA_ERROR(false, 513, "验证码错误"),

    PASSWORD_NO_SAME(false, 514, "新密码与确认密码不一致"),

    PASSWORD_ERROR(false, 515, "旧密码错误"),

    IMAGE_UPLOAD_ERROR(false, 516, "文件上传出错,请检查文件格式是否符合要求："
            + "支持'bmp、gif、jpg、jpeg、png、tar、zip、mp4、avi、rmvb、mp3、avi'等格式,文件名不超过100个字符"
            + ",单个文件大小不超过500MB,多文件大小和不超过1GB"),

    FILE_UPLOAD_ERROR(false, 517, "文件在上传过程中出错,请重试!"),

    SIGNATURE_ERROR(false, 518, "签名验证失败"),

    PARAM_CHECK_FAIL(false, 1001, "参数校验未通过"),

    PARAM_TYPE_ERROR(false, 1002, "参数类型有误"),

    PARAM_VALUE_OUT_OF_RANGE(false, 1003, "参数值超出范围"),

    PARAM_SIZE_OUT_OF_RANGE(false, 1004, "参数数量超出范围"),

    TOKEN_INVALID(false, 1020, "凭证无效"),

    TOKEN_EXPIRE(false, 1021, "凭证已过期"),

    REFRESH_TOKEN_EXPIRE(false, 1022, "刷新凭证已过期"),

    TOKEN_PARSE_ERROR(false, 1023, "凭证解析错误"),

    BUSINESS_ERROR(false, 1110, ""),

    BUSINESS_BUSY(false, 1111, "业务繁忙，请稍后再试"),

    OPERATE_FAIL(false, 1112, "操作失败，请稍后再试"),

    REPEAT_REGISTER(false, 1200, "重复注册"),

    GET_WX_PHONE_FAIL(false, 1201, "获取微信手机号失败"),

    REPEAT_NICKNAME(false, 1210, "该昵称已被使用，请更换后再试~"),

    DATA_NOT_FOUND(false, 1404, "未查询到相关数据"),

    PRODUCT_INVALID(false, 1500, "该商品已失效");

    private final Boolean success;

    private final Integer code;

    private final String msg;

    ResponseCodeEnum(Boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
