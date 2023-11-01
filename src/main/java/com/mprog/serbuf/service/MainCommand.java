package com.mprog.serbuf.service;

public interface MainCommand {

    String clear(String key);

    String get(String key);

    String getAndDelete(String key);

    String set(String key, String value);

    String append(String key, String value);

    String set(String key, String value, Integer expireInDays);

    boolean exists(String key);

}
