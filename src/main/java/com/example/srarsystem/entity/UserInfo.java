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
    private int pjStatus;
    private String pjId;
}
