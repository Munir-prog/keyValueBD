package com.mprog.serbuf.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShardAllData {

    private List<ReplicaAllData> replicaAllDataList;
}
