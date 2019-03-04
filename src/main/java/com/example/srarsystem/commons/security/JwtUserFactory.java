package com.example.srarsystem.commons.security;

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
    public static JwtUser create(UserInfo user) {
        return new JwtUser(
                user.getUserId(),
                user.getUserName(),
                user.getUserPassword(),
                mapToGrantedAuthorities(user),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserInfo user) {
        return  user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }

}
