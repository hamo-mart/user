package com.hamo.user.repository.user;

import com.hamo.user.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<UserResponse> findUsers(Pageable pageable);

    UserResponse findUserById(Long userId);
}
