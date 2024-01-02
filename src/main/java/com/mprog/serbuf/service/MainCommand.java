package com.mprog.serbuf.service;

import com.mprog.serbuf.model.ResponseDto;

import java.util.List;
import java.util.Map;

public interface MainCommand {

    List<ResponseDto> createCollection(String collection, boolean internal);

    boolean clear(String collection, String key);

    String get(String collection, String key, boolean internal);

    String getAndDelete(String collection, String key);

    List<ResponseDto> set(String collection, String key, String value, boolean internal);
    void update(String collection, String key, String value);
    void pull(String collection, Map<String, String> data);

    String append(String collection, String key, String value);

    void set(String collection, String key, String value, Integer expireInSeconds);

    boolean exists(String collection, String key);

}
