package com.hamo.user.service.role;

import com.hamo.user.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public List<String> getUserRolesByUserId(Long userId) {
        return roleRepository.findRolesByUserId(userId);
    }
}
