package com.client.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/12 13:46
 * @Version 1.0
 *
 * 日期格式转换工具类
 */
public class DateFormatUtil {

    //日期转换工具
    public static String dateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    //时间转换工具
    public static String datetimeFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date==null?"没有打卡\t\t\t":sdf.format(date);
    }

}
