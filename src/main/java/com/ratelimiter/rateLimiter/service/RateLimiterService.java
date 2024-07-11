package com.ratelimiter.rateLimiter.service;

import java.util.concurrent.TimeUnit;

public interface RateLimiterService {
    boolean isAllowed(String userId, String action);
    void setKeyValue(String key, Object value, long timeout, TimeUnit unit);
    Object getValue(String key);
}

