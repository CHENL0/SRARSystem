package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen
 * @createTime 20181017 22:06
 * @description the impl of UserService
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UUIDUtils UUIDUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UUIDUtils UUIDUtils) {
        this.userRepository = userRepository;
        this.UUIDUtils = UUIDUtils;
    }

    /**
     * @Description //TODO for userLogin
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public boolean userLogin(String userName, String userPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserInfo userInfo = userRepository.findOneByUserName(userName);
//        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(userName, userPassword);
        if (encoder.matches(userPassword,userInfo.getUserPassword())) {
            return true;
        }
        return false;
    }


    /**
     * @Description //TODO get UserName By UserId
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public UserInfo findOneByUserName(String userName) {
        return userRepository.findOneByUserName(userName);
    }


    /**
     * @Description //TODO verify the phone is register
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public boolean isPhoneRegister(String registerPhone) {
        UserInfo userInfo = userRepository.findOneByUserPhone(registerPhone);
        if (userInfo != null && !userInfo.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * @Description //TODO save the userInfo
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public void registerUser(UserInfo userInfo) {
        userRepository.save(userInfo);
    }

    /**
     * @Description //TODO get user security question by userName
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public String getUrSecurityQuestionByUserName(String userName) {
        UserInfo userInfo = userRepository.findOneByUserName(userName);
        return userInfo.getUrSecurityQuestion();
    }

    /**
     * @Description //TODO update the user password
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public boolean updateUserPassowrd(String userName, String urSecurityAnswer, String newUserPassword) {
        UserInfo userInfo = userRepository.findOneByUserName(userName);
        if (userInfo.getUrSecurityAnswer().equals(urSecurityAnswer)) {
            userInfo.setUserPassword(newUserPassword);
            userRepository.save(userInfo);
        }
        return true;
    }

    /**
     * @Description //TODO get userinfo by userid
     * @Author Chen
     * @DateTime 2018/11/2
     * @Param
     * @Return
     */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return userRepository.getUserInfoByUserId(userId);
    }

    @Override
    public boolean validateQuestion(String userName, String urSecurityAnswer) {
        UserInfo userInfo = userRepository.findOneByUserName(userName);
        if(userInfo.getUrSecurityAnswer().equals(urSecurityAnswer)){
            return true;
        }
        return false;
    }

    @Override
    public UserInfo finishUserInfoData(UserInfo userInfo,UserInfo userInfoMapper,String fileName) {
        userInfo.setUserNickname(userInfoMapper.getUserNickname());
        userInfo.setUserAddress(userInfoMapper.getUserAddress());
        userInfo.setUserIntroduce(userInfoMapper.getUserIntroduce());
        userInfo.setUserGender(userInfoMapper.getUserGender());
        userInfo.setUserIconName(fileName);
        return userInfo;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userRepository.save(userInfo);
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        return userRepository.findAll();
    }

    @Override
    public void updateDelFlagByUserId(String userId,int delFlag) {
        UserInfo userInfo = userRepository.getUserInfoByUserId(userId);
        userInfo.setDelFlag(delFlag);
        userRepository.save(userInfo);
    }
}
