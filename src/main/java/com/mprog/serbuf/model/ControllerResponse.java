package com.mprog.serbuf.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ControllerResponse {

    private String time;
    private List<ResponseDto> responseDtoList;
}
