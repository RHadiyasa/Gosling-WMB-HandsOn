package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.dto.request.DraftOrderRequest;
import com.enigma.wmb_api.dto.request.OrderDetailsRequest;
import com.enigma.wmb_api.dto.request.UpdateOrderStatusRequest;
import com.enigma.wmb_api.dto.response.OrderDetailsResponse;
import com.enigma.wmb_api.dto.response.OrderResponse;
import com.enigma.wmb_api.service.OrderService;
import com.enigma.wmb_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Constant.ORDER_API)
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/draft")
    public ResponseEntity<?> createDraftOrder(@RequestBody DraftOrderRequest draftOrderRequest) {
        OrderResponse orderResponse = orderService.createDraftOrder(draftOrderRequest);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success create Draft Order", orderResponse);
    }

    @PostMapping("/{orderId}/details")
    private ResponseEntity<?> addOrderDetails(@RequestBody OrderDetailsRequest orderDetailsRequest, @PathVariable String orderId) {
        OrderResponse orderResponse = orderService.addOrderDetails(orderId, orderDetailsRequest);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success add Order Details", orderResponse);
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable String orderId) {
        List<OrderDetailsResponse> orderDetailsResponse = orderService.getOrderDetails(orderId);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success Get Order Details", orderDetailsResponse);
    }

    // Update sebagian / parsial
    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success Update Order Status", orderResponse);
    }

    @PostMapping("/{orderId}/checkout")
    public ResponseEntity<?> checkoutOrder(@PathVariable String orderId) {
        OrderResponse orderResponse = orderService.checkoutOrder(orderId);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success Checkout Order", orderResponse);
    }
}
