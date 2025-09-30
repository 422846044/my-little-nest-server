package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread join方法示例
 *
 * @author Kong
 */
public class ThreadJoinExample {

    private static final Logger log = LoggerFactory.getLogger(ThreadJoinExample.class);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info("t1 is running");
        });
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
                log.info("t2 is running");
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();
                log.info("t3 is running");
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
