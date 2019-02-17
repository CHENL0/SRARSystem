package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

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
    private String pfSecurityQuestion;
    private String pfSecurityAnswer;
    private int pfSubmitCount;
    private int pfSuccessCount;

    public ProfessorInfo() {
        this.pfPicture = "noting.png";
        this.pfNickname = "懒人";
        this.pfIntroduce = "这个人不懂得怎么介绍自己";
    }
}
