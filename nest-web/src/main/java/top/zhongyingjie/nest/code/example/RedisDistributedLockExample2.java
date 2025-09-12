package top.zhongyingjie.nest.code.example;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * redis分布式锁示例2
 *
 * @author Kong
 */
@Component
public class RedisDistributedLockExample2 {

    private final RedisTemplate<String, Boolean> redisTemplate;

    public RedisDistributedLockExample2(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final String tryLockLuaScript = "local lockValue = redis.call('get', KEYS[1])\n" +
            "if lockValue == false then\n" +
            "    redis.call('setex', KEYS[1], ARGV[2], ARGV[1] .. '_1')\n" +
            "    return true\n" +
            "else\n" +
            "    local parts = {}\n" +
            "    local index = 0\n" +
            "    for match in (lockValue .. \"_\"):gmatch(\"(.-)\" .. \"_\") do\n" +
            "        parts[index] = match\n" +
            "        index = index + 1\n" +
            "    end\n" +
            "    if parts[0] == ARGV[1] then\n" +
            "        local count = tonumber(parts[1]) + 1\n" +
            "        redis.call('setex', KEYS[1], ARGV[2], ARGV[1] .. '_' .. count)\n" +
            "        return true\n" +
            "    end\n" +
            "end\n" +
            "return false\n";

    private final String unlockLuaScript = "local lockValue = redis.call('get', KEYS[1])\n" +
            "if lockValue ~= false then\n" +
            "    local parts = {}\n" +
            "    local index = 0\n" +
            "    for match in (lockValue .. \"_\"):gmatch(\"(.-)\" .. \"_\") do\n" +
            "        parts[index] = match\n" +
            "        index = index + 1\n" +
            "    end\n" +
            "    if parts[0] == ARGV[1] then\n" +
            "        local count = tonumber(parts[1])\n" +
            "        if count > 1 then\n" +
            "            count = count - 1\n" +
            "            redis.call('set', KEYS[1], ARGV[1] .. '_' .. count)\n" +
            "        else\n" +
            "            redis.call('del', KEYS[1])\n" +
            "        end\n" +
            "        return true\n" +
            "    end\n" +
            "end\n" +
            "return false";

    /**
     * 尝试获取redis分布式锁
     *
     * @param lockName 锁名称
     * @return 获取结果
     */
    public boolean tryLock(String lockName) {
        // 获取线程id
        String currentThreadId = Long.toString(Thread.currentThread().getId());

        return redisTemplate.execute(new DefaultRedisScript<>(tryLockLuaScript, Boolean.class),
                Collections.singletonList(lockName), currentThreadId, 3);
    }

    /**
     * 释放redis分布式锁
     *
     * @param lockName 锁名称
     * @return 是否执行解锁成功
     */
    public boolean unlock(String lockName) {
        // 获取线程id
        String currentThreadId = Long.toString(Thread.currentThread().getId());
        return redisTemplate.execute(new DefaultRedisScript<>(unlockLuaScript, Boolean.class),
                Collections.singletonList(lockName), currentThreadId);
    }
}
