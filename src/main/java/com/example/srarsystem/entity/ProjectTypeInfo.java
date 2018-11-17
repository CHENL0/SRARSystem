package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 20181117 12:49
 * @description the info of project type
 */
@Data
@Entity
public class ProjectTypeInfo {
    @Id
    private int pjTypeId;
    private String pjType;
}
