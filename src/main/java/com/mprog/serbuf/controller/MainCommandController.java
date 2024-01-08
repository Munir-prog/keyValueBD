package com.mprog.serbuf.controller;

import com.mprog.serbuf.model.*;
import com.mprog.serbuf.service.impl.MainCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MainCommandController {

    private final MainCommandService mainCommandService;

    @GetMapping("/get")
    public String get(@RequestParam String collection, @RequestParam String key) {
        long start = System.currentTimeMillis();
        String result = mainCommandService.get(collection, key, false);
        long end = System.currentTimeMillis();
        return result + " time: " + (end - start);
    }

    @GetMapping("/get/internal")
    public String getInternal(@RequestParam String collection, @RequestParam String key) {
        long start = System.currentTimeMillis();
        String result = mainCommandService.get(collection, key, true);
        long end = System.currentTimeMillis();
        return result + " time: " + (end - start);
    }

    @GetMapping("/get/all")
    public ResponseAllData getAll() {
        long start = System.currentTimeMillis();
        ResponseAllData allData = mainCommandService.getAllData();
        long end = System.currentTimeMillis();
        return allData;
    }

    @GetMapping("/get/all/count")
    public ResponseAllData getAllCount() {
        long start = System.currentTimeMillis();
        ResponseAllData allData = mainCommandService.getAllDataCount();
        long end = System.currentTimeMillis();
        return allData;
    }

    @GetMapping("/get/count")
    public Long count() {
        long start = System.currentTimeMillis();
        ResponseAllData allData = mainCommandService.getAllData();
        long end = System.currentTimeMillis();
        return allData.getCount();
    }

    @GetMapping("/get/all/internal")
    public AllDataResponse getAllInternal() {
        long start = System.currentTimeMillis();
        Map<String, Map<String, String>> result = mainCommandService.getAllDataInternal();
        long end = System.currentTimeMillis();
        return new AllDataResponse(result, true);
    }

    @PostMapping("/clear")
    public String clear(@RequestParam String collection, @RequestParam String key) {
        long start = System.currentTimeMillis();
        boolean result = mainCommandService.clear(collection, key);
        long end = System.currentTimeMillis();
        return result + " time: " + (end - start);
    }

    @PostMapping("/create")
    public ControllerResponse create(@RequestParam String collection) {
        long start = System.currentTimeMillis();
        List<ResponseDto> responseDtoList = mainCommandService.createCollection(collection, false);
        long end = System.currentTimeMillis();
        return ControllerResponse.builder()
                .responseDtoList(responseDtoList)
                .time("time: " + (end - start))
                .build();
    }

    @PostMapping("/create/internal")
    public String createInternal(@RequestParam String collection) {
        long start = System.currentTimeMillis();
        mainCommandService.createCollection(collection, true);
        long end = System.currentTimeMillis();
        return " time: " + (end - start);
    }

    @PostMapping("/set")
    public ControllerResponse set(@RequestParam String collection, @RequestParam String key, @RequestParam String value) {
        long start = System.currentTimeMillis();
        List<ResponseDto> responseDtoList = mainCommandService.set(collection, key, value, false);
        long end = System.currentTimeMillis();
        return ControllerResponse.builder()
                .responseDtoList(responseDtoList)
                .time("time: " + (end - start))
                .build();
    }


    @PostMapping("/set/internal")
    public ControllerResponse setInternal(@RequestParam String collection, @RequestParam String key, @RequestParam String value) {
//        log.info("set internal method");
        long start = System.currentTimeMillis();
        List<ResponseDto> responseDtoList = mainCommandService.set(collection, key, value, true);
        long end = System.currentTimeMillis();
        return ControllerResponse.builder()
                .responseDtoList(responseDtoList)
                .time("time: " + (end - start))
                .build();
    }

    @PostMapping("/test/set")
    public String set(@RequestParam String collection, @RequestParam String key, @RequestParam String value, @RequestParam Long iter) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < iter; i++) {
            mainCommandService.set(collection, key+i, value+i, false);
        }
        long end = System.currentTimeMillis();
        return "time: " + (end - start);
    }

    @PostMapping("/update")
    public String update(@RequestParam String collection, @RequestParam String key, @RequestParam String value) {
        long start = System.currentTimeMillis();
        mainCommandService.update(collection, key, value);
        long end = System.currentTimeMillis();
        return "time: " + (end - start);
    }

    @GetMapping("/get/info")
    public List<DatabaseInfo> getDBInfo(@RequestParam(required = false) String db) {
        return mainCommandService.getDBInfo(db);
    }


    @PostMapping("/getAndDelete")
    public String getAndDelete(@RequestParam String collection, @RequestParam String key) {
        return mainCommandService.getAndDelete(collection, key);
    }


    @PostMapping("/setex")
    public void set(@RequestParam String collection, @RequestParam String key, @RequestParam String value, @RequestParam("ex") Integer expireInSeconds) {
        mainCommandService.set(collection, key, value, expireInSeconds);
    }

    @GetMapping("/exists")
    public boolean exists(@RequestParam String collection, @RequestParam String key) {
        return mainCommandService.exists(collection, key);
    }

    @PostMapping("/append")
    public String append(@RequestParam String collection, @RequestParam String key, @RequestParam String value) {
        return mainCommandService.append(collection, key, value);
    }
}

