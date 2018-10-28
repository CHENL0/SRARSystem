package com.example.srarsystem.commons;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtils {

	/**
	 * @Author Chen
	 * @Description //TODO  produce uuid method
	 * @Date 15:01 2018/8/18
	 * @Param []
	 * @return java.lang.String
	 **/
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
}