package com.hamo.user.service.user.impl;

import com.hamo.user.domain.user.User;
import com.hamo.user.dto.user.UserRegisterRequest;
import com.hamo.user.dto.user.UserResponse;
import com.hamo.user.dto.user.UserUpdateRequest;
import com.hamo.user.exception.UserNotFoundException;
import com.hamo.user.repository.user.UserRepository;
import com.hamo.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponse createLocalUser(UserRegisterRequest request) {

        User user = User.createLocalUser(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                null);
        //TODO 이미지 저장구현(이미지서버와 통신)

        User save = userRepository.save(user);
        return toResponse(save);
    }
    @Transactional
    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.updateNickname(request.getNickname());
        user.updatePassword(request.getPassword());

        //TODO 이미지 저장구현(이미지서버와 통신)
        user.updateProfileImageId(null);


        return toResponse(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findUsers(pageable);
    }
    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}
