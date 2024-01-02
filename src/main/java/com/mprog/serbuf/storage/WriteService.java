package com.mprog.serbuf.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static com.mprog.serbuf.storage.StorageUtils.*;

@Service
public class WriteService {


    @Value("${serbuf.base.path}")
    private String basePath;

    @Value("${serbuf.nbytes}")
    private Integer nBytes;

    public void setValToStorage(String collection, String key, String value) throws IOException {

        key = replaceDividers(key);
        value = replaceDividers(value);
        String keyValue = key + COLON + value + SEMICOLON;

        File file = buildFile(basePath, collection);
        if (!file.exists()) { // создаем файл и записываем количество существующих файлов коллекции и данные
            writeToDBfile(collection); // записываем новую коллекцию в файл который содержит коллекции.
            String fileCountRow = buildFileCountRow(null); // 22x
            writeToFile(fileCountRow + keyValue, file, StandardOpenOption.CREATE);
        } else {
            if (getFileSizeMegaBytes(file) > nBytes) { // в файле нет места
                String firstLine = readFirstLine(file);
                if (StringUtils.hasText(firstLine)) {
                    int fileCount = getFileCount(firstLine); // define file count
                    File nextFile = buildFile(basePath,collection + fileCount);

                    if (!nextFile.exists()) { // создаем файл и записываем данные
                        writeToFile(keyValue, nextFile, StandardOpenOption.CREATE);
                    } else {  // записываем в конец существующего файла
                        writeToFile(keyValue, nextFile, StandardOpenOption.APPEND);
                    }

                    if (getFileSizeMegaBytes(nextFile) > nBytes) { // перезаписываем количество файлов
                        String fileCountRow = buildFileCountRow(fileCount);
                        writeDataToFile(collection + EXT, fileCountRow, 0);
                    }
                }
            } else { // записываем в конец существующего файла
                writeToFile(keyValue, file, StandardOpenOption.APPEND);
            }
        }
    }

    private String readFirstLine(File file) throws IOException {
        BufferedReader brTest = new BufferedReader(new FileReader(file));
        return brTest.readLine();
    }


    private double getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024);
    }

    private String buildFileCountRow(Integer num) {
        if (num == null) {
            return 1 + "xx\n";
        }
        String row = String.valueOf(++num);
        int length = row.length();
        if (length == 1) {
            return row + "xx\n";
        } else if (length == 2) {
            return row + "x\n";
        } else
            return row + "\n";
    }

    private void writeDataToFile(String filePath, String data, int seek) throws IOException {
        RandomAccessFile file = new RandomAccessFile(basePath + filePath, "rw");
        file.seek(seek);
        file.write(data.getBytes());
        file.close();
    }


    private void writeToDBfile(String collection) {
        File file = new File(basePath + DATABASE);
        writeToFile(collection + "\n", file, StandardOpenOption.APPEND);
    }

    private void writeToFile(String toWrite, File file, StandardOpenOption option) {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8, option)) {
            writer.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
