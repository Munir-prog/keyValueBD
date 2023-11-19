package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.CacheVal;

import java.util.concurrent.ConcurrentHashMap;

public interface Storage {
    ConcurrentHashMap<String, CacheVal> getStorageByKey(String key);
    void save(ConcurrentHashMap<String, CacheVal> map, String key);
}
