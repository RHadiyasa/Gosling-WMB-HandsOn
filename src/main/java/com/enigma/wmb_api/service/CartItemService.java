package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.response.CartItemResponse;
import com.enigma.wmb_api.entity.Menu;

public interface CartItemService {
    CartItemResponse addMenuToCart(String userId, Menu menu);
}
