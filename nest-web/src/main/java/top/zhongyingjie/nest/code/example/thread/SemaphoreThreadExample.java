package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

/**
 * Semaphore线程顺序执行示例
 *
 * @author Kong
 */
public class SemaphoreThreadExample {

    private static final Logger log = LoggerFactory.getLogger(SemaphoreThreadExample.class);

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        new Mythread("t1", semaphore).start();
        semaphore.acquire();
        new Mythread("t2", semaphore).start();
        semaphore.acquire();
        new Mythread("t3", semaphore).start();
    }

    static class Mythread extends Thread {

        private final String name;

        private final Semaphore semaphore;

        Mythread(String name, Semaphore semaphore) {
            this.name = name;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                log.info("{} is running", name);
            } finally {
                semaphore.release();
            }
        }
    }
}
