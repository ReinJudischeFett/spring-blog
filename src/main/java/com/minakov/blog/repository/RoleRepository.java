package com.minakov.blog.repository;

import com.minakov.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
        }