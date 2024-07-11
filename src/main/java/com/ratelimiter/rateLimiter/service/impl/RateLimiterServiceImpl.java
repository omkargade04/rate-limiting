package com.ratelimiter.rateLimiter.service.impl;


import com.ratelimiter.rateLimiter.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceImpl implements RateLimiterService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${rate.limiter.max.requests}")
    private int MAX_REQUEST;

    @Value("${rate.limiter.time.window.seconds}")
    private int TIME_WINDOW_IN_SECONDS;
//
    public RateLimiterServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isAllowed(String userId, String action) {
        String key = "rate_limiter:" + userId + ":" + action;
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        Integer currentCount = (Integer) valueOperations.get(key);

        if( currentCount == null ){
            valueOperations.set(key, 1, TIME_WINDOW_IN_SECONDS, TimeUnit.SECONDS);
            return true;
        } else if (currentCount < MAX_REQUEST) {
            valueOperations.increment(key);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setKeyValue(String key, Object value, long timeout, TimeUnit unit) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, unit);
    }

    @Override
    public Object getValue(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}
