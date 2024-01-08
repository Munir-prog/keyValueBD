package com.mprog.serbuf.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllDataResponse {

    private Map<String, Map<String, String>> allData;
    private Boolean result;
}
