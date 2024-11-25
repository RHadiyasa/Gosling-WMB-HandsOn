package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.response.CartItemResponse;
import com.enigma.wmb_api.entity.Cart;
import com.enigma.wmb_api.entity.CartItem;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.User;
import com.enigma.wmb_api.repository.CartItemRepository;
import com.enigma.wmb_api.repository.CartRepository;
import com.enigma.wmb_api.repository.UserRepository;
import com.enigma.wmb_api.service.CartItemService;
import com.enigma.wmb_api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartItemResponse addMenuToCart(String userId, Menu menu) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id" + userId + " not found");
        } else {
            Cart userCart = cartRepository.findByUserId(UUID.fromString(userId));
            if (userCart == null) {
                Cart newUserCart = Cart.builder()
                        .user(user.get())
                        .cartItems(new ArrayList<>())
                        .build();
                cartRepository.saveAndFlush(newUserCart);

                CartItem newCartItem = CartItem.builder()
                        .menu(menu)
                        .quantity(10) //TODO: harusnya dinamis dari request
                        .cart(newUserCart)
                        .build();
                cartItemRepository.saveAndFlush(newCartItem);
            } else {
                List<CartItem> currentCartItems = userCart.getCartItems();
                CartItem newCartItem = CartItem.builder()
                        .menu(menu)
                        .quantity(10) //TODO: harusnya dinamis dari request
                        .cart(userCart)
                        .build();
                currentCartItems.add(newCartItem);
                userCart.setCartItems(currentCartItems);
                cartRepository.saveAndFlush(userCart);
            }

        }
        return null;
    }
}
