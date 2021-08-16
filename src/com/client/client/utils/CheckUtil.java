package com.client.client.utils;

import java.util.regex.Pattern;

/**
 * @Author:zdc
 * @Date 2021/8/16 9:46
 * @Version 1.0
 */
public class CheckUtil {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            boolean matches = Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
            if (!matches){
                System.out.println("手机格式输入有误！");
            }
            return matches;
        }
        return false;
    }

    public static boolean isSex(String sex) {
        if ((sex != null) && (!sex.isEmpty())) {
            boolean flag = sex.equals("女")||sex.equals("男");
            if (!flag){
                System.out.println("性别格式输入有误！");
            }
            return flag;
        }
        return false;
    }

    public static boolean isPassword(String password){
        if ((password!=null)&&(!password.isEmpty())){
            boolean matches = Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$", password);
            if (!matches){
                System.out.println("密码输入格式有误，必须是由4-8位的数字和字母组成！");
            }
            return matches;
        }
        return false;
    }

    public static boolean isNumber(String number){
        if ((number!=null)&&(!number.isEmpty())){
            boolean matches = Pattern.matches("^S[0-9]{3,4}", number);
            if (!matches){
                System.out.println("员工编号输入格式有误，必须是由S开头后面追加3-4位数字组成！");
            }
            return matches;
        }
        return false;
    }

    public static boolean isDate(String date){
        if ((date!=null)&&(!date.isEmpty())){
            boolean matches = Pattern.matches("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)", date);
            if (!matches){
                System.out.println("日期输入有误！");
            }
            return matches;
        }
        return false;
    }
}
