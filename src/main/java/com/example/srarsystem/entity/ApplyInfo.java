package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 12 17:01
 * @description
 */
@Data
@Entity
public class ApplyInfo {
    @Id
    private String applyId;
    private String applyUser;
    private String applyType;
    private String fileName;
    private String filePath;


}
