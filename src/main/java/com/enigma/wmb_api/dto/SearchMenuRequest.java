package com.enigma.wmb_api.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SearchMenuRequest extends PagingRequest {
    private String name;
    private String category;
}
