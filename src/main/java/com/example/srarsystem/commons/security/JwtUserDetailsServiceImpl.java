package com.example.srarsystem.commons.security;

import com.example.srarsystem.entity.AdminInfo;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.RoleInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.repository.AdminRepository;
import com.example.srarsystem.repository.ProfessorRepository;
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
    private ProfessorRepository professorRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.contains("USER")){
            RoleInfo roleInfo = roleRepository.findByRoleName("ROLE_USER");
            UserInfo user = userRepository.findOneByUserName(username);
            user.setRoles(Collections.singleton(roleInfo));
            if (user == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                return JwtUserFactory.createUser(user);
            }
        }else if(username.contains("ADMIN")){
            RoleInfo roleInfo = roleRepository.findByRoleName("ROLE_ADMIN");
            AdminInfo admin = adminRepository.findOneByAdminName(username);
            admin.setRoles(Collections.singleton(roleInfo));
            if (admin == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                return JwtUserFactory.createAdmin(admin);
            }
        }else if(username.contains("PROFESSOR")){
            RoleInfo roleInfo = roleRepository.findByRoleName("ROLE_PROFESSOR");
            ProfessorInfo professor = professorRepository.findOneByPfName(username);
            professor.setRoles(Collections.singleton(roleInfo));
            if (professor == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                return JwtUserFactory.createProfessor(professor);
            }
        }
        return null;
    }
}
