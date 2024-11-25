package com.codeWithGanesh.repository;

import com.codeWithGanesh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
