package com.mprog;

import com.mprog.serbuf.service.StorageCreatorService;
import com.mprog.serbuf.service.impl.MainCommandService;
import com.mprog.serbuf.service.impl.StorageService;
import com.mprog.serbuf.storage.MapDiskStorage;
import com.mprog.serbuf.storage.MapMemoryStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MadCache {

    public static void main(String[] args) {
        SpringApplication.run(MadCache.class, args);
        /*StorageService storageService = new StorageService();
        StorageCreatorService storageCreatorService = new StorageCreatorService();
        MapDiskStorage mapDiskStorage = new MapDiskStorage(storageService, storageCreatorService);
        MapMemoryStorage mapMemoryStorage = new MapMemoryStorage();
        MainCommandService service = new MainCommandService(mapMemoryStorage);

        service.set("1", "test");
        service.set("1", "test2");
        service.set("1", "test");

        System.out.println(service.exists("1"));
        System.out.println(service.get("1"));
        System.out.println(service.append("1", " test2"));*/
    }

}
