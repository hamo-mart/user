package com.hamo.user.service.user;

import com.hamo.user.dto.user.UserRegisterRequest;
import com.hamo.user.dto.user.UserResponse;
import com.hamo.user.dto.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createLocalUser(UserRegisterRequest request);

    UserResponse updateUser(Long userId, UserUpdateRequest request);

    void deleteUser(Long userId);


    //어드민권한
    Page<UserResponse> getUsers(Pageable pageable);

    UserResponse getUserById(Long userId);
}
