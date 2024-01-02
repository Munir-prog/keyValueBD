package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.CacheVal;
import com.mprog.serbuf.model.DatabaseInfo;
import com.mprog.serbuf.model.ValueDto;
import com.mprog.serbuf.service.ReadService;
import com.mprog.serbuf.service.StorageCreatorService;
import com.mprog.serbuf.utils.StorageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "sebuf", name = "disk")
public class MapDiskStorage implements Storage{

    private final DatabaseInfoService databaseInfoService;
    private final ReadService readService;
    private final WriteService writeService;
    private final UpdateService updateService;
    private final DeleteService deleteService;
    private final StorageCreatorService storageCreatorService;


    @Override
    public void setValToStorage(String collection, String key, String value) {
        try {
            writeService.setValToStorage(collection, key, value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConcurrentHashMap<String, CacheVal> setValToStorageAndReturn(String collection, String key, String value) {
        return null;
    }

    @Override
    public String getValFromStorage(String collection, String key) {
        ValueDto valFromStorage = readService.getValFromStorage(collection, key);
        return valFromStorage.getValue();
    }

    @Override
    public void updateValInStorage(String collection, String key, String value) {
        updateService.updateValInStorage(collection, key, value);
    }

    @Override
    public void deleteValFromStorage(String collection, String key) {
        deleteService.deleteValFromStorage(collection, key);
    }

    @Override
    public List<DatabaseInfo> getDBInfo(String db) {
        return databaseInfoService.getDBInfo(db);
    }











    @Override
    public ConcurrentHashMap<String, CacheVal> getStorageByKey(String key) {
        String storagePrefix = StorageUtils.getStoragePrefix(key);
        String storageName = storageCreatorService.getStorageNameIfCreated(storagePrefix);
        if (storageName != null) {
//            return (ConcurrentHashMap<String, CacheVal>) databaseInfoService.read(storagePrefix);
            return null;
        } else {
            return storageCreatorService.createStorage();
        }
    }

    @Override
    public void save(ConcurrentHashMap<String, CacheVal> map, String key) {
//        String storagePrefix = StorageUtils.getStoragePrefix(key);
//        databaseInfoService.save(map, storagePrefix);
//        storageCreatorService.setCreatedStorage(storagePrefix);
    }

    @Override
    public Map<String, Map<String, String>> getAllData() {
        return null;
    }

    @Override
    public void createCollection(String collection) {
//        try {
//            databaseInfoService.createCollection(collection);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }


}
