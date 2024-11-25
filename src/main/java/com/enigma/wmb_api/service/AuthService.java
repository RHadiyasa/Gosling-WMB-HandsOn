package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.LoginRequest;
import com.enigma.wmb_api.dto.request.RegisterRequest;
import com.enigma.wmb_api.dto.response.AuthResponse;

public interface AuthService {
    public AuthResponse register(RegisterRequest request);
    public AuthResponse login(LoginRequest request);
}
