package top.zhongyingjie.nest.code.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池超时处理示例1
 *
 * @author Kong
 */
public class ThreadPoolSettingTimeOutExample1 {


    private static final Logger log = LoggerFactory.getLogger(ThreadPoolSettingTimeOutExample1.class);

    /**
     * 睡眠时间
     */
    private static final int SLEEP = 5000;

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // 创建任务
        Callable<String> task = () -> {
            // 设置睡眠时间模拟超时
            Thread.sleep(SLEEP);
            return "complete";
        };
        // 提交任务
        Future<String> future = executor.submit(task);
        try {
            // 设置任务执行超时时间
            String result = future.get(1, TimeUnit.SECONDS);
            // 输出执行结果
            log.info(result);
        } catch (InterruptedException e) {
            log.error("线程中断异常：{}", e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error("任务执行异常：{}", e.getMessage(), e);
        } catch (TimeoutException e) {
            log.error("任务执行超时");
            // 超时取消任务
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
    }
}
