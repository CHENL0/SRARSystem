package com.example.srarsystem.commons;

import java.text.ParseException;
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
        String dateReplace = date.replace("-","");
        return dateReplace;
    }

    /**
     * @Title:getTimestamp
     * @Description: get the current time for the scheduled;
     * @param:
     * @return:String
     */
    public static Date parseDateTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = null;
        try {
            parseDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDate;
    }
}
