package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.CacheVal;
import com.mprog.serbuf.service.StorageCreatorService;
import com.mprog.serbuf.service.impl.StorageService;
import com.mprog.serbuf.utils.StorageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;


@Service
@Primary
@RequiredArgsConstructor
public class MapDiskStorage implements Storage{

    private final StorageService storageService;
    private final StorageCreatorService storageCreatorService;


    @Override
    public ConcurrentHashMap<String, CacheVal> getStorageByKey(String key) {
        String storagePrefix = StorageUtils.getStoragePrefix(key);
        String storageName = storageCreatorService.getStorageNameIfCreated(storagePrefix);
        if (storageName != null) {
            return (ConcurrentHashMap<String, CacheVal>) storageService.read(storagePrefix);
        } else {
            return storageCreatorService.createStorage();
        }
    }

    @Async
    @Override
    public void save(ConcurrentHashMap<String, CacheVal> map, String key) {
        String storagePrefix = StorageUtils.getStoragePrefix(key);
        storageService.save(map, storagePrefix);
        storageCreatorService.setCreatedStorage(storagePrefix);
    }


}
