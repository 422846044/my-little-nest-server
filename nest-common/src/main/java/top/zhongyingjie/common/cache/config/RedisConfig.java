package top.zhongyingjie.common.cache.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis配置类
 *
 * @author Kong
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    private static final String REDIS_PREFIX = "redis://";

    private static final int PING_CONNECTION_INTERVAL = 1000;

    @Value("${bumblebee.cache.timeout:7200}")
    private Integer timeout;

    /**
     * 当有多个管理器的时候，必须使用该注解在一个管理器上注释：表示该管理器为默认的管理器
     *
     * @param connectionFactory 链接工厂
     * @return 缓存
     */
    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //序列化方式
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair =
                RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer);
        RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        //设置过期时间
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(timeout));
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);

        //设置白名单
        /*
        使用fastjson的时候：序列化时将class信息写入，反解析的时候，
        fastjson默认情况下会开启autoType的检查，相当于一个白名单检查，
        如果序列化信息中的类路径不在autoType中，
        反解析就会报com.alibaba.fastjson.JSONException: autoType is not support的异常
        */
        JSONFactory.getDefaultObjectReaderProvider().addAutoTypeAccept("com.aitulan.");
        return cacheManager;
    }

    /**
     * 获取redisTemple实例
     *
     * @param lettuceConnectionFactory 连接工厂
     * @return redisTemple实例
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        //value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        //key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    /**
     * 获取redisson客户端
     *
     * @param redisProperties redis属性参数类
     * @return redisson客户端
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(RedisProperties redisProperties) {
        Config config = new Config();
        if (redisProperties.getSentinel() != null && !redisProperties.getSentinel().getNodes().isEmpty()) {
            // 哨兵模式
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
            List<String> sentinelAddress = new ArrayList<>();
            for (String node : redisProperties.getCluster().getNodes()) {
                sentinelAddress.add(REDIS_PREFIX + node);
            }
            sentinelServersConfig.setSentinelAddresses(sentinelAddress);
            if (CharSequenceUtil.isNotEmpty(redisProperties.getSentinel().getPassword())) {
                sentinelServersConfig.setSentinelPassword(redisProperties.getSentinel().getPassword());
            }
        } else if (redisProperties.getCluster() != null && !redisProperties.getCluster().getNodes().isEmpty()) {
            // 集群模式
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            List<String> clusterNodes = new ArrayList<>();
            for (String node : redisProperties.getCluster().getNodes()) {
                clusterNodes.add(REDIS_PREFIX + node);
            }
            clusterServersConfig.setNodeAddresses(clusterNodes);
            if (CharSequenceUtil.isNotEmpty(redisProperties.getPassword())) {
                clusterServersConfig.setPassword(redisProperties.getPassword());
            }
        } else {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(REDIS_PREFIX + redisProperties.getHost() + ":" + redisProperties.getPort());
            if (CharSequenceUtil.isNotEmpty(redisProperties.getPassword())) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
            singleServerConfig.setPingConnectionInterval(PING_CONNECTION_INTERVAL);
        }

        return Redisson.create(config);
    }

    /**
     * 自定义缓存key生成策略，默认将使用该策略
     *
     * @return 缓存key生成器
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            Map<String, Object> container = new HashMap<>(3);
            Class<?> targetClassClass = target.getClass();
            //类地址
            container.put("class", targetClassClass.toGenericString());
            //方法名称
            container.put("methodName", method.getName());
            //包名
            container.put("package", targetClassClass.getPackage());
            //参数列表
            for (int i = 0; i < params.length; i++) {
                container.put(String.valueOf(i), params[i]);
            }
            //转为JSON字符串
            String jsonString = JSON.toJSONString(container);
            //做SHA256 Hash计算，得到一个SHA256摘要作为Key
            return DigestUtils.sha256Hex(jsonString);
        };
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        //异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化-> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError", e);
            }
        };
    }

}
