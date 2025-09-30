package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock实现3线程顺序输出0-100示例
 *
 * @author Kong
 */
public class ReentrantLockPrint100Example {

    private static final Logger log = LoggerFactory.getLogger(ReentrantLockPrint100Example.class);

    private static int count = 0;

    private static final int MAX = 100;

    private static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        List<Condition> conditions = new ArrayList<Condition>();
        for (int i = 0; i < 3; i++) {
            // 为每个线程分配一个条件
            Condition condition = LOCK.newCondition();
            conditions.add(condition);
            new Thread(new MyThread(i, conditions)).start();
        }
    }

    private static class MyThread implements Runnable {

        private final int i;

        private final List<Condition> conditions;

        MyThread(int i, List<Condition> conditions) {
            this.i = i;
            this.conditions = conditions;
        }

        private void signalNext() {
            conditions.get(((i + 1) % 3)).signal();
        }

        @Override
        public void run() {
            while (true) {
                LOCK.lock();
                // 取模来分配线程应该输出的数字
                try {
                    if (count % 3 != i) {
                        // 不满足条件则等待
                        try {
                            // 放入这个特定的条件队列中等待，并且释放锁LOCK
                            conditions.get(i).await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (count > MAX) {
                        // 通知下一个线程，保证能正常退出
                        signalNext();
                        return;
                    }
                    log.info("线程[{}]打印[{}]", i, count);
                    count++;
                    // 执行完毕，通知下一个节点执行
                    signalNext();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }
}
