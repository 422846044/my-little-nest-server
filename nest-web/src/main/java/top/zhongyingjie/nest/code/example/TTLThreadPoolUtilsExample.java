package top.zhongyingjie.nest.code.example;

import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TTL线程池工具示例
 *
 * @author Kong
 */
public class TTLThreadPoolUtilsExample {

    /**
     * 使用TtlExecutors包装原始线程，兼容TTL
     */
    private static final ExecutorService TTL_EXECUTOR_SERVICE = TtlExecutors.getTtlExecutorService(
            Executors.newFixedThreadPool(10));

    public static ExecutorService getExecutorService() {
        return TTL_EXECUTOR_SERVICE;
    }
}
