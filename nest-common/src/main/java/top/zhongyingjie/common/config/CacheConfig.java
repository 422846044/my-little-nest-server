package top.zhongyingjie.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheConfig {

    @Bean("qiniuCache")
    public Cache<String, String> qiniuUploadToken() {
        return Caffeine.newBuilder()
                // 设置最后一次写入后经过固定时间过期
                // 初始的缓存空间大小
                // 缓存的最大条数
                .build();
    }

}

