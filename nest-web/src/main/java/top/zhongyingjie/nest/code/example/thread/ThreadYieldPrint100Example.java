package top.zhongyingjie.nest.code.example.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread yield方法实现3线程按顺序输出0-100
 *
 * @author Kong
 */
public class ThreadYieldPrint100Example {

    private static final Logger log = LoggerFactory.getLogger(ThreadYieldPrint100Example.class);

    private static final int MAX = 1000;

    private static int count = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            int index = i;
            new Thread(() -> {
                while (count < MAX) {
                    while (count % 3 != index) {
                        // 尝试让出资源给其他线程，若没让出继续让，直到有线程匹配上
                        Thread.yield();
                    }
                    if (count > MAX) {
                        return;
                    }
                    log.info("线程[{}]打印[{}]", index, count);
                    count++;
                }
            }).start();
        }
    }
}
