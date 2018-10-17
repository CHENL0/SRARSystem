package com.example.srarsystem.service;

/**
 * @author Chen
 * @createTime 20181017 22:03
 * @description the interface of UserService
 */
public interface UserService {
    /**
     * @Description  //TODO
     * @Author Chen
     * @DateTime 2018/10/17
     * @Param userName,userPassword
     * @Return
     */
    boolean register(String userName,String userPassword);
     /**
     * @Description  //TODO
     * @Author Chen
     * @DateTime 2018/10/17
     * @Param
     * @Return
     */
    boolean login(String userName,String userPassword);
}
