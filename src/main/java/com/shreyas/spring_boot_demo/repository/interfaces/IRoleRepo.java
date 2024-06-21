package com.shreyas.spring_boot_demo.repository.interfaces;

import com.shreyas.spring_boot_demo.entity.Constants.RoleType;
import com.shreyas.spring_boot_demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleType roleName);
}
