package com.mprog.serbuf.service;

public interface ListCommand {

    String listSetHead(String key, String... values);

    String listGetHead(String key, String... values);

    String listSetTail(String key, String... values);

    String listGetTail(String key, String... values);

}
