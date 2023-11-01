package com.mprog.serbuf.service;

public interface SetCommand {

    String setSet(String key, String... values);
    String setFetch(String key);

}
