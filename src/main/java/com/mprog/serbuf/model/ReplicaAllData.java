package com.mprog.serbuf.model;


import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ReplicaAllData {

    private Map<String, Map<String, String>> data;

}