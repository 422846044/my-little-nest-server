package top.zhongyingjie.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;


/**
 * 线程池配置类
 *
 * @author Kong
 */
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolConfig.class);

    /**
     * 核心线程数（默认线程数）
     */
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 2 * Runtime.getRuntime().availableProcessors();

    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private static final int KEEP_ALIVE_TIME = 5;

    /**
     * 任务的等待时间
     */
    private static final int AWAIT_TERMINATION_TIME = 30;

    /**
     * 缓冲队列数
     */
    private static final int QUEUE_CAPACITY = 1200;

    /**
     * 自定义拒绝策略睡眠时间
     */
    private static final int CUSTOMIZE_REJECTED_EXECUTION_SLEEP = 5000;

    /**
     * 线程池名前缀
     */
    private static final String THREAD_NAME_PREFIX = "log-thread-pool";

    /**
     * 获取异步日志线程池
     *
     * @return 异步日志线程池
     */
    @Bean(name = "logAsyncService")
    public ThreadPoolTaskExecutor logAsyncService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setAwaitTerminationMillis(AWAIT_TERMINATION_TIME);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        //使用自定义线程拒绝策略，拒绝后休眠五秒之后再次排队
        executor.setRejectedExecutionHandler(customizeRejectedExecutionHandler());
        return executor;
    }

    /**
     * 获取自定义拒绝策略
     *
     * @return 自定义拒绝策略
     */
    public RejectedExecutionHandler customizeRejectedExecutionHandler() {
        return (r, executor) -> {
            if (!executor.isShutdown()) {
                try {
                    log.info("日志线程队列已满，进入休眠");
                    Thread.sleep(CUSTOMIZE_REJECTED_EXECUTION_SLEEP);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                //再次尝试入队
                executor.execute(r);
            }
        };
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            /*log.error("====================捕获线程异常=================");
            log.error("错误信息:{}", throwable.getMessage());
            log.error("调用的方法:{}", method.getName());
            log.error("参数列表:{}", objects);
            log.error("===============================================");*/
        };
    }
}


