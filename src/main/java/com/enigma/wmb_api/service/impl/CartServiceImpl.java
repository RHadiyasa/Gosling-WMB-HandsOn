package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.response.CartResponse;
import com.enigma.wmb_api.repository.CartRepository;
import com.enigma.wmb_api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    @Override
    public CartResponse getUserActiveCarts(String userId) {
        return CartResponse.cartToCartsMenuResponse(cartRepository.findByUserId(UUID.fromString(userId)));
    }
}
