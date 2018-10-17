package com.example.srarsystem.service;

import com.example.srarsystem.commons.CommonsUtils;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181017 22:06
 * @description the impl of UserService
 */
@Service
public class UserServiceImpl implements UserService {

    private  UserRepository userRepository;
    private CommonsUtils commonsUtils;
    @Override
    public boolean register(String userName, String userPassword) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(userPassword);
        userInfo.setPjId(commonsUtils.getUUID());
        userRepository.save(userInfo);
        return true;
    }

    @Override
    public boolean login(String userName, String userPassword) {

        return false;
    }
}
