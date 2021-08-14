package com.server.service;

/**
 * @Author:zdc
 * @Date 2021/8/12 21:58
 * @Version 1.0
 */
public interface BuyerService {
    //查询库存小于100的商品
    String queryInventory();

    //根据number查询商品
    String queryGoodByNumber(String message);

    //添加商品
    String addGoods(String message);

    //添加库存
    String addGoodInventory(String message);
}
