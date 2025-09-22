package com.hamo.user.dto.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;

    private Set<String> roles;

    @QueryProjection
    public UserResponse(Long id, String email, String nickname, String profileImageUrl) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;

    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
