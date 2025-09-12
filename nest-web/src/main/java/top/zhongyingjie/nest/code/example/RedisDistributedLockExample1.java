package top.zhongyingjie.nest.code.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁示例1
 *
 * @author Kong
 */
@Component
public class RedisDistributedLockExample1 {

    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLockExample1.class);

    private final StringRedisTemplate stringRedisTemplate;

    public RedisDistributedLockExample1(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 尝试获取redis分布式锁
     *
     * @param lockName 锁名称
     * @return 获取结果
     */
    public boolean tryLock(String lockName) {
        // 获取线程id
        String currentThreadId = Long.toString(Thread.currentThread().getId());
        ValueOperations<String, String> redisOperations = stringRedisTemplate.opsForValue();
        // 获取锁的值
        String lockValue = redisOperations.get(lockName);
        if (lockValue == null) {
            // 为空则尝试获取锁，并且设置过期时间
            boolean result = Boolean.TRUE.equals(redisOperations
                    .setIfAbsent(lockName, currentThreadId + "_1", 3, TimeUnit.SECONDS));
            if (result) {
                log.info("进程[{}]获取锁", currentThreadId);
            }
            return result;
        }
        // 若不为空，说明存在锁，判断是否为线程持有的锁
        String[] idAndCount = lockValue.split("_");
        if (idAndCount.length == 2 && currentThreadId.equals(idAndCount[0])) {
            // 相同，则添加重入次数
            int count = Integer.parseInt(idAndCount[1]);
            boolean result = Boolean.TRUE.equals(redisOperations
                    .setIfPresent(lockName, currentThreadId + "_" + ++count, 3, TimeUnit.SECONDS));
            if (result) {
                log.info("进程[{}]获取锁", currentThreadId);
            }
            return result;
        }
        // 若非当前线程则获取失败
        return false;
    }

    /**
     * 释放redis分布式锁
     *
     * @param lockName 锁名称
     */
    public void unlock(String lockName) {
        // 获取线程id
        String currentThreadId = Long.toString(Thread.currentThread().getId());
        ValueOperations<String, String> redisOperations = stringRedisTemplate.opsForValue();
        // 获取锁的值
        String lockValue = redisOperations.get(lockName);
        if (lockValue != null) {
            // 若不为空，判断是否为线程持有的锁
            String[] idAndCount = lockValue.split("_");
            if (idAndCount.length == 2 && currentThreadId.equals(idAndCount[0])) {
                // 相同，则减少重入次数
                int count = Integer.parseInt(idAndCount[1]);
                if (count > 1) {
                    redisOperations.setIfPresent(lockName, currentThreadId + "_" + --count, 3, TimeUnit.SECONDS);
                } else {
                    stringRedisTemplate.delete(lockName);
                }
                log.info("进程[{}]释放锁", currentThreadId);
            }
        }
    }
}
