package com.mprog.serbuf.service;

public interface MainCommand {

    boolean clear(String key);

    String get(String key);

    String getAndDelete(String key);

    void set(String key, String value);

    String append(String key, String value);

    void set(String key, String value, Integer expireInSeconds);

    boolean exists(String key);

}
