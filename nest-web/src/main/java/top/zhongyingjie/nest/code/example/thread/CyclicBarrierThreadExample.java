package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier线程顺序执行示例
 *
 * @author Kong
 */
public class CyclicBarrierThreadExample {

    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierThreadExample.class);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cb = new CyclicBarrier(2);

        new Mythread("t1", cb).start();
        cb.await();
        new Mythread("t2", cb).start();
        cb.await();
        new Mythread("t3", cb).start();
        cb.await();
    }

    static class Mythread extends Thread {

        private final String name;

        private final CyclicBarrier cb;

        Mythread(String name, CyclicBarrier cb) {
            this.name = name;
            this.cb = cb;
        }

        @Override
        public void run() {
            try {
                log.info("{} is running", name);
            } finally {
                try {
                    cb.await();
                } catch (Exception e) {
                    log.warn("{} has thrown exception", name, e);
                }
            }
        }
    }
}
