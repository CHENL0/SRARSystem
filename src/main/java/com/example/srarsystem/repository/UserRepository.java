package com.example.srarsystem.repository;

import com.example.srarsystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Chen
 * @createTime 20181017 22:09
 * @description the repository of user
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo,String> {

    UserInfo findByUserNameAndUserPassword(String userName,String userPassword);
    UserInfo getUserInfoByUserId(String userId);
    UserInfo findOneByUserName(String userName);
    UserInfo getUserInfoByUserPhone(String userPhone);
}
