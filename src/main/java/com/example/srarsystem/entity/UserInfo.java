package com.example.srarsystem.entity;

import lombok.Data;

/**
 * @author Chen
 * @createTime 15 22:29
 * @description the info of user
 */
@Data
public class UserInfo {
    private String userId;
    private String userName;
    private String userPassword;
    private String userAddress;
    private String userPhone;
    private String urSecurityQusertion;
    private String urSecurityAnswer;
    private int pjStatus;
    private String pjId;

    public UserInfo(String userId, String userName, String userPassword, String userPhone,
                    String urSecurityQusertion, String urSecurityAnswer) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.urSecurityQusertion = urSecurityQusertion;
        this.urSecurityAnswer = urSecurityAnswer;
    }
}
