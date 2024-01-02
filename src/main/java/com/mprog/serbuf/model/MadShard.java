package com.mprog.serbuf.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MadShard {

    private Integer index;
    private List<MadReplica> replicas;
}
