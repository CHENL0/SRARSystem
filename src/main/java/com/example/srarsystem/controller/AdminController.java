package com.example.srarsystem.controller;

import com.example.srarsystem.commons.AccessUtils;
import com.example.srarsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chen
 * @createTime 20181020 8:57
 * @description the controller of manager
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * @Description //TODO
     * @Author Chen
     * @DateTime 2018/10/21
     * @Param
     * @Return
     */
    @RequestMapping(value = "/registerForAdmin")
    public @ResponseBody
    Object register(String adminName, String adminPassword,
                    HttpServletResponse response) {
        AccessUtils.getAccessAllow(response);
        /*to get The time stamp*/
        adminService.saveAdmin(adminName,adminPassword);
        Map<String, String> map = new HashMap<>();
        map.put("responseType", "success");
        return map;
    }
}
