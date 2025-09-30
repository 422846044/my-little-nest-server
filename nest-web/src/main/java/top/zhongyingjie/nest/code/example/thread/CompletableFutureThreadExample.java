package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture顺序执行示例
 *
 * @author Kong
 */
public class CompletableFutureThreadExample {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureThreadExample.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(new Mythread("t1"))
                .thenRunAsync(new Mythread("t2"))
                .thenRunAsync(new Mythread("t3"))
                        .toCompletableFuture();
        voidCompletableFuture.get();
    }

    static class Mythread extends Thread {

        private final String name;

        Mythread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            log.info("{} is running", name);
        }
    }
}
