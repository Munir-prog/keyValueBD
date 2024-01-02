package com.mprog.serbuf.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private String host;
    private Integer port;
    private String message;
    private String value;
    private boolean success;

}
