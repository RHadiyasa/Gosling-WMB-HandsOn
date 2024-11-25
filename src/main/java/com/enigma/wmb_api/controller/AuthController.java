package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.dto.request.LoginRequest;
import com.enigma.wmb_api.dto.request.RegisterRequest;
import com.enigma.wmb_api.dto.response.AuthResponse;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.User;
import com.enigma.wmb_api.service.AuthService;
import com.enigma.wmb_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.AUTH_API)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CUSTOMER_REGISTRATION, authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_CUSTOMER_LOGIN, authResponse);
    }
}
