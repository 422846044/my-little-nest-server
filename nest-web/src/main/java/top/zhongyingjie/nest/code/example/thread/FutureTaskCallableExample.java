package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask和Callable线程任务示例
 *
 * @author Kong
 */
public class FutureTaskCallableExample {

    private static final Logger log = LoggerFactory.getLogger(FutureTaskCallableExample.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable1 = () -> "lambda";

        Callable<String> callable2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("Sleeping");
                Thread.sleep(5000);
                return "匿名类";
            }
        };

        FutureTask<String> futureTask1 = new FutureTask<>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);
        new Thread(futureTask1).start();
        Thread thread = new Thread(futureTask2);
        thread.start();
        log.info(futureTask1.get());
        log.info(futureTask2.get());
    }

}
