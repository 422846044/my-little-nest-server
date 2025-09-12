package top.zhongyingjie.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import top.zhongyingjie.common.enums.ResponseCodeEnum;

import java.time.Instant;

/**
 * 统一响应对象
 *
 * @param <T> 响应数据类型
 * @author Kong
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 业务消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳(毫秒)
     */
    private final long timestamp = Instant.now().toEpochMilli();

    /**
     * 成功 (无数据)
     *
     * @param <T> 响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> success() {
        return new Result<T>().setCode(ResponseCodeEnum.SUCCESS.getCode()).setMessage("success");
    }

    /**
     * 成功 (带数据)
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(ResponseCodeEnum.SUCCESS.getCode()).setMessage("success").setData(data);
    }

    /**
     * 失败 (标准错误码)
     *
     * @param code    响应码
     * @param message 响应消息
     * @param <T>     响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<T>().setCode(code).setMessage(message);
    }

    /**
     * 失败 (400 Bad Request)
     *
     * @param message 响应消息
     * @param <T>     响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> badRequest(String message) {
        return fail(ResponseCodeEnum.BAD_REQUEST.getCode(), message);
    }

    /**
     * 失败 (404 Not Found)
     *
     * @param resourceName 响应消息
     * @param <T>          响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> notFound(String resourceName) {
        return fail(ResponseCodeEnum.NOT_FOUND.getCode(), resourceName + " not found");
    }

    /**
     * 失败 (500 Server Error)
     *
     * @param message 响应消息
     * @param <T>     响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> serverError(String message) {
        return fail(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    /**
     * 通过响应码枚举封装失败响应
     *
     * @param responseCodeEnum 响应码枚举
     * @param <T>              响应数据类型
     * @return 统一返回对象
     */
    public static <T> Result<T> response(ResponseCodeEnum responseCodeEnum) {
        return fail(responseCodeEnum.getCode(), responseCodeEnum.getMsg());
    }

    /**
     * 追加额外消息 (适用于日志等场景)
     *
     * @param appendMsg 消息
     * @return 统一返回对象
     */
    public Result<T> appendMessage(String appendMsg) {
        if (this.message == null) {
            this.message = appendMsg;
        } else {
            this.message += " | " + appendMsg;
        }
        return this;
    }

    /**
     * 快速判断是否成功
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return this.code == ResponseCodeEnum.SUCCESS.getCode();
    }

    /**
     * 分页数据包装
     *
     * @param list  数据列表
     * @param total 总条数
     * @param <T>  列表数据类型
     * @return 统一返回对象
     */
    public static <T> Result<PageData<T>> page(Iterable<T> list, long total) {
        return success(new PageData<>(list, total));
    }

    /**
     * 分页数据对象
     *
     * @param <T> 列表类型
     */
    public static class PageData<T> {
        private final Iterable<T> list;
        private final long total;

        public PageData(Iterable<T> list, long total) {
            this.list = list;
            this.total = total;
        }

        public Iterable<T> getList() {
            return list;
        }

        public long getTotal() {
            return total;
        }
    }

    public int getCode() {
        return code;
    }

    /**
     * 链式调用，设置code值
     *
     * @param code 响应码
     * @return 统一返回对象
     */
    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 链式调用，设置message值
     *
     * @param message 响应消息
     * @return 统一返回对象
     */
    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    /**
     * 链式调用，设置data值
     *
     * @param data 响应数据
     * @return 统一返回对象
     */
    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
