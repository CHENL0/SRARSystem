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
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
