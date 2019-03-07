package com.example.srarsystem.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Chen
 * @createTime 07 11:08
 * @description
 */
@Configuration
public class WebMvcConf implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/resources/static/**").addResourceLocations("classpath:/resources/static/");
        //上传的图片在D盘下的OTA目录下，访问路径如：http://localhost:8081/OTA/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中OTA表示访问的前缀。"file:D:/OTA/"是文件真实的存储路径
        registry.addResourceHandler("/icon/**").addResourceLocations("file:E:/pjStatic/icon/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:E:/pjStatic/images/");
        registry.addResourceHandler("/test/**").addResourceLocations("file:E:/test/");
    }

}
