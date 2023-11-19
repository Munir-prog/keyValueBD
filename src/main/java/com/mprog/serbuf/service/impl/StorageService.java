package com.mprog.serbuf.service.impl;

import com.mprog.serbuf.model.CacheVal;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

@Service
public class StorageService {

    @SneakyThrows
    public void save(Map<String, CacheVal> map, String name) {
        try (FileOutputStream fos = new FileOutputStream(name + "map");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(map);
        }
    }

    public Map<String, CacheVal> read(String name) {
        try (FileInputStream fos = new FileInputStream(name + "map");
             ObjectInputStream ois = new ObjectInputStream(fos)) {
            return (Map<String, CacheVal>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
