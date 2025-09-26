package com.hamo.user.service.role;

import java.util.List;

public interface RoleService {

    List<String> getUserRolesByUserId(Long userId);
}
