package com.mprog.serbuf.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class CacheVal implements Serializable {


    private String value;

    private Integer expiration;

    private LocalDateTime setDate;
}
