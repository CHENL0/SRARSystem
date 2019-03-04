package com.example.srarsystem.commons.security;

import com.example.srarsystem.entity.RoleInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.RoleRepository;
import com.example.srarsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * @author Chen
 * @createTime 26 21:13
 * @description
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RoleInfo roleInfo = roleRepository.findByRoleName("ROLE_USER");
        UserInfo user = userRepository.findOneByUserName(username);
        user.setRoles(Collections.singleton(roleInfo));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
