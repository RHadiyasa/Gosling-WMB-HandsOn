package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.OrderStatus;
import com.enigma.wmb_api.dto.request.DraftOrderRequest;
import com.enigma.wmb_api.dto.request.OrderDetailsRequest;
import com.enigma.wmb_api.dto.request.UpdateOrderStatusRequest;
import com.enigma.wmb_api.dto.response.OrderDetailsResponse;
import com.enigma.wmb_api.dto.response.OrderResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.Order;
import com.enigma.wmb_api.entity.OrderDetails;
import com.enigma.wmb_api.repository.OrderRepository;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final MenuService menuService;

    @Override
    public OrderResponse createDraftOrder(DraftOrderRequest draftOrderRequest) {
        Customer customer = customerService.getOne(draftOrderRequest.getCustomerId());
        Order draftOrder = Order.builder()
                .customer(customer)
                .orderType(draftOrderRequest.getOrderType())
                .status(OrderStatus.PENDING)
                .orderDetails(new ArrayList<>())
                .build();

        Order savedOrder = orderRepository.save(draftOrder);
        return toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse addOrderDetails(String orderId, OrderDetailsRequest orderDetailsRequest) {
        // Mendapatkan order berdasarkan ID
        Order order = getOne(orderId);

        // Hanya bisa menambahkan item ke dalam order jika statusnya PENDING
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no pending order");
        }

        // Mendapatkan data menu berdasarkan menuId dari request
        Menu menu = menuService.getOne(orderDetailsRequest.getMenuId());

        // Mengecek apakah menu yang sama sudah ada di order details
        Optional<OrderDetails> existingOrderDetail = order.getOrderDetails()
                .stream()
                .filter(detail -> detail.getMenu().getId().equals(menu.getId()))
                .findFirst();

        if (existingOrderDetail.isPresent()) {
            // Jika menu sudah ada di order, tambahkan kuantitasnya
            OrderDetails orderDetails = existingOrderDetail.get();
            orderDetails.setQuantity(orderDetails.getQuantity() + orderDetailsRequest.getQuantity());
            orderDetails.setPriceSnapshot(menu.getPrice()); // Mengupdate harga snapshot jika diperlukan
            // perbarui total harga
            orderDetails.setTotalPrice(orderDetails.getQuantity() * menu.getPrice());
        } else {
            // Jika menu belum ada, buat detail order baru dan tambahkan ke order
            OrderDetails newOrderDetails = OrderDetails.builder()
                    .menu(menu) // Set menu yang dipesan
                    .order(order) // Hubungkan dengan order saat ini
                    .quantity(orderDetailsRequest.getQuantity()) // Set kuantitas dari request
                    .priceSnapshot(menu.getPrice()) // Simpan harga menu saat ini
                    .totalPrice(menu.getPrice() * orderDetailsRequest.getQuantity())
                    .build();

            // Tambahkan detail order baru ke daftar order
            order.getOrderDetails().add(newOrderDetails);
        }

        // Simpan perubahan ke database
        Order updatedOrder = orderRepository.save(order);

        // Konversi order yang diupdate ke objek respons dan kembalikan
        return toOrderResponse(updatedOrder);
    }

    @Override
    public List<OrderDetailsResponse> getOrderDetails(String orderId) {
        Order order = getOne(orderId);
        return order.getOrderDetails().stream()
                .map(this::toOrderDetailsResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Order getOne(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @Override
    public OrderResponse updateOrderDetails(String orderId, String detailId, OrderDetailsRequest orderDetailsRequest) {
        return null;
    }

    @Override
    public OrderResponse deleteOrderDetails(String orderId, String detailId) {
        return null;
    }

    @Override
    public OrderResponse updateOrderStatus(String orderId, UpdateOrderStatusRequest updateOrderStatusRequest) {
        // Validasi kalo misalkan sudah dibayar, baru bisa diupdate jadi completed
        // kalau tidak dibayar jadi canceled
        Order order = getOne(orderId);
        order.setStatus(updateOrderStatusRequest.getOrderStatus());
        Order updatedOrder = orderRepository.save(order);
        return toOrderResponse(updatedOrder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse checkoutOrder(String orderId) {
        Order order = getOne(orderId);

        // Cek apakah statusnya pending?
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no pending order");
        }

        order.setStatus(OrderStatus.PAID);
        Order updatedOrder = orderRepository.save(order);

        updatedOrder.getOrderDetails().forEach(orderDetail ->{ // Iterasi semua orderDetails
            // Ambil menu
            Menu menu = orderDetail.getMenu();

            // cek apakah stock menu cukup
            if (menu.getStock() < orderDetail.getQuantity()) {
                // Kalo stock kurang
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
            }

            menu.setStock(menu.getStock() - orderDetail.getQuantity()); // kurangi stock
            menuService.updateOne(menu); // update stock

        });

        return toOrderResponse(updatedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        return null;
    }


    private OrderResponse toOrderResponse(Order order) {

        List<OrderDetailsResponse> orderDetailsResponse = order.getOrderDetails().stream()
                .map(this::toOrderDetailsResponse).collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName())
                .orderType(order.getOrderType())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getStatus())
                .orderDetails(orderDetailsResponse)
                .build();
    }

    private OrderDetailsResponse toOrderDetailsResponse(OrderDetails orderDetails) {
        return OrderDetailsResponse.builder()
                .id(orderDetails.getId())
                .menuId(orderDetails.getMenu().getId())
                .menuName(orderDetails.getMenu().getName())
                .quantity(orderDetails.getQuantity())
                .price(orderDetails.getTotalPrice())
                .build();
    }
}
