package com.example.srarsystem.entity;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 20181015 22:20
 * @description the info of admin
 */
@Data
@Entity
public class AdminInfo {
    @Id
    private String adminId;
    private String adminName;
    private String adminPassword;

}
