package com.example.srarsystem.entity;

import lombok.Data;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Date lastPasswordResetDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleInfo> roles = new HashSet<>();
}
