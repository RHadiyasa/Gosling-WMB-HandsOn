package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.Cart;
import com.enigma.wmb_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByUserId(UUID userId);
}
