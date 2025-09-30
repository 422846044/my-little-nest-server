package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Synchronized实现3线程顺序输出0-100示例
 *
 * @author Kong
 */
public class SynchronizedPrint100Example {

    private static final Logger log = LoggerFactory.getLogger(SynchronizedPrint100Example.class);

    private static volatile int count = 0;

    private static final int MAX = 100;

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new MyThread(i)).start();
        }
    }

    private static class MyThread implements Runnable {

        private final int i;

        MyThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while (count < MAX) {
                synchronized (MyThread.class) {
                    // 取模来分配线程应该输出的数字 自旋等待条件满足
                    while (count % 3 != i) {
                        try {
                            MyThread.class.wait();
                        } catch (InterruptedException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                    // 防止被阻塞的线程再唤醒时继续执行超过100
                    if (count > MAX) {
                        return;
                    }
                    log.info("线程[{}]打印[{}]", i, count);
                    count++;
                    MyThread.class.notifyAll();
                }
            }
        }
    }
}
