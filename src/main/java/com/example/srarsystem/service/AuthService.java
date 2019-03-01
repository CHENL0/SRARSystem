package com.example.srarsystem.service;

import com.example.srarsystem.entity.UserInfo;

/**
 * @author Chen
 * @createTime 28 23:57
 * @description
 */
public interface AuthService {
    UserInfo register(UserInfo userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
