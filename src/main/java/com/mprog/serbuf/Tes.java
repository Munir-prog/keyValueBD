package com.mprog.serbuf;

import com.mprog.serbuf.storage.DatabaseInfoService;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Tes {
    public static void main(String[] args) throws IOException {
        DatabaseInfoService databaseInfoService = new DatabaseInfoService();
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
//            storageService.setValToStorage("file", "key" + i, "value" + i);
//        }
//        storageService.setValToStorage("file", "FFFADW718723", "life is very good");
//        System.out.println(databaseInfoService.getValFromStorage("file", "key9999996"));
//        storageService.deleteValFromStorage("file", "k:ey218008");
//        storageService.updateValInStorage("file", "k:ey218010", "test22222222212345678");
        long end = System.currentTimeMillis();
        System.out.println(end-start);


    }

    private static void writeDataToFile(String filePath, String data, int seek) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        file.seek(seek);
        file.write(data.getBytes());
        file.close();
    }

}
