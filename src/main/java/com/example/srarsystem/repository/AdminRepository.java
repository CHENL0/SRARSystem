package com.example.srarsystem.repository;

import com.example.srarsystem.entity.AdminInfo;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chen
 * @createTime 20181020 10:34
 * @description the repository of admin
 */
@Repository
public interface AdminRepository extends JpaRepository<AdminInfo, String> {
    AdminInfo findByAdminNameAndAdminPassword(String adminName, String adminPassword);

    AdminInfo findOneByAdminName(String adminName);
}

