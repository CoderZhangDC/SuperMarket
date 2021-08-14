package com.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/12 16:10
 * @Version 1.0
 */
public class VipNumberUtil {

    //生成vip卡号
    public static String getVipNumber(String number){
        //去除参数前的vip
        //number的日期
        String dateString = number.substring(3,number.length()-4);
        //number的序号
        String index = number.substring(number.length()-4);
        //获取当前日期如，20210812
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String vipNumber =  sdf.format(new Date());
        //假使当前该时间和给的number时间相同，则在序号的基础上加1
        if (vipNumber.equals(dateString)){
            int i = (Integer.parseInt(index)+1);
            //如果计算后是1位数
            if (i<10){
                vipNumber += "000"+i;
            }else if (i<100){
                vipNumber += "00"+i;
            }else if (i<1000){
                vipNumber += "0"+i;
            }else if (i<10000){
                vipNumber += i;
            }
        }else{
            vipNumber += "0001";
        }
        return "vip"+vipNumber;
    }

}
