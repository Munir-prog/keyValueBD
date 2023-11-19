package com.mprog.serbuf.controller;

import com.mprog.serbuf.service.impl.MainCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MainCommandController {

    private final MainCommandService mainCommandService;

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return mainCommandService.get(key);
    }

    @PostMapping("/clear")
    public boolean clear(@RequestParam String key) {
        return mainCommandService.clear(key);
    }

    @PostMapping("/getAndDelete")
    public String getAndDelete(@RequestParam String key) {
        return mainCommandService.getAndDelete(key);
    }

    @PostMapping("/set")
    public void set(@RequestParam String key, @RequestParam String value) {
        mainCommandService.set(key, value);
    }

    @PostMapping("/setex")
    public void set(@RequestParam String key, @RequestParam String value, @RequestParam("ex") Integer expireInSeconds) {
        mainCommandService.set(key, value, expireInSeconds);
    }

    @GetMapping("/exists")
    public boolean exists(@RequestParam String key) {
        return mainCommandService.exists(key);
    }

    @PostMapping("/append")
    public String append(@RequestParam String key, @RequestParam String value) {
        return mainCommandService.append(key, value);
    }
}

