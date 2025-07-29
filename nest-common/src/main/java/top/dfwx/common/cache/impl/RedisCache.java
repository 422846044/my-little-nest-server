package top.dfwx.common.cache.impl;

import top.dfwx.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * redis缓存实现
 *
 * @author atulan_zyj
 * @date 2024/4/22
 */
@Component
public class RedisCache implements Cache {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 获取缓存值
     *
     * @param key 缓存键
     * @return
     */
    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取字符串类型缓存值
     *
     * @param key 缓存键
     * @return
     */
    @Override
    public String getStr(Object key) {
        try {
            return redisTemplate.opsForValue().get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 放入缓存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    @Override
    public void put(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 放入缓存并设置过期时间
     *
     * @param key      缓存键
     * @param value    缓存值
     * @param exp      失效时间数
     * @param timeUnit 失效时间单位
     */
    @Override
    public void putAndExp(Object key, Object value, Long exp, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, exp, timeUnit);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     * @return
     */
    @Override
    public Boolean delete(Object key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量获取缓存值
     *
     * @param keys 缓存键集合
     * @return
     */
    @Override
    public List multiGet(Collection keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 将Map集合元素放入缓存
     *
     * @param map 元素集合
     */
    @Override
    public void multiPut(Map map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 批量删除缓存
     *
     * @param keys 缓存键集合
     */
    @Override
    public void multiDel(Collection keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 模糊删除缓存
     *
     * @param key 缓存值前缀
     */
    @Override
    public void vagueDel(Object key) {
        List<Object> keys = keys(key + "*");
        multiDel(keys);
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        List<Object> keys = keys("*");
        multiDel(keys);
    }

    /**
     * 获取缓存哈希值
     *
     * @param key     缓存键
     * @param hashKey 缓存哈希键
     * @return
     */
    @Override
    public Object getHash(Object key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取缓存哈希值
     *
     * @param key 缓存键
     * @return
     */
    @Override
    public Map<Object, Object> getHash(Object key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 放入缓存哈希值
     *
     * @param key       缓存键
     * @param hashKey   缓存哈希键
     * @param hashValue 缓存哈希值
     */
    @Override
    public void putHash(Object key, Object hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }


    /**
     * 将Map放入缓存哈希表
     *
     * @param key 缓存键
     * @param map 缓存哈希表
     */
    @Override
    public void putAllHash(Object key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public void deleteHash(Object key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 是否存在缓存键
     *
     * @param key 缓存键
     * @return
     */
    @Override
    public boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据前缀匹配所有缓存键
     *
     * @param pattern 前缀
     * @return
     */
    @Override
    public List<Object> keys(String pattern) {
        List<Object> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            //符合条件的key
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }

    /**
     * redis 计数器累加
     * 注：到达liveTime之后，该次增加取消，即自减1，而不是redis值为0
     *
     * @param key      为累计的key，同一key每次调用则+1
     * @param liveTime 单位秒后失效
     * @return 计数器结果
     */
    @Override
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        //初始设置过期时间
        if (increment == 0 && liveTime > 0) {
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }

        return increment;
    }


    /**
     * redis scan扫描
     *
     * @param pattern  表达式
     * @param consumer 对迭代到的key进行操作
     */
    private void scan(String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor =
                         connection.scan(ScanOptions.scanOptions()
                                 .count(Long.MAX_VALUE)
                                 .match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            }
        });
    }
}
