package com.hamo.user.service.auth;

import com.hamo.user.dto.auth.LoginResponse;

public interface AuthService {


    LoginResponse authenticate(String username, String password);
}
