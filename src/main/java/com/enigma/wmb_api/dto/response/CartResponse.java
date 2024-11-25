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
public class CartResponse {
    private String id;
    private List<CartItem> items;
    private Integer totalQuantity;
    private Long totalAmount;

    public static CartResponse cartToCartsMenuResponse(Cart cart) {
        return CartResponse.builder()
                .id(String.valueOf(cart.getId()))
                .totalQuantity(cart.getTotalQuantity())
                .totalAmount(cart.getTotalAmount())
                .items(cart.getCartItems())
                .build();
    }
}
