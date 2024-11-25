package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.response.CartItemResponse;
import com.enigma.wmb_api.dto.response.CartResponse;

public interface CartService {
    public CartResponse getUserActiveCarts(String userId);
}
