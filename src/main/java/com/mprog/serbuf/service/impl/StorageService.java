package com.mprog.serbuf.service.impl;

import com.mprog.serbuf.model.CacheVal;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.Objects;

@Service
public class StorageService {

    private String path;

    @SneakyThrows
    public void save(Map<String, CacheVal> map, String name) {
        initPath();
        try (FileOutputStream fos = new FileOutputStream(path + name + "map");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(map);
        }
    }

    public Map<String, CacheVal> read(String name) {
        initPath();
        try (FileInputStream fos = new FileInputStream(path + name + "map");
             ObjectInputStream ois = new ObjectInputStream(fos)) {
            return (Map<String, CacheVal>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void initPath() {
        if (path == null) {
            ClassLoader classLoader = getClass().getClassLoader();
            path = Objects.requireNonNull(classLoader.getResource(".")).getFile();
        }
    }
}
