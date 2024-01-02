package com.mprog.serbuf.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MadReplica {

    private String host;
    private Integer port;

    public String buildSetUrl() {
        return String.format(baseUrl(), "set");
    }

    public String buildCreateCollectionUrl() {
        return String.format(baseUrl(), "create");
    }

    public String buildGetAllUrl() {
        return String.format(baseUrl(), "get/all");
    }

    public String buildGetCollectionUrl() {
        return String.format(baseUrl(), "get");
    }

    private String baseUrl() {
        return "http://" + this.host + ":" + this.port + "/api/v1/%s/internal";
    }

    public ResponseDto buildResponseDto() {
        return ResponseDto.builder()
                .host(this.host)
                .port(this.port)
                .build();
    }
}
