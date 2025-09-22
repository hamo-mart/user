package com.hamo.user.repository.user;

import com.hamo.user.config.QueryDslConfig;
import com.hamo.user.domain.user.User;
import com.hamo.user.dto.user.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import static org.junit.jupiter.api.Assertions.*;

@Import(QueryDslConfig.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;



    @BeforeEach
    void setUp() {
        User user1 = User.createLocalUser("hongmart1@hongmart.com", "password","martking1", null);
        User user2 = User.createLocalUser("hongmart2@hongmart.com", "password","martking2", null);
        User user3 = User.createLocalUser("hongmart3@hongmart.com", "password","martking3", null);

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);

        testEntityManager.flush();
    }

    @AfterEach
    void tearDown() {
        testEntityManager.clear();
    }

    @Test
    @DisplayName("유저 등록테스트")
    void save() throws Exception {
        User user = User.createLocalUser("test@hongmart.com", "password","test", null);


        User save = userRepository.save(user);
        assertNotNull(save);
        assertEquals(user.getEmail(), save.getEmail());
        assertEquals(user.getPassword(), save.getPassword());
        assertEquals(user.getNickname(), save.getNickname());
        assertEquals(user.getId(), save.getId());
    }

    @Test
    @DisplayName("유저 수정테스트")
    void update() throws Exception {
        User user = User.createLocalUser("test@hongmart.com", "password","test", null);
        User save = userRepository.save(user);

        save.updateNickname("수정된 닉네임");
        save.updatePassword("수정된 패스워드");
        save.updateProfileImageId(1L);

        User update = userRepository.save(user);
        assertEquals(user.getNickname(), update.getNickname());
        assertEquals(user.getPassword(), update.getPassword());
        assertEquals(user.getEmail(), update.getEmail());
    }

    @Test
    @DisplayName("유저 페이징 조회")
    void findAll() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserResponse> users = userRepository.findUsers(pageable);

        assertNotNull(users);
        assertEquals(3, users.getTotalElements());
    }

    @Test
    @DisplayName("유저 단일 조회")
    void findById() throws Exception {
        User user = User.createLocalUser("findOne@hongmart.com", "password","findOne", null);
        testEntityManager.persist(user);
        testEntityManager.flush();

        Long userId = user.getId();
        UserResponse userById = userRepository.findUserById(userId);
        assertNotNull(userById);
        assertEquals(userById.getId(), userId);
        assertEquals(userById.getNickname(), "findOne");
    }



}