package com.kooixiuhong.api.common.utils;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    public <T> T retrieveFromCache(RedisTemplate<String, T> template, String key) {
        BoundValueOperations<String, T> boundValueOperations = template.boundValueOps(key);
        return boundValueOperations.get();
    }

    public <T> void storeInCache(T entity, RedisTemplate<String, T> template, String key, long expiry) {
        BoundValueOperations<String, T> boundValueOperations = template.boundValueOps(key);
        boundValueOperations.set(entity);
        boundValueOperations.expire(expiry, TimeUnit.MINUTES);
    }

}
