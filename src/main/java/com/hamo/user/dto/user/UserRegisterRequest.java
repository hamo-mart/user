package com.hamo.user.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRegisterRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

}
