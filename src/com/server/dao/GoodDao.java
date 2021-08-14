package com.server.dao;

import com.server.pojo.Goods;
import com.server.pojo.SellInfo;

import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/12 19:45
 * @Version 1.0
 */
public interface GoodDao {
    //根据number查询商品
    Goods queryGoodByNumber(int goodNumber);

    //修改库存
    void updateInventory(SellInfo si);

    //查询库存少于100的商品
    List<Goods> queryGoodByInventory();

    //添加商品
    int addGoods(Goods goods);

    //修改库存
    void updateInventoryGoods(Goods goods);
}
