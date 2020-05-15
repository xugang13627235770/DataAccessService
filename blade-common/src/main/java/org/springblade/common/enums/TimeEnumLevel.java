package org.springblade.common.enums;

/**
 * 设备看板时间类型定义
 */
public enum TimeEnumLevel {

    DAY_TASK(1, "day"), WEEK_TASK(2, "week"), MONTH_TASK(3, "month"), YEAR_TASK(4, "year");

    private Integer code;
    private String name;

    private TimeEnumLevel(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getTaskTimeDateCode(Integer taskType) {
        String result = null;
        TimeEnumLevel[] elements = TimeEnumLevel.values();
        for (TimeEnumLevel tl : elements) {
            Integer code = tl.getCode();
            if (taskType.equals(code)) {
                result = tl.getName();
                break;
            }
        }
        if (result == null) {
            result = "unknown";
        }
        return result;
    }
}
