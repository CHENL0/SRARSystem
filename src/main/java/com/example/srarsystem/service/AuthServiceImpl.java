package com.example.srarsystem.service;

import com.example.srarsystem.commons.security.JwtTokenUtil;
import com.example.srarsystem.commons.security.JwtUser;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Arrays.asList;

/**
 * @author Chen
 * @createTime 28 23:57
 * @description
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("jwtUserDetailsServiceImpl")
    private  UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public UserInfo register(UserInfo userToAdd) {
        final String username = userToAdd.getUserName();
        if(userRepository.findOneByUserName(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getUserPassword();
        userToAdd.setUserPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
//        userToAdd.setRoles("ROLE_USER");
        return userRepository.save(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        if(passwordEncoder.matches(password,password)){
            // Reload password post-security so we can generate token
        if(username.contains("USER")){

        }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final String token = jwtTokenUtil.generateToken(userDetails);
            return token;
//        }
//            return "false";
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
