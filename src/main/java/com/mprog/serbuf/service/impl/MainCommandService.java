package com.mprog.serbuf.service.impl;

import com.mprog.serbuf.model.*;
import com.mprog.serbuf.service.CoordinateService;
import com.mprog.serbuf.service.MainCommand;
import com.mprog.serbuf.storage.Storage;
import com.mprog.serbuf.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainCommandService implements MainCommand {

    private final Storage storage;
    private final CoordinateService coordinateService;
    private final RestTemplate restTemplate;

    @Override
    public String get(String collection, String key, boolean internal) {
        if (!internal) {
            MadShard shard = coordinateService.getShard(key);
            List<MadReplica> replicas = shard.getReplicas();
            List<ResponseDto> responseList = new ArrayList<>();
            for (MadReplica replica : replicas) {
                String urlTemplate = UriComponentsBuilder.fromHttpUrl(replica.buildGetCollectionUrl())
                        .queryParam("collection", collection)
                        .queryParam("key", key)
                        .encode()
                        .toUriString();
                ResponseDto responseDto = sendRestRequest(responseList, replica, urlTemplate, HttpMethod.GET);
                if (responseDto.isSuccess()) {
                    return responseDto.getValue();
                }
            }
            throw new RuntimeException("Not found!");
        } else {
            return storage.getValFromStorage(collection, key);
        }
    }

    public ResponseAllData getAllData() {
        List<MadShard> shards = coordinateService.getShards();
        List<ShardAllData> shardAllDataList = new ArrayList<>();
        for (MadShard shard : shards) {
            List<MadReplica> replicas = shard.getReplicas();
            List<ReplicaAllData> replicaAllDataList = new ArrayList<>();
            for (MadReplica replica : replicas) {
                String urlTemplate = UriComponentsBuilder.fromHttpUrl(replica.buildGetAllUrl())
                        .encode()
                        .toUriString();
                ReplicaAllData replicaAllData = ReplicaAllData.builder()
                        .data(sendGetAllDataRequest(urlTemplate).getAllData())
                        .build();
                replicaAllDataList.add(replicaAllData);
            }
            ShardAllData shardAllData = ShardAllData.builder()
                    .replicaAllDataList(replicaAllDataList)
                    .build();
            shardAllDataList.add(shardAllData);

        }
        return ResponseAllData.builder()
                .shardAllData(shardAllDataList)
                .build();
    }

    public Map<String, Map<String, String>> getAllDataInternal() {
        return storage.getAllData();
    }

    @Override
    public List<ResponseDto> set(String collection, String key, String value, boolean internal) {
        List<ResponseDto> responseList = new ArrayList<>();
        if (!internal) {
            MadShard shard = coordinateService.getShard(key);
            shard.getReplicas()
                    .forEach(replica -> {
                        String urlTemplate = UriComponentsBuilder.fromHttpUrl(replica.buildSetUrl())
                                .queryParam("collection", collection)
                                .queryParam("key", key)
                                .queryParam("value", value)
                                .encode()
                                .toUriString();
                        sendRestRequest(responseList, replica, urlTemplate, HttpMethod.POST);
                    });
        } else {
            storage.setValToStorage(collection, key, value);
        }
        return responseList;
    }

    @Override
    public List<ResponseDto> createCollection(String collection, boolean internal) {
        List<ResponseDto> responseList = new ArrayList<>();
        if (!internal) {
            List<MadShard> shards = coordinateService.getShards();
            coordinateService.getShards()
                    .stream()
                    .map(MadShard::getReplicas)
                    .flatMap(Collection::stream)
                    .forEach(replica -> {
                        String urlTemplate = UriComponentsBuilder.fromHttpUrl(replica.buildCreateCollectionUrl())
                                .queryParam("collection", collection)
                                .encode()
                                .toUriString();
                        sendRestRequest(responseList, replica, urlTemplate, HttpMethod.POST);
                    });
        } else
            storage.createCollection(collection);
        return responseList;
    }

    private AllDataResponse sendGetAllDataRequest(String urlTemplate) {
        try {
            HttpEntity<AllDataResponse> response = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    RestUtils.getHeaders(),
                    AllDataResponse.class
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Something happened while getting all data ", e);
            throw new RuntimeException("sdada");
        }
    }

    private ResponseDto sendRestRequest(List<ResponseDto> responseList, MadReplica replica, String urlTemplate, HttpMethod httpMethod) {
        ResponseDto responseDto = replica.buildResponseDto();
        try {
            HttpEntity<String> response = restTemplate.exchange(
                    urlTemplate,
                    httpMethod,
                    RestUtils.getHeaders(),
                    String.class
            );
            responseDto.setMessage("ok!");
            responseDto.setValue(response.getBody());
            responseDto.setSuccess(true);
        } catch (Exception e) {
            responseDto.setSuccess(false);
            responseDto.setMessage(e.getMessage());
        }
        responseList.add(responseDto);
        return responseDto;
    }

    @Override
    public void update(String collection, String key, String value) {
        storage.updateValInStorage(collection, key, value);
    }


    public List<DatabaseInfo> getDBInfo(String db) {
        return storage.getDBInfo(db);
    }

    @Override
    public boolean clear(String collection, String key) {
        storage.deleteValFromStorage(collection, key);
        return true;
    }

    @Override
    public String getAndDelete(String collection, String key) {
        var map = storage.getStorageByKey(key);
        CacheVal val = map.remove(key);
        storage.save(map, key);
        return val != null ? val.getValue() : null;

    }

    @Override
    public synchronized String append(String collection, String key, String value) {
        var map = storage.getStorageByKey(key);
        CacheVal val = map.get(key);
        if (val != null) {
            String old = val.getValue();
            String newVal = old != null ? old + value : value;
            val.setValue(newVal);
            storage.save(map, key);
            return newVal;
        } else {
            CacheVal newCacheVal = buildCacheVal(value, null, null);
            map.put(key, newCacheVal);
            storage.save(map, key);
            return value;
        }
    }

    @Override
    public void pull(String collection, Map<String, String> data) {

    }

    @Override
    public void set(String collection, String key, String value, Integer expireInSeconds) {
        var map = storage.getStorageByKey(key);
        CacheVal val = buildCacheVal(value, expireInSeconds, LocalDateTime.now());
        map.put(key, val);
        storage.save(map, key);
    }

    @Override
    public boolean exists(String collection, String key) {
        var map = storage.getStorageByKey(key);
        return map.containsKey(key);
    }

    private static CacheVal buildCacheVal(String value, Integer expireInSeconds, LocalDateTime localDateTime) {
        return CacheVal.builder()
                .value(value)
                .setDate(localDateTime)
                .expiration(expireInSeconds)
                .build();
    }

}
