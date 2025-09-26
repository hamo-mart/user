package com.hamo.user.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private Long userId;
    private List<String> roles;
    private String nickname;
}
