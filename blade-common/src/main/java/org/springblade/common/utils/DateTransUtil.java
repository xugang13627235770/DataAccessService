package org.springblade.common.utils;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTransUtil {

    public static String defaultFormatString = "yyyy-MM-dd HH:mm:ss";
    public static String defaultFormatString_1 = "yyyy-MM-dd HH:mm:ss SSS";
    public static String defaultFormatString_2 = "yyyy-MM-dd'T'HH:mm:ss";
    public static String defaultFormatString_3 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static String defaultFormatString_4 = "yyyyMM";

    /**
     * 日期转String   defaultFormatString = "yyyy-MM-dd HH:mm:ss"
     *
     * @param date
     * @param format 日期格式
     * @return
     */
    public static String DateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null || "".equals(format)) {
            format = defaultFormatString;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 字符串转日期    defaultFormatString = "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String dateStr, String format){
        if (StringUtils.isEmpty(dateStr)){
            return new Date();
        }
        if (format == null || "".equals(format)) {
            format = defaultFormatString;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public  static String secondsTimeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = defaultFormatString;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)*1000));
    }

    /**
     * 日期格式字符串转换成10位时间戳
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStampSenconds(String date_str, String format) {
        try {
            if (format == null) {
                format = defaultFormatString_2;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = defaultFormatString;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 日期格式字符串转换成13位时间戳
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            if (format == null) {
                format = defaultFormatString_2;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String dateTransform(String date) throws ParseException {
        //String dateStr = "2017-05-18T10:26:10.488Z";
        SimpleDateFormat dff = new SimpleDateFormat(defaultFormatString_3, Locale.ENGLISH);//输入的被转化的时间格式
        SimpleDateFormat df1 = new SimpleDateFormat(defaultFormatString);//需要转化成的时间格式
        Date date1 = dff.parse(date);
        return df1.format(date1);
    }


    /**
     * 获取日期所属年份
     */
    public static Integer getDateYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    /**
     * 获取日期所属月份
     */
    public static Integer getDateMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }


    /**
     * 某时间前一个小时的时间
     */
    public static Date beforeOneHourToNowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return calendar.getTime();
    }

    /**
     * 某时间前10分钟的时间
     */
    public static Date beforeTenMinuteToNowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 10);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(StringToDate("2019-08-21 09:45:00","yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 某时间前30分钟的时间
     */
    public static Date before30MinuteToNowDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 30);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return calendar.getTime();
    }

    /**
     * 某时间后10分钟的时间
     */
    public static Date after10MinuteToNowDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 10);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return calendar.getTime();
    }


    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
