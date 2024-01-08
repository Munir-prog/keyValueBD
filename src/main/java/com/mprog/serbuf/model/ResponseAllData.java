package com.mprog.serbuf.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseAllData {

    private List<ShardAllData> shardAllData;
    private Long count;
}
