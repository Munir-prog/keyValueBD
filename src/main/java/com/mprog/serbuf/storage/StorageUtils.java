package com.mprog.serbuf.storage;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


@UtilityClass
public class StorageUtils {

    public static final String EXT = ".col";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String DATABASE = "databases";

    public static int getFileCount(String firstLine) {
        firstLine = firstLine.replaceAll("x", " ");
        return Integer.parseInt(firstLine.trim());
    }

    public static File buildFile(String basePath, String collection) {
        return new File(basePath + collection + EXT);
    }

    public static String replaceDividers(String textToReplace) {
        textToReplace = replaceDividersSemi(textToReplace);
        textToReplace = replaceDividersColon(textToReplace);
        return textToReplace;
    }

    public static String replaceDividersColon(String textToReplace) {
        return textToReplace.replaceAll(COLON, "*&^%");
    }

    public static String replaceDividersSemi(String textToReplace) {
        return textToReplace.replaceAll(SEMICOLON, "%^&*");
    }

    public static void writeDataToFile(String filePath, String data, int seek) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        file.seek(seek);
        file.write(data.getBytes());
        file.close();
    }

    @SneakyThrows
    public static List<String> getLinesFromFile(String basePath, String collection){
        File file = buildFile(basePath, collection);
        return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    }
}
