package com.example.srarsystem.commons;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Chen
 * @createTime 2019116 16:38
 * @description the util for access control allow
 */
public class AccessUtils {
    public static void getAccessAllow (HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }
}
