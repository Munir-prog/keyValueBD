package com.mprog.serbuf.service;

import com.mprog.serbuf.model.CacheVal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StorageCreatorService {

    private final CopyOnWriteArrayList<String> createdStorages = new CopyOnWriteArrayList<>();

    public String getStorageNameIfCreated(String storagePrefix) {
        if (createdStorages.contains(storagePrefix))
            return storagePrefix;
        else {
            return null;
        }
    }

    public ConcurrentHashMap<String, CacheVal> createStorage() {
        return new ConcurrentHashMap<>();
    }

    public void setCreatedStorage(String storagePrefix) {
        createdStorages.add(storagePrefix);
    }

}
