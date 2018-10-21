package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181017 22:06
 * @description the impl of UserService
 */
@Service
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;
    private final UUIDUtils UUIDUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UUIDUtils UUIDUtils) {
        this.userRepository = userRepository;
        this.UUIDUtils = UUIDUtils;
    }


    @Override
    public boolean userLogin(String userName, String userPassword) {
        UserInfo userInfo=userRepository.findByUserNameAndUserPassword(userName,userPassword);
        if(!userInfo.equals("") && userInfo != null){
            return true;
        }
        return false;
    }

    @Override
    public String getUserIdByUserName(String userName) {
        return userRepository.getUserIdByUserName(userName);

    }

    @Override
    public boolean isPhoneRegister(String registerPhone) {
        UserInfo userInfo = userRepository.getUserInfoByUserPhone(registerPhone);
        if(userInfo != null && !userInfo.equals("")){
            return true;
        }
        return false;
    }

    @Override
    public void registerUser(UserInfo userInfo) {
        userRepository.save(userInfo);
    }

    @Override
    public UserInfo getUrSecurityQuestionByUserName(String userName) {
        return userRepository.findOneByUserName(userName);
    }

    @Override
    public boolean updateUserPassowrd(String userName, String urSecurityAnswer, String newUserPassword) {
        UserInfo userInfo = userRepository.findOneByUserName(userName);
        if(userInfo.getUrSecurityAnswer().equals(urSecurityAnswer)){
            userInfo.setUserPassword(newUserPassword);
            userRepository.save(userInfo);
        }
        return true;
    }
}
