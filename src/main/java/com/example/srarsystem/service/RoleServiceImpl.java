package com.example.srarsystem.service;

import com.example.srarsystem.entity.RoleInfo;
import com.example.srarsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Chen
 * @createTime 04 11:06
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public RoleInfo getRoleByRoleName(String RoleName) {
        return roleRepository.findByRoleName(RoleName);
    }
}
