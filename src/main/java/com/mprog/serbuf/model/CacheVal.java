package com.mprog.serbuf.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CacheVal {

    private String value;

    private Integer expiration;

    private LocalDateTime setDate;
}
