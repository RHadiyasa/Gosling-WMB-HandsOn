package com.enigma.wmb_api.dto;

import com.enigma.wmb_api.constant.MenuCategory;
import com.enigma.wmb_api.entity.Menu;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String id;
    private String name;
    private Long price;
    private MenuCategory category;

    public static MenuResponse menuEntityToMenuResponse(Menu menu) {
          return MenuResponse.builder()
                  .name(menu.getName())
                  .price(menu.getPrice())
                  .category(menu.getCategory())
                  .build();
    }
}
