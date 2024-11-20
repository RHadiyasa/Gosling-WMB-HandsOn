package com.enigma.wmb_api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    private String name;
    private Long price;
    private String Category;
}
