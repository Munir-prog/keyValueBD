package com.mprog.serbuf.service;

import java.util.Map;

public interface HashCommand {

    String hashSet(String key, String hashKey, String hashValue);

    String hashSet(String key, Map<String, String> hashMap);

    String hashGet(String key, String hashKey);

    Map<String, String> hashGetAll(String key);

}
