package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.Cart;
import com.enigma.wmb_api.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
