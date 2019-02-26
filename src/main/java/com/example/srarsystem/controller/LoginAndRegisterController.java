package com.example.srarsystem.controller;


import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.commons.DateUtils;
import com.example.srarsystem.commons.SendSmsUtils;
import com.example.srarsystem.commons.UUIDUtils;
import com.example.srarsystem.entity.AdminInfo;
import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.UserInfo;
import com.example.srarsystem.service.AdminService;
import com.example.srarsystem.service.ProfessorService;
import com.example.srarsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Chen
 * @createTime 20181020 11:26
 * @description the controller of login and register
 */
@RestController
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


    @RequestMapping("/loginIndex")
    public ModelAndView index(Model model, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("loginSign.html");
        return modelAndView;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @PostMapping(value = "/login")
    public @ResponseBody
    Object login(@RequestParam(value = "loginName") String loginName,
                 @RequestParam(value = "loginPassword") String loginPassword,
                 HttpServletResponse response, HttpServletRequest request) {
        AccessUtils.getAccessAllow(response);
        String trimLoginName = StringUtils.trimWhitespace(loginName);
        Map<String,String> responseType = new HashMap<>();
        if (StringUtils.substringMatch(trimLoginName, 0, "ADMIN")) {
            if (adminService.adminLogin(trimLoginName, loginPassword)) {
                AdminInfo adminInfo = adminService.findOneByAdminName(trimLoginName);
                request.getSession().setAttribute("adminInfo", adminInfo);
                responseType.put("responseType","success");
                return responseType;
            }
            responseType.put("responseType","false");
            return responseType;
        } else if (StringUtils.substringMatch(trimLoginName, 0, "PROFESSOR")) {
            if (professorService.pfLogin(trimLoginName, loginPassword)) {
                ProfessorInfo professorInfo = professorService.findOneByPfName(trimLoginName);
                if(professorInfo.getDelFlag()==0){
                    request.getSession().setAttribute("professorInfo", professorInfo);
                    responseType.put("responseType","success");
                    return responseType;
                }else {
                    responseType.put("responseType","freeze");
                    return responseType;
                }
            }
            responseType.put("responseType","false");
            return responseType;
        } else if (StringUtils.substringMatch(trimLoginName, 0, "USER")) {
            if (userService.userLogin(trimLoginName, loginPassword)) {
                UserInfo userInfo = userService.findOneByUserName(trimLoginName);
                if(userInfo.getDelFlag()==0){
                    request.getSession().setAttribute("userInfo", userInfo);
                    responseType.put("responseType","success");
                    return responseType;
                }else {
                    responseType.put("responseType","freeze");
                    return responseType;
                }
            }
            responseType.put("responseType","false");
            return responseType;
        }
        responseType.put("responseType","false");
        return responseType;
    }

    /**
     * @Description //TODO to get code by registerPhone
     * @Author Chen
     * @DateTime 2018/10/20
     * @Param
     * @Return
     */
    @PostMapping(value = "/getCode")
    public @ResponseBody
    Object getCode(String registerPhone,HttpServletResponse response, HttpServletRequest request) {
        AccessUtils.getAccessAllow(response);
        String rand = SendSmsUtils.createRandNum();
        SendSmsUtils.sendMsgTo(registerPhone, rand);
        request.getSession().setAttribute("code", SendSmsUtils.sigMD5(rand));
        return true;
    }

    /**
     * @Description //TODO verify the phone code
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @PostMapping("/verifyCode")
    public @ResponseBody
    Object verifyCode(String code, HttpServletRequest request,
                      HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        String codes = (String) request.getSession().getAttribute("code");
        if (codes != null && SendSmsUtils.sigMD5(code).equals(codes)) {
            return true;
        }
        return false;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @PostMapping("/verifyPhone")
    public @ResponseBody
    Object verifyPhone(String registerPhone, HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        if (userService.isPhoneRegister(registerPhone)) {
            return true;
        }
        return false;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @PostMapping(value = "/register")
    public @ResponseBody
    Object register(String registerPhone, String registerPassword,
                    String urSecurityQuestion, String urSecurityAnswer, HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        /*to get The time stamp*/
        String timestamp = DateUtils.getTimestamp();
        String userName = "USER_" + timestamp;
        UserInfo userInfo = new UserInfo(UUIDUtils.getUUID(), userName, registerPassword, registerPhone,
                urSecurityQuestion, urSecurityAnswer);
        userService.registerUser(userInfo);
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        return map;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @PostMapping(value = "/findUrSecurity")
    public @ResponseBody
    Object findUrSecurityQuestion(String userName, HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        String qusetion = userService.getUrSecurityQuestionByUserName(userName);
        Map<String, String> map = new HashMap<>();
        map.put("question", qusetion);
        return map;
    }

    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @PostMapping(value = "/updatePassword")
    public @ResponseBody
    Object findPassword(String userName, String urSecurityAnswer,
                        String newUserPassword, HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        userService.updateUserPassowrd(userName, urSecurityAnswer, newUserPassword);
        return true;
    }

    /**
     * @Description  //TODO
     * @Author Chen
     * @DateTime 2019/1/1
     * @Param
     * @Return
     */
    @PostMapping(value = "/validateAnswer")
    public @ResponseBody
    Object validateAnswer(String userName, String urSecurityAnswer, HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        if(userService.validateQuestion(userName,urSecurityAnswer)){
            return true;
        }
        return false;
    }
}
