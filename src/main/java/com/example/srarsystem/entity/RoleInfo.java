package com.example.srarsystem.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Chen
 * @createTime 26 21:19
 * @description
 */
@Entity
@Data
public class RoleInfo {
    @Id
    private String id;
    private String roleName;
}
