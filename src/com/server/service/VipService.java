package com.server.service;

/**
 * @Author:zdc
 * @Date 2021/8/13 16:32
 * @Version 1.0
 */
public interface VipService {
    //积分兑换
    String scoreExchange(String message);

    //短信验证
    String checkCode(String message);
}
