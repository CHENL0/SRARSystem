package com.example.srarsystem.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Chen
 * @createTime 20181021 0:19
 * @description the utils of date
 */
public class DateUtils {
    /**
     * @Title:getTimestamp
     * @Description: get the timestamp to send to remote sms server
     * @param:
     * @return:String
     */
    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String date = sdf.format(new Date());
        return date;
    }
}
