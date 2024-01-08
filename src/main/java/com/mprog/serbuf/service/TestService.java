package com.mprog.serbuf.service;

import com.mprog.serbuf.storage.Storage;
import com.mprog.serbuf.utils.RandomString;
import com.mprog.serbuf.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final RestTemplate restTemplate;
    private final RandomString randomString;
    private final Storage storage;


    public void testEfficiency(List<String> collections, int threadCount, int threadPoolSize, int requestCount) throws InterruptedException {
        String url = "http://localhost:8080/api/v1/set";
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        long start = System.currentTimeMillis();
        for (int j = 0; j < threadCount; j++) {
            int finalJ = j;
            executorService.submit(() -> {
                for (String collection : collections) {
                    for (int i = 0; i < requestCount; i++) {
                        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                                .queryParam("collection", collection)
                                .queryParam("key", randomString.nextString() + (i + finalJ) + randomString.nextString() )
                                .queryParam("value", randomString.nextString())
                                .toUriString();
                        sendRestRequest(urlTemplate);
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e) {
                            log.error("some i {}, j {} ex: ", i, finalJ, e);
                        }
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1L, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start));
    }

    private void sendRestRequest(String urlTemplate) {
        try {
            restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.POST,
                    RestUtils.getHeaders(),
                    String.class
            );
        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    public void clear(boolean internal) {
        if (internal) {
//            storage.clear()
        }
    }
}
