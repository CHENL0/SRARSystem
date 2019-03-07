package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int delFlag;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleInfo> roles = new HashSet<>();

    private Date lastPasswordResetDate;
    public UserInfo() {
    }

    public UserInfo(String userId, String userName, String userPassword, String userPhone,
                    String urSecurityQuestion, String urSecurityAnswer,Set<RoleInfo> roles) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.urSecurityQuestion = urSecurityQuestion;
        this.urSecurityAnswer = urSecurityAnswer;
        this.roles = roles;
        this.userIconName = "noting.png";
        this.userNickname = "懒人";
        this.userIntroduce = "这个人不懂得怎么介绍自己";
    }
}
