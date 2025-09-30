package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch线程顺序执行示例
 *
 * @author Kong
 */
public class CountDownLatchThreadExample {

    private static final Logger log = LoggerFactory.getLogger(CountDownLatchThreadExample.class);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl1 = new CountDownLatch(1);
        CountDownLatch cdl2 = new CountDownLatch(1);
        CountDownLatch cdl3 = new CountDownLatch(1);

        new Mythread("t1", cdl1).start();
        cdl1.await();
        new Mythread("t2", cdl2).start();
        cdl2.await();
        new Mythread("t3", cdl3).start();
        cdl3.await();
    }

    static class Mythread extends Thread {

        private final String name;

        private final CountDownLatch cdl;

        Mythread(String name, CountDownLatch cdl) {
            this.name = name;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            try {
                log.info("{} is running", name);
            } finally {
                cdl.countDown();
            }
        }
    }
}
