package com.mprog.serbuf.service.impl;

import com.mprog.serbuf.model.CacheVal;
import com.mprog.serbuf.service.MainCommand;
import com.mprog.serbuf.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MainCommandService implements MainCommand {

    private final Storage storage;


    @Override
    public boolean clear(String key) {
        var map = storage.getStorageByKey(key);
        CacheVal val = map.remove(key);
        storage.save(map, key);
        return val != null;
    }

    @Override
    public String get(String key) {
        var map = storage.getStorageByKey(key);
        CacheVal val = map.get(key);
        return val != null ? val.getValue() : null;
    }

    @Override
    public String getAndDelete(String key) {
        var map = storage.getStorageByKey(key);
        CacheVal val = map.remove(key);
        storage.save(map, key);
        return val != null ? val.getValue() : null;

    }

    @Override
    public void set(String key, String value) {
        var map = storage.getStorageByKey(key);
        CacheVal val = CacheVal.builder()
                .value(value).build();
        map.put(key, val);
        storage.save(map, key);
    }

    @Override
    public synchronized String append(String key, String value) {
        var map = storage.getStorageByKey(key);
        CacheVal val = map.get(key);
        if (val != null) {
            String old = val.getValue();
            String newVal = old != null ? old + value : value;
            val.setValue(newVal);
            storage.save(map, key);
            return newVal;
        } else {
            CacheVal newCacheVal = buildCacheVal(value, null, null);
            map.put(key, newCacheVal);
            storage.save(map, key);
            return value;
        }
    }

    @Override
    public void set(String key, String value, Integer expireInSeconds) {
        var map = storage.getStorageByKey(key);
        CacheVal val = buildCacheVal(value, expireInSeconds, LocalDateTime.now());
        map.put(key, val);
        storage.save(map, key);
    }

    @Override
    public boolean exists(String key) {
        var map = storage.getStorageByKey(key);
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
