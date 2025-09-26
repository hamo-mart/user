package com.hamo.user.repository.role;

import com.hamo.user.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("SELECT r.name FROM Role r JOIN UserRole ur on ur.role.id = r.id where ur.user.id =:userId")
    List<String> findRolesByUserId(Long userId);
}
