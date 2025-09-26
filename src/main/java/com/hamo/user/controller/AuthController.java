package com.hamo.user.controller;

import com.hamo.user.dto.auth.LoginRequest;
import com.hamo.user.dto.auth.LoginResponse;
import com.hamo.user.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(loginResponse);
    }
}
