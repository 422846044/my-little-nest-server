package top.zhongyingjie.nest.code.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

/**
 * 滑动窗口限流
 *
 * @author Kong
 */
@Component
public class SlidingWindowRateLimiter {

    private static final Logger log = LoggerFactory.getLogger(SlidingWindowRateLimiter.class);

    /**
     * 窗口大小
     */
    private final long windowSize = 60 * 1000;

    /**
     * 限制数量
     */
    private final int limit = 3;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * lua脚本
     */
    private final String luaScript = "local window_start = ARGV[1] - tonumber(ARGV[4])\n" +
            "redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, window_start)\n" +
            "local count = redis.call('ZCARD', KEYS[1])\n" +
            "if count < tonumber(ARGV[2]) then\n" +
            "redis.call('ZADD', KEYS[1], ARGV[1], ARGV[3])\n" +
            "return '1'\n" +
            "else\n" +
            "return '0'\n" +
            "end";

    /**
     * 限流模拟
     *
     * @param key 拦截key
     * @return 是否限流
     */
    public boolean allowRequest(String key) {
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        String result = stringRedisTemplate.execute(new DefaultRedisScript<>(luaScript, String.class),
                Collections.singletonList(key),
                Long.toString(currentTimeMillis), Integer.toString(limit),
                UUID.randomUUID().toString(), Long.toString(windowSize));
        return result.equals("1");
    }

}
