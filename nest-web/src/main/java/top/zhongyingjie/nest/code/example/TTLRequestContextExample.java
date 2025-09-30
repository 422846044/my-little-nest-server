package top.zhongyingjie.nest.code.example;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 使用TTL传递请求上下文示例
 *
 * @author Kong
 */
public class TTLRequestContextExample {

    private static final ThreadLocal<String> REQUEST_ID_TL = new TransmittableThreadLocal<>();

    /**
     * 保存请求id
     *
     * @param requestId 请求id
     */
    public static void setRequestId(String requestId) {
        REQUEST_ID_TL.set(requestId);
    }

    public static String getRequestId() {
        return REQUEST_ID_TL.get();
    }

    /**
     * 清除请求id
     */
    public static void clearRequestId() {
        REQUEST_ID_TL.remove();
    }

}
