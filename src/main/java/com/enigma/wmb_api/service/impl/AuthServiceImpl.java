package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.dto.request.LoginRequest;
import com.enigma.wmb_api.dto.request.RegisterRequest;
import com.enigma.wmb_api.dto.response.AuthResponse;
import com.enigma.wmb_api.entity.User;
import com.enigma.wmb_api.repository.UserRepository;
import com.enigma.wmb_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(UserRole.CUSTOMER)
                .build();
        userRepository.saveAndFlush(user);
        return AuthResponse.builder().token(String.valueOf(user.getId())).build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return AuthResponse.builder().token(String.valueOf(user.getId())).build();
    }
}
