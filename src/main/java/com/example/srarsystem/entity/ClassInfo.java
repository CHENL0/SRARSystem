package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 20181004 13:53
 * @description the info of class
 */
@Data
@Entity
public class ClassInfo {
    @Id
    private int classId;
    /**
     * “基础研究” “应用研究” “开发研究”
     */
    private String classCategories;
}
