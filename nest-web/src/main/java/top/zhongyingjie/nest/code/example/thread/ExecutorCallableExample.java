package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Executor和Callable线程任务示例
 *
 * @author Kong
 */
public class ExecutorCallableExample {

    private static final Logger log = LoggerFactory.getLogger(ExecutorCallableExample.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<String> callable1 = () -> "lambda";

        Callable<String> callable2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("Sleeping");
                Thread.sleep(5000);
                return "匿名类";
            }
        };
        Future<String> future1 = executorService.submit(callable1);
        Future<String> future2 = executorService.submit(callable2);

        log.info(future1.get());
        log.info(future2.get());
    }

}
