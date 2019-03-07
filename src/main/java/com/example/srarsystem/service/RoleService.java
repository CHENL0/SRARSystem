package com.example.srarsystem.service;

import com.example.srarsystem.entity.RoleInfo;

import java.util.Optional;

/**
 * @author Chen
 * @createTime 04 11:05
 * @description
 */
public interface RoleService {
     RoleInfo getRoleByRoleName (String RoleName);
}
