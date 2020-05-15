package org.springblade.common.utils;

import java.util.Random;

/**
 * @Author
 * @Date 2020/4/7 23:42
 * @Description 生成唯一的主键 随机数
 * 格式 ： 时间+随机数
 * synchronized 为保证多线程时不重复
 */
public class KeyUtil {

    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
