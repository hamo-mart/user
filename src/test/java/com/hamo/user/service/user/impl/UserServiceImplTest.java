package com.hamo.user.service.user.impl;

import com.hamo.user.domain.user.User;
import com.hamo.user.dto.user.UserRegisterRequest;
import com.hamo.user.dto.user.UserResponse;
import com.hamo.user.dto.user.UserUpdateRequest;
import com.hamo.user.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;



    @Test
    @DisplayName("유저 로컬 저장")
    void createUser() {
        UserRegisterRequest request = new UserRegisterRequest(
                "user@mart.com",
                "password",
                "nickname"
        );

        UserResponse response = new UserResponse(
                1L,
                "user@mart.com",
                "nickname"
        );

        User user = User.createLocalUser(
                "user@mart.com",
                "password",
                "nickname",
                null
        );

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserResponse localUser = userService.createLocalUser(request);

        assertNotNull(localUser);
        assertEquals(localUser.getId(), user.getId());
        assertEquals(localUser.getNickname(), user.getNickname());

    }

    @Test
    @DisplayName("유저 업데이트")
    void updateUser() {
        UserUpdateRequest request = new UserUpdateRequest(
                "updateNickname",
                "newPassword"
        );
        User user = User.createLocalUser(
                "user@mart.com",
                "password",
                "nickname",
                1L
        );
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.updateUser(1L, request);
        assertNotNull(userResponse);
        assertEquals(userResponse.getId(), user.getId());
        assertEquals(userResponse.getNickname(), user.getNickname());
    }


    @Test
    @DisplayName("유저 삭제")
    void deleteUser() {
        userService.deleteUser(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("유저 페이징 조회")
    void getUsers(){
        User user1 = User.createLocalUser(
                "user1@mart.com",
                "password",
                "nickname",
                null
        );
        User user2 = User.createLocalUser(
                "user2@mart.com",
                "password",
                "nickname",
                null
        );
        User user3 = User.createLocalUser(
                "user3@mart.com",
                "password",
                "nickname",
                null
        );
        Pageable pageable = PageRequest.of(0, 10);
        List<UserResponse> users = Arrays.asList(user1, user2, user3)
                        .stream().map(u -> new UserResponse(u.getId(), u.getEmail(), u.getNickname())).toList();
        Mockito.when(userRepository.findUsers(pageable)).thenReturn(new PageImpl<>(users, pageable, 3));

        Page<UserResponse> userResponses = userService.getUsers(pageable);

        assertNotNull(userResponses);
        assertEquals(userResponses.getContent(), users);
    }


    @Test
    @DisplayName("유저 단일 조회")
    void getUserById(){
        UserResponse actual = new UserResponse(
                1L,
                "one@mart.com",
                "oneman"
        );



        Mockito.when(userRepository.findUserById(Mockito.anyLong())).thenReturn(actual);

        UserResponse result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(result.getId(), actual.getId());
        assertEquals(result.getEmail(), actual.getEmail());
        assertEquals(result.getNickname(), actual.getNickname());

    }
}