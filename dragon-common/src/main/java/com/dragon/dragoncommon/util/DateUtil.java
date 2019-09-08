package com.dragon.dragoncommon.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: shopping-base
 * @description: 日期工具类
 * @author: zhangsong
 * @create: 2019-09-08 10:28
 **/
@Slf4j
public class DateUtil {
    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String DATE_LONG = "yyyyMMddHHmmss";

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_SIMPLE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String DATE_SHORT = "yyyyMMdd";


    /**
     * 返回系统当前时间(精确到毫秒)，格式：yyyyMMddHHmmss
     *
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getSystemTime() {
        DateFormat df = new SimpleDateFormat(DATE_LONG);
        return df.format(new Date());
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getDateFormatter() {
        DateFormat df = new SimpleDateFormat(DATE_SIMPLE);
        return df.format(new Date());
    }

    /**
     * 获取两个日期之间相差的天数
     */
    public static int getDays(String start, String end){
        Date startDate = getDate(start);
        Date endDate = getDate(end);
        long days = (endDate.getTime() - startDate.getTime())/(1000*3600*24);
        return (int) days;
    }

    /**
     * 获取两个long类型日期之间相差的天数
     */
    public static int getLongBetweenDays(long start, long end){
        Date startDate = getDate(getShortDate(start));
        Date endDate = getDate(getShortDate(end));
        long days = (endDate.getTime() - startDate.getTime())/(1000*3600*24);
        return (int) days;
    }

    /**
     * 获取yyyy年MM月dd日格式的日期
     */
    public static String getDateYMD(){
        String dateStr = getDate();
        StringBuilder str = new StringBuilder();
        str.append(dateStr.substring(0,4)).append("年").append(dateStr.substring(4,6)).append("月")
                .append(dateStr.substring(6)).append("日");
        return str.toString();
    }
    /**
     * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
     */
    public static String getDate() {
        DateFormat df = new SimpleDateFormat(DATE_SHORT);
        return df.format(new Date());
    }

    /**
     * 获取所传日期年月日(精确到天)，格式：yyyyMMdd
     */
    public static String getShortDate(long timestamp) {
        DateFormat df = new SimpleDateFormat(DATE_SHORT);
        return df.format(new Date(timestamp));
    }

    /**
     * 获取时间string类型
     *
     * @param d      时间
     * @param format 格式
     */
    public static String getDate(Date d, String format) {
        if (null == d) {
            return "";
        }
        if (null == format || "".equals(format)) {
            format = DATE_SHORT;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    /**
     * 根据传入的时间获取短时间字符串
     * @param d
     * @return
     */
    public static String getDate(Date d) {
        return getDate(d, DATE_SHORT);
    }

    public static String getDate(String da, String formatS, String formatE) {
        DateFormat df = new SimpleDateFormat(formatS);
        Date date;
        try {
            date = df.parse(da);
            df = new SimpleDateFormat(formatE);
            return df.format(date);
        } catch (ParseException e) {
            log.info("getDate:{}", e);
        }
        return "";
    }

    /**
     * 将字符串[yyyyMMdd]转为时间
     * @param da
     * @return
     */
    public static Date getDate(String da) {
        return getDate(da, DATE_SHORT);
    }

    /**
     * 将字符串转为时间
     * @param da
     * @param format
     * @return
     */
    public static Date getDate(String da, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(da);
        } catch (ParseException e) {
            log.info("getDate:{}", e);
        }
        return null;
    }

    /**
     * 将时间戳转为时间字符串
     * @param time
     * @param format
     * @return
     */
    public static String getDate(long time, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(time);
    }

    /**
     * 获取系统当期时间
     */
    public static Date getCurrentDate() {
        return new Date();
    }
}
