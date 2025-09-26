package com.hamo.user.service.auth;

import com.hamo.user.domain.user.User;
import com.hamo.user.dto.auth.LoginResponse;
import com.hamo.user.exception.UserNotAuthenticationExceptoin;
import com.hamo.user.exception.UserNotFoundException;
import com.hamo.user.repository.role.RoleRepository;
import com.hamo.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public LoginResponse authenticate(String username, String password) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));

        boolean matches = passwordEncoder.matches(user.getPassword(), password);
        if (!matches){
            throw new UserNotAuthenticationExceptoin(user.getId());
        }

        Long userId = user.getId();
        var roles = roleRepository.findRolesByUserId(user.getId());
        String nickname = user.getNickname();

        return new LoginResponse(userId, roles, nickname);
    }
}
