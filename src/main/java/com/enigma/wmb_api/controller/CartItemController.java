package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.dto.response.CartItemResponse;
import com.enigma.wmb_api.service.CartItemService;
import com.enigma.wmb_api.service.CartService;
import com.enigma.wmb_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.CART_ITEM_API)
public class CartItemController {
    private final CartItemService cartItemService;
    @GetMapping
    public ResponseEntity<?> getCarts(@RequestHeader(name = "user_id") String userId) {
//        CartItemResponse cartsResponse = cartService.getCarts(userId);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfuly get active cart", null);
    }
}
