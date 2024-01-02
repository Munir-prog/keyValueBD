package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.DatabaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static com.mprog.serbuf.storage.StorageUtils.*;

@Service
@RequiredArgsConstructor
public class DatabaseInfoService {

    @Value("${serbuf.base.path}")
    private String basePath;

    @SneakyThrows
    public List<DatabaseInfo> getDBInfo(String db) {
        // получаем список коллекций
        List<String> lines = Files.readAllLines(new File(basePath + DATABASE).toPath(), StandardCharsets.UTF_8);

        // строим объект содержащий в себе информацию о коллекции
        List<DatabaseInfo> info = getDatabaseInfoLine(lines, db);

        for (DatabaseInfo databaseInfo : info) {

            // считываем размер коллекции
            long dbSize = new File(basePath + databaseInfo.getName() + EXT).length();
            for (int i = 2; i <= databaseInfo.getFileCount(); i++) {
                dbSize += new File(basePath + databaseInfo.getName() + i + EXT).length();
            }
            double size = Math.round((dbSize / (double) 1000000) * 100.0) / 100.0;

            databaseInfo.setSizeMb(size + " mb");
            databaseInfo.setSizeB(dbSize + " bytes");
        }

        return info;
    }

    private List<DatabaseInfo> getDatabaseInfoLine(List<String> lines, String db) {
        if (StringUtils.hasText(db)) {
            return lines.stream()
                    .filter(dbName -> dbName.equals(db))
                    .map(this::buildNewDatabaseInfo)
                    .peek(dbInfo -> dbInfo.setFileCount(getFileCount(dbInfo.getMetaInfo())))
                    .toList();
        } else {
            return lines.stream()
                    .map(this::buildNewDatabaseInfo)
                    .peek(dbInfo -> dbInfo.setFileCount(getFileCount(dbInfo.getMetaInfo())))
                    .toList();
        }
    }

    private DatabaseInfo buildNewDatabaseInfo(String dbName) {
        return new DatabaseInfo(dbName, getLinesFromFile(basePath, dbName).get(0), null, null, null);
    }


//    private static long getFileSizeBytes(File file) {
//        return file.length();
//    }


//    @SneakyThrows
//    public void save(Map<String, CacheVal> map, String name) {
//        initPath();
//        try (FileOutputStream fos = new FileOutputStream(path + name + "map");
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(map);
//        }
//    }
//
//
//    public Map<String, CacheVal> read(String name) {
//        initPath();
//        try (FileInputStream fos = new FileInputStream(path + name + "map");
//             ObjectInputStream ois = new ObjectInputStream(fos)) {
//            return (Map<String, CacheVal>) ois.readObject();
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//            return null;
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    private void initPath() {
//        if (path == null) {
//            ClassLoader classLoader = getClass().getClassLoader();
//            path = Objects.requireNonNull(classLoader.getResource(".")).getFile();
//        }
//    }
//
//    public void createCollection(String collection) throws IOException {
//        File file = buildFile(basePath, collection);
//        if (file.exists()) {
//            throw new RuntimeException("Collection " + collection + " already exists.");
//        } else {
//            file.createNewFile();
//        }
//    }
}
