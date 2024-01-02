package com.mprog.serbuf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mprog.serbuf.model.ClusterConfig;
import com.mprog.serbuf.model.MadShard;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoordinateService {

    private ClusterConfig clusterConfig;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void constructClusterConfig() {
        try {
            Resource resource = resourceLoader.getResource("classpath:config.json");

            InputStream inputStream = resource.getInputStream();

            try {
                byte[] byteData = FileCopyUtils.copyToByteArray(inputStream);
                String content = new String(byteData, StandardCharsets.UTF_8);

//            File f = ResourceUtils.getFile("classpath*:config.json");
                ObjectMapper objectMapper = new ObjectMapper();
                clusterConfig = objectMapper.readValue(content, ClusterConfig.class);
            } catch (IOException e) {
                log.error("IOException", e);
            }
        } catch (Exception e) {
            log.error("Something went wrong while cluster configuration ", e);
            System.exit(-1);
        }
    }


    public MadShard getShard(String key) {
        return clusterConfig.getShards()
                .stream()
                .filter(shard -> shard.getIndex().equals(calculateHashCodeAndReturnShardIndex(key)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Exception while shard choosing"));
    }

    public List<MadShard> getShards() {
        return clusterConfig.getShards();
    }

    private Integer calculateHashCodeAndReturnShardIndex(String key) {
        return Math.abs(key.hashCode() % getShardCount());
    }

    public Integer getShardCount() {
        return clusterConfig.getShards().size();
    }
}
