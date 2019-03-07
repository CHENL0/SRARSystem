package com.example.srarsystem.commons.security;

import com.example.srarsystem.entity.AdminInfo;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Chen
 * @createTime 26 21:10
 * @description
 */
public class JwtUserFactory {


    private JwtUserFactory() {
    }
    public static JwtUser createUser(UserInfo user) {
        return new JwtUser(
                user.getUserId(),
                user.getUserName(),
                user.getUserPassword(),
                mapToGrantedAuthoritiesForUser(user),
                user.getLastPasswordResetDate()
        );
    }
    public static JwtUser createAdmin(AdminInfo adminInfo) {
        return new JwtUser(
                adminInfo.getAdminId(),
                adminInfo.getAdminName(),
                adminInfo.getAdminPassword(),
                mapToGrantedAuthoritiesForAdmin(adminInfo),
                adminInfo.getLastPasswordResetDate()
        );
    }
    public static JwtUser createProfessor(ProfessorInfo professorInfo) {
        return new JwtUser(
                professorInfo.getPfId(),
                professorInfo.getPfName(),
                professorInfo.getPfPassword(),
                mapToGrantedAuthoritiesForProfessor(professorInfo),
                professorInfo.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthoritiesForUser(UserInfo user) {
        return  user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }
    private static List<GrantedAuthority> mapToGrantedAuthoritiesForAdmin(AdminInfo admin) {
        return  admin.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }
    private static List<GrantedAuthority> mapToGrantedAuthoritiesForProfessor(ProfessorInfo professorInfo) {
        return  professorInfo.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }

}
