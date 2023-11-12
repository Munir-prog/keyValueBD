package com.mprog.serbuf.service.impl;

import com.mprog.serbuf.model.CacheVal;
import com.mprog.serbuf.service.MainCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MainCommandService implements MainCommand {

    private final ConcurrentHashMap<String, CacheVal> map = new ConcurrentHashMap<>();

    @Override
    public boolean clear(String key) {
        CacheVal val = map.remove(key);
        return val != null;
    }

    @Override
    public String get(String key) {
        CacheVal val = map.get(key);
        return val != null ? val.getValue() : null;
    }

    @Override
    public String getAndDelete(String key) {
        CacheVal val = map.remove(key);
        return val != null ? val.getValue() : null;
    }

    @Override
    public void set(String key, String value) {
        CacheVal oldVal = map.get(key);
        if (oldVal != null) {
            oldVal.setValue(value);
        } else {
            CacheVal val = CacheVal.builder()
                    .value(value).build();
            map.put(key, val);
        }
    }

    @Override
    public String append(String key, String value) {
        CacheVal val = map.get(key);
        if (val != null) {
            String old = val.getValue();
            String newVal = old != null ? old + value : value;
            val.setValue(newVal);
            return newVal;
        } else {
            CacheVal newCacheVal = buildCacheVal(value, null, null);
            map.put(key, newCacheVal);
            return value;
        }
    }

    @Override
    public void set(String key, String value, Integer expireInSeconds) {
        CacheVal val = buildCacheVal(value, expireInSeconds, LocalDateTime.now());
        map.put(key, val);
    }

    @Override
    public boolean exists(String key) {
        return map.containsKey(key);
    }

    private static CacheVal buildCacheVal(String value, Integer expireInSeconds, LocalDateTime localDateTime) {
        return CacheVal.builder()
                .value(value)
                .setDate(localDateTime)
                .expiration(expireInSeconds)
                .build();
    }

}
