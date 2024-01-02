package com.mprog.serbuf.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileService {

    public static void writeToFile(String fileName, byte[] bytes) {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)){
            file.createNewFile();
            fos.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
