package com.mprog.serbuf.storage;

import com.mprog.serbuf.model.CacheVal;
import com.mprog.serbuf.model.DatabaseInfo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "sebuf", name = "memory")
public class MapMemoryStorage implements Storage{

    private List<Character> letters;
    private List<Integer> digits;
    private Map<String, Map<String, String>> db = new ConcurrentHashMap<>();

    @Override
    public ConcurrentHashMap<String, CacheVal> getStorageByKey(String key) {
        char first = key.charAt(0);
        if (letters.contains(first)) {
            return getMapByLetter(first);
        }
        int intVal = Character.getNumericValue(first);
        if (digits.contains(intVal)) {
            return getMapByDigit(intVal);
        }
        return otherMap;
    }

    @Override
    public void setValToStorage(String collectionName, String key, String value) {
        if (db.containsKey(collectionName)) {
            Map<String, String> collection = db.get(collectionName);
            collection.put(key, value);
        } else {
            log.warn("No such collection");
            throw new RuntimeException("No such collection");
        }
    }

    public void clear() {
        for (Map<String, String> value : db.values()) {
//            value
        }
    }

    @Override
    public ConcurrentHashMap<String, CacheVal> setValToStorageAndReturn(String collection, String key, String value) {
        return null;
    }

    @Override
    public void deleteValFromStorage(String collection, String key) {

    }

    @Override
    public void updateValInStorage(String collection, String key, String value) {

    }

    @Override
    public List<DatabaseInfo> getDBInfo(String db) {
        return null;
    }

    @Override
    public String getValFromStorage(String collectionName, String key) {
        if (db.containsKey(collectionName)) {
            log.info("collection exists");
            Map<String, String> collection = db.get(collectionName);
            String value = collection.get(key);
            log.info("Value {} found by key {}", value, key);
            return value;
        } else {
            log.warn("No such collection");
            throw new RuntimeException("No such collection");
        }
    }

    @Override
    public void save(ConcurrentHashMap<String, CacheVal> map, String key) {
        System.out.println("Test");
    }

    @Override
    public Map<String, Map<String, String>> getAllData() {
        return db;
    }

    @Override
    public void createCollection(String collectionName) {
        if (db.containsKey(collectionName)) {
            throw new RuntimeException(collectionName + " collection already exists!");
        } else {
            db.put(collectionName, new ConcurrentHashMap<>());
        }
    }


    private final ConcurrentHashMap<String, CacheVal> aMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> bMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> cMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> dMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> eMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> fMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> gMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> hMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> iMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> jMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> kMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> lMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> mMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> nMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> oMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> pMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> qMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> rMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> sMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> tMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> uMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> vMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> wMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> xMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> yMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> zMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, CacheVal> m1Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m2Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m3Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m4Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m5Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m6Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m7Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m8Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m9Map = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CacheVal> m0Map = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, CacheVal> otherMap = new ConcurrentHashMap<>();


    private ConcurrentHashMap<String, CacheVal> getMapByDigit(int intVal) {
        return switch (intVal) {
            case 1 -> m1Map;
            case 2 -> m2Map;
            case 3 -> m3Map;
            case 4 -> m4Map;
            case 5 -> m5Map;
            case 6 -> m6Map;
            case 7 -> m7Map;
            case 8 -> m8Map;
            case 9 -> m9Map;
            case 0 -> m0Map;
            default -> throw new IllegalStateException("Unexpected value: " + intVal);
        };
    }

    private ConcurrentHashMap<String, CacheVal> getMapByLetter(char first) {
        return switch (first) {
            case 'a' -> aMap;
            case 'b' -> bMap;
            case 'c' -> cMap;
            case 'd' -> dMap;
            case 'e' -> eMap;
            case 'f' -> fMap;
            case 'g' -> gMap;
            case 'h' -> hMap;
            case 'i' -> iMap;
            case 'j' -> jMap;
            case 'k' -> kMap;
            case 'l' -> lMap;
            case 'm' -> mMap;
            case 'n' -> nMap;
            case 'o' -> oMap;
            case 'p' -> pMap;
            case 'q' -> qMap;
            case 'r' -> rMap;
            case 's' -> sMap;
            case 't' -> tMap;
            case 'u' -> uMap;
            case 'v' -> vMap;
            case 'w' -> wMap;
            case 'x' -> xMap;
            case 'y' -> yMap;
            case 'z' -> zMap;
            default -> throw new IllegalStateException("Unexpected value: " + first);
        };
    }


    @PostConstruct
    public void constructArrays() {
        letters = Arrays.stream("abcdefghijklmnopqrstuvwxyz".split(""))
                .map(it -> it.charAt(0))
                .toList();
        digits = Arrays.stream("1234567890".split(""))
                .map(Integer::parseInt)
                .toList();
    }
}
