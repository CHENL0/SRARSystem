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
    private String pfPassword;
    private String pfPhone;
}
