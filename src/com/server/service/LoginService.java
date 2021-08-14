package com.server.service;

/**
 * @Author:zdc
 * @Date 2021/8/11 19:17
 * @Version 1.0
 */
public interface LoginService {

    //员工登录
    String EmpLogin(String message);

    //会员登录
    String VipLogin(String message);
}
