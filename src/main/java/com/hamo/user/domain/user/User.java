package com.hamo.user.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    //중복확인
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "password", length = 300)
    private String password;

    //중복확인
    @Column(name = "nickname", length = 50, unique = true)
    private String nickname;

    @Column(name = "profile_image_id")
    private Long profileImageId;

    @Column(name = "provider", length = 50)
    private String provider;

    @Column(name = "provider_id", length = 200)
    private String providerId;

    private User(String email, String password, String nickname, Long profileImageId, String provider, String providerId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageId = profileImageId;
        this.provider = provider;
        this.providerId = providerId;
    }
    public static User createLocalUser(String email, String password, String nickname, Long profileImageId) {
        return new User(email, password, nickname, profileImageId, null, null);
    }
    public static User createOauthUser(String nickname, Long profileImageId, String provider, String providerId) {
        return new User(null,null, nickname, profileImageId, provider, providerId);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImageId(Long profileImageId) {
        this.profileImageId = profileImageId;
    }
}
