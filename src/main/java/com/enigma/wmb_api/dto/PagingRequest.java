package com.enigma.wmb_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PagingRequest {
    private Integer page;
    private Integer size;
    private String sort;
}
