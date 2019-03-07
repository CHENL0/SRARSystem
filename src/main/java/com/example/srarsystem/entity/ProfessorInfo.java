package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Chen
 * @createTime 20181015 22:38
 * @description the info of professor
 */
@Data
@Entity
public class ProfessorInfo {
    @Id
    private String pfId;
    private String pfName;
    private String pfNickname;
    private String pfType;
    private String pfPassword;
    private String pfPhone;
    private String pfIntroduce;
    private String pfPicture;
    private String userName;
    private String pfAddress;
    private String pfGender;
    private String pfSecurityQuestion;
    private String pfSecurityAnswer;
    private int pfSubmitCount;
    private int pfSuccessCount;
    private int delFlag;
    private Date lastPasswordResetDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleInfo> roles = new HashSet<>();

    public ProfessorInfo() {
        this.pfPicture = "noting.png";
        this.pfNickname = "懒人";
        this.pfIntroduce = "这个人不懂得怎么介绍自己";
    }
}
