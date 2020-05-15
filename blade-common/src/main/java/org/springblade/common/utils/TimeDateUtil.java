package org.springblade.common.utils;
import org.springblade.common.enums.TimeEnumLevel;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeDateUtil {
    /**
     * 根据任务类型获取开始时间
     *
     * @return Long
     */
    public static Date getTaskStartTime(Integer taskType) {
        Date starTime = null;
            //日
        if (taskType.equals(TimeEnumLevel.DAY_TASK.getCode())) {
            starTime = getTodayStarTime();
            //星期
        } else if (taskType.equals(TimeEnumLevel.WEEK_TASK.getCode())) {
            starTime = getNewWeekStarTime();
            //月
        } else if (taskType.equals(TimeEnumLevel.MONTH_TASK.getCode())) {
            starTime = getNewMonthStartTime();
            //年
        } else {
            starTime = getYearStartTime();
        }
        return starTime;
    }

    /**
     * 获取今天开始时间
     *
     * @return Long
     */
    public static Date getTodayStarTime() {
        //获取当前一天的开始时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDay = calendar.getTime();
        return startDay;
    }

    /**
     * 获取昨天开始时间
     *
     * @return Long
     */
    public static Date getYesterdayStarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date starTime = calendar.getTime();
        return starTime;
    }

    /**
     * 获取昨天结束时间
     *
     * @return Long
     */
    public static Date getYesterdayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endTime = calendar.getTime();
        return endTime;
    }

    /**
     * 获取当前星期的开始时间
     *
     * @return Long
     */
    public static Date getWeekStarTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public static Date getNewWeekStarTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -6);
        return cal.getTime();
    }


    public static Timestamp getDayStartTime(Date d) {
        Calendar cal = Calendar.getInstance();
        if (null != d) {
            cal.setTime(d);
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 获取当前月的开始时间
     *
     * @return Long
     */
    public static Date getMonthStartTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -30);
        return cal.getTime();
    }

    public static Date getNewMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, -30);
        return cal.getTime();
    }

    // 获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    // 获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * 获取当前年的开始时间
     *
     * @return Long
     */
    public static Date getYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        Date startTime = getDayStartTime(cal.getTime());
        return startTime;
    }
}
