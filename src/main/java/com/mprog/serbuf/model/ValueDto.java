package com.mprog.serbuf.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValueDto {

    private String value;
    private Integer index;
    private Integer size;
    private String fileNum;
    private Integer fileCount;
}
