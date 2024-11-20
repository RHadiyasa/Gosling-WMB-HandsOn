package com.enigma.wmb_api.dto;

import com.enigma.wmb_api.constant.MenuCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String name;
    private Long price;
    private MenuCategory category;;
}
