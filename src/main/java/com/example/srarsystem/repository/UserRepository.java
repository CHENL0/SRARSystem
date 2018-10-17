package com.example.srarsystem.repository;

import com.example.srarsystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chen
 * @createTime 20181017 22:09
 * @description the repository of user
 */
public interface UserRepository extends JpaRepository<UserInfo,String> {

}
