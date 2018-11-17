package com.example.srarsystem.service;

import com.example.srarsystem.entity.UserInfo;

/**
 * @author Chen
 * @createTime 20181017 22:03
 * @description the interface of UserService
 */
public interface UserService {

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/17
     * @Param
     * @Return
     */
    boolean userLogin(String userName, String userPassword);

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/20
     * @Param
     * @Return
     */
    UserInfo findOneByUserName(String userName);

    /**
     * @Description //TODO is the phone had register
     * @Author Chen
     * @DateTime 2018/10/20
     * @Param
     * @Return
     */
    boolean isPhoneRegister(String registerPhone);

        /**
         * @Description  //TODO
         * @Author Chen
         * @DateTime 2018/10/21
         * @Param
         * @Return
         */
    void registerUser(UserInfo userInfo);

    String getUrSecurityQuestionByUserName(String userName);

    boolean updateUserPassowrd(String userName,String urSecurityAnswer,String newUserPassword);

    UserInfo getUserInfoByUserId(String userId);
}
