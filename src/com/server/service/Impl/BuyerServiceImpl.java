package com.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.server.dao.GoodDao;
import com.server.dao.Impl.GoodDaoImpl;
import com.server.pojo.Goods;
import com.server.service.BuyerService;

/**
 * @Author:zdc
 * @Date 2021/8/12 21:58
 * @Version 1.0
 */
public class BuyerServiceImpl implements BuyerService {
    private GoodDao gd = new GoodDaoImpl();

    @Override
    public String queryInventory() {
        return JSON.toJSONString(gd.queryGoodByInventory());
    }

    @Override
    public String queryGoodByNumber(String message) {
        Goods goods = gd.queryGoodByNumber(Integer.parseInt(message));
        return goods!=null?JSON.toJSONString(goods):"该商品不存在！";
    }

    @Override
    public String addGoods(String message) {
        System.out.println(message);
        int i  = gd.addGoods(JSON.parseObject(message,Goods.class));
        return i==1?"添加成功！":"添加失败！";
    }

    @Override
    public String addGoodInventory(String message) {
        Goods goods = JSON.parseObject(message, Goods.class);
        gd.updateInventoryGoods(goods);
        return "库存修改成功！";
    }

    @Override
    public String queryAllGoods() {
        //查询数据库中所有上架的商品
        return JSON.toJSONString(gd.queryAllGoods());
    }

    @Override
    public String delGoods(String message) {
        int i = gd.delGoods(message);
        return i==0?"下架失败！":"下架成功！";
    }
}
