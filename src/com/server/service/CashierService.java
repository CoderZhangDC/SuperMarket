package com.server.service;

/**
 * @Author:zdc
 * @Date 2021/8/12 19:38
 * @Version 1.0
 */
public interface CashierService {
    //查询会员信息
    String queryVipByNumber(String message);
    //收银员结算
    String shell(String message);
}
