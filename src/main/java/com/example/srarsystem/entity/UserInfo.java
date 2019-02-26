package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 15 22:29
 * @description the info of user
 */
@Data
@Entity
public class UserInfo {
    @Id
    private String userId;
    private String userName;
    private String userNickname;
    private String userPassword;
    private String userAddress;
    private String userPhone;
    private String userGender;
    private String userIntroduce;
    private String userIconName;
    private String urSecurityQuestion;
    private String urSecurityAnswer;
    private int pjStatus;
    private String pjId;
    private int delFlag;

    public UserInfo(String userId, String userName, String userPassword, String userPhone,
                    String urSecurityQuestion, String urSecurityAnswer) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.urSecurityQuestion = urSecurityQuestion;
        this.urSecurityAnswer = urSecurityAnswer;
    }
}
