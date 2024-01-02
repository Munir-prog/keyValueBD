package com.mprog.serbuf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DatabaseInfo {
    private String name;
    private String metaInfo;
    private Integer fileCount;
    private String sizeMb;
    private String sizeB;
}
