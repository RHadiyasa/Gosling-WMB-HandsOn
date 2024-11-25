package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.entity.Cart;
import com.enigma.wmb_api.entity.CartItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private String id;
    private Integer quantity;
    private String name;
    private Long priceSnapshot;

    private Long totalAmount;

    public static CartItemResponse cartToCartsMenuResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(String.valueOf(cartItem.getId()))
                .quantity(cartItem.getQuantity())
                .name(cartItem.getMenu().getName())
                .priceSnapshot(cartItem.getMenu().getPrice())
                .build();
    }
}
