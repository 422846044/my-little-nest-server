package top.zhongyingjie.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine本地缓存配置
 */
@Configuration
public class CacheConfig {

    private static final long EXPIRE = 3200L;

    /**
     * 获取七牛云上传令牌
     *
     * @return 七牛云上传令牌
     */
    @Bean("qiniuCache")
    public Cache<String, String> qiniuUploadToken() {
        return Caffeine.newBuilder()
                // 设置最后一次写入后经过固定时间过期
                .expireAfterWrite(EXPIRE, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                // 缓存的最大条数
                .build();
    }

}

