package com.example.srarsystem.repository;

import com.example.srarsystem.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Chen
 * @createTime 04 10:28
 * @description
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleInfo, Long> {
    RoleInfo findByRoleName(String roleName);
}
