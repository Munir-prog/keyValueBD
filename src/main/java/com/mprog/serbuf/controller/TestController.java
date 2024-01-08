package com.mprog.serbuf.controller;

import com.mprog.serbuf.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public String get(@RequestParam List<String> collections, @RequestParam Integer threadCount, @RequestParam Integer threadPoolSize, @RequestParam Integer requestCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        testService.testEfficiency(collections, threadCount, threadPoolSize, requestCount);
        long end = System.currentTimeMillis();
        return "time: " + (end - start);
    }

    @GetMapping("/clear")
    public String get(@RequestParam boolean internal) throws InterruptedException {
        long start = System.currentTimeMillis();
        testService.clear(internal);
        long end = System.currentTimeMillis();
        return "time: " + (end - start);
    }
}

