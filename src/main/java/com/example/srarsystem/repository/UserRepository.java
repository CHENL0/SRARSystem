package com.example.srarsystem.repository;

import com.example.srarsystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Chen
 * @createTime 20181017 22:09
 * @description the repository of user
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo,String> {

    UserInfo findByUserNameAndUserPassword(String userName,String userPassword);
    String getUserIdByUserName(String userName);
    UserInfo getUserInfoByUserId(String userId);
    UserInfo findOneByUserName(String userNmae);
    UserInfo getUserInfoByUserPhone(String userPhone);
}
