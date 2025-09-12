package top.zhongyingjie.common.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存接口
 *
 * @param <T> 变量类型
 * @author Kong
 */
public interface Cache<T> {

    /**
     * 获取缓存值
     *
     * @param key 缓存键
     * @return 缓存值
     */
    T get(Object key);

    /**
     * 获取字符串类型缓存值
     *
     * @param key 缓存键
     * @return 字符串类型缓存值
     */
    String getStr(Object key);

    /**
     * 放入缓存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    void put(Object key, T value);

    /**
     * 放入缓存并设置过期时间
     *
     * @param key      缓存键
     * @param value    缓存值
     * @param exp      失效时间数
     * @param timeUnit 失效时间单位
     */
    void putAndExp(Object key, T value, Long exp, TimeUnit timeUnit);

    /**
     * 删除缓存
     *
     * @param key 缓存键
     * @return 是否成功删除
     */
    Boolean delete(Object key);

    /**
     * 批量获取缓存值
     *
     * @param keys 缓存键集合
     * @return 缓存值列表
     */
    List<T> multiGet(Collection<String> keys);

    /**
     * 将Map集合元素放入缓存
     *
     * @param map 元素集合
     */
    void multiPut(Map map);

    /**
     * 批量删除缓存
     *
     * @param keys 缓存键集合
     */
    void multiDel(Collection keys);

    /**
     * 模糊删除缓存
     *
     * @param key 缓存值前缀
     */
    void vagueDel(Object key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 获取缓存哈希值
     *
     * @param key     缓存键
     * @param hashKey 缓存哈希键
     * @return 哈希值
     */
    T getHash(Object key, Object hashKey);

    /**
     * 获取缓存哈希表
     *
     * @param key 缓存键
     * @return 哈希表
     */
    Map<Object, Object> getHash(Object key);

    /**
     * 放入缓存哈希值
     *
     * @param key       缓存键
     * @param hashKey   缓存哈希键
     * @param hashValue 缓存哈希值
     */
    void putHash(Object key, Object hashKey, Object hashValue);


    /**
     * 将Map放入缓存哈希表
     *
     * @param key 缓存键
     * @param map 缓存哈希表
     */
    void putAllHash(Object key, Map map);

    /**
     * 根据哈希键删除哈希表数据
     *
     * @param key     缓存键
     * @param hashKey 哈希键
     */
    void deleteHash(Object key, Object hashKey);

    /**
     * 是否存在缓存键
     *
     * @param key 缓存键
     * @return 是否存在缓存键
     */
    boolean hasKey(Object key);


    /**
     * 根据前缀匹配所有缓存键
     *
     * @param pattern 前缀
     * @return 缓存键列表
     */
    List<Object> keys(String pattern);


    /**
     * redis 计数器累加
     * 注：到达liveTime之后，该次增加取消，即自减1，而不是redis值为0
     *
     * @param key      为累计的key，同一key每次调用则+1
     * @param liveTime 单位秒后失效
     * @return 计数器结果
     */
    Long incr(String key, long liveTime);


}
