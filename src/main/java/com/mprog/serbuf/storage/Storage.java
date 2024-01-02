package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.CacheVal;
import com.mprog.serbuf.model.DatabaseInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public interface Storage {
    ConcurrentHashMap<String, CacheVal> getStorageByKey(String key);
    void setValToStorage(String collection, String key, String value);
    ConcurrentHashMap<String, CacheVal> setValToStorageAndReturn(String collection, String key, String value);
    void deleteValFromStorage(String collection, String key);
    void updateValInStorage(String collection, String key, String value);
    List<DatabaseInfo> getDBInfo(String db);
    String getValFromStorage(String collection, String key);
    void save(ConcurrentHashMap<String, CacheVal> map, String key);
    Map<String, Map<String, String>> getAllData();
    void createCollection(String collection);
}
