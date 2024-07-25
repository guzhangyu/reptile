package com.learn.reptile.config.db;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * @Author zhangyugu
 * @Date 2020/11/30 9:55 上午
 * @Version 1.0
 */
public class RedisAutoCacheManager extends RedisCacheManager {

    private static final String SPLIT_FLAG = "#";
    private static final int CACHE_LENGTH = 2;

    public RedisAutoCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        if(StringUtils.isNotBlank(name) && name.contains("#")) {
            String[] cacheArray = name.split("#");
            if(cacheArray.length < 2) {
                return super.createRedisCache(name, cacheConfig);
            }else {
                if(cacheConfig!=null) {
                    long cacheAge = Long.parseLong(cacheArray[1]);
                    cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(cacheAge));
                }

                return super.createRedisCache(name, cacheConfig);
            }
        }else {
            return super.createRedisCache(name, cacheConfig);
        }
    }
}
