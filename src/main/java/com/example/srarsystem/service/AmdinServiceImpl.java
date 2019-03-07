package com.example.srarsystem.service;

import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.AdminInfo;
import com.example.srarsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Chen
 * @createTime 20181020 10:54
 * @description the impl of adminservice
 */
@Service
public class AmdinServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AmdinServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean adminLogin(String adminName, String adminPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AdminInfo adminInfo = adminRepository.findOneByAdminName(adminName);
        if (encoder.matches(adminPassword,adminInfo.getAdminPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void saveAdmin(String adminName, String adminPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminId(UUIDUtils.getUUID());
        adminInfo.setAdminName(adminName);
        adminInfo.setAdminPassword(encoder.encode(adminPassword));
        adminRepository.save(adminInfo);
    }

    @Override
    public AdminInfo findOneByAdminName(String adminName) {
        return adminRepository.findOneByAdminName(adminName);
    }

}
