package top.zhongyingjie.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import top.zhongyingjie.common.enums.ResponseCodeEnum;

import java.time.Instant;

/**
 * 统一响应对象
 *
 * @author atulan_zyj
 * @date 2025/4/2
 */

@Data
@Accessors(chain = true)
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
     */
    public static <T> Result<T> success() {
        return new Result<T>().setCode(ResponseCodeEnum.SUCCESS.getCode()).setMessage("success");
    }

    /**
     * 成功 (带数据)
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(T data) {
        return (Result<T>) success().setData(data);
    }

    /**
     * 失败 (标准错误码)
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<T>().setCode(code).setMessage(message);
    }

    /**
     * 失败 (400 Bad Request)
     */
    public static <T> Result<T> badRequest(String message) {
        return fail(ResponseCodeEnum.BAD_REQUEST.getCode(), message);
    }

    /**
     * 失败 (404 Not Found)
     */
    public static <T> Result<T> notFound(String resourceName) {
        return fail(ResponseCodeEnum.NOT_FOUND.getCode(), resourceName + " not found");
    }

    /**
     * 失败 (500 Server Error)
     */
    public static <T> Result<T> serverError(String message) {
        return fail(ResponseCodeEnum.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static <T> Result<T> response(ResponseCodeEnum responseCodeEnum){
        return fail(responseCodeEnum.getCode(),responseCodeEnum.getMsg());
    }

    /**
     * 追加额外消息 (适用于日志等场景)
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
     */
    public boolean isSuccess() {
        return this.code == ResponseCodeEnum.SUCCESS.getCode();
    }

    /**
     * 分页数据包装
     * @param list 数据列表
     * @param total 总条数
     */
    public static <T> Result<PageData<T>> page(Iterable<T> list, long total) {
        return success(new PageData<>(list, total));
    }

    /**
     * 分页数据对象
     */
    @Data
    public static class PageData<T> {
        private final Iterable<T> list;
        private final long total;

        public PageData(Iterable<T> list, long total) {
            this.list = list;
            this.total = total;
        }
    }

}
