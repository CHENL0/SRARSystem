package com.example.srarsystem.controller;


import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.SendSmsUtils;
import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.AdminService;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Chen
 * @createTime 20181020 11:26
 * @description the controller of login and register
 */
@Controller
public class LoginAndRegisterController {
    private final UserService userService;
    private final AdminService adminService;
    private final ProfessorService professorService;

    @Autowired
    public LoginAndRegisterController(UserService userService, AdminService adminService,
                                      ProfessorService professorService) {
        this.userService = userService;
        this.adminService = adminService;
        this.professorService = professorService;
    }

    @PostMapping(value = "/login")
    public @ResponseBody
    Object login(String loginName, String loginPassowrd, HttpServletRequest request) {
        String trimLoginName = StringUtils.trimWhitespace(loginName);
        if (StringUtils.substringMatch(loginName, 0, "ADMIN")) {
            if (adminService.adminLogin(loginName, loginPassowrd)) {
                String adminId = adminService.getAdminIdByAdminName(loginName);
                request.getSession().setAttribute("adminId", adminId);
            }
            return false;
        } else if (StringUtils.substringMatch(loginName, 0, "PROFESSOR")) {
            if (professorService.pfLogin(loginName, loginPassowrd)) {
                String pfId = professorService.getPfIdByPfName(loginName);
                request.getSession().setAttribute("pfId", pfId);
            }
            return false;
        } else if (StringUtils.substringMatch(loginName, 0, "USER")) {
            if (userService.userLogin(loginName, loginPassowrd)) {
                String userId = userService.getUserIdByUserName(loginName);
                request.getSession().setAttribute("userId", userId);
            }
            return false;
        }
        return false;
    }

    /**
     * @Description //TODO to get code by registerPhone
     * @Author Chen
     * @DateTime 2018/10/20
     * @Param
     * @Return
     */
    @GetMapping(value = "/getCode")
    public @ResponseBody
    Object getCode(String registerPhone, HttpServletRequest request) {

        String rand = SendSmsUtils.createRandNum();
        SendSmsUtils.sendMsgTo(registerPhone, rand);
        request.getSession().setAttribute("code", SendSmsUtils.sigMD5(rand));
        return true;
    }

    @GetMapping("/verifyCode")
    public @ResponseBody
    Object verifyCode(String code, HttpServletRequest request,
                      HttpServletResponse response) {
        String codes = (String) request.getSession().getAttribute("code");
        if (codes != null && SendSmsUtils.sigMD5(code).equals(codes)) {
            return true;
        }
        return false;
    }

    @GetMapping("/verifyPhone")
    public @ResponseBody
    Object verifyPhone(String registerPhone) {
        if (userService.isPhoneRegister(registerPhone)) {
            return true;
        }
        return false;
    }

    public Object register(String registerPhone,String registerPassword) {
        /*to get The time stamp*/
        String timestamp = DateUtils.getTimestamp();
        String userName = "USER_" + timestamp;
        UserInfo userInfo = new UserInfo(UUIDUtils.getUUID(),userName,registerPassword,registerPhone);
        userService.registerUser(userInfo);
        return true;
    }
}
