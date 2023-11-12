package com.mprog;

import com.mprog.serbuf.service.impl.MainCommandService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class MadCache {

    public static void main(String[] args) {
//        SpringApplication.run(MadCache.class, args);
        MainCommandService service = new MainCommandService();
        service.set("1", "test");
        service.set("1", "test2");
        service.set("1", "test");

        System.out.println(service.exists("1"));
        System.out.println(service.get("1"));
        System.out.println(service.append("1", " test2"));
    }

}
