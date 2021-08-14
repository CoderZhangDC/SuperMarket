package com.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.server.dao.GoodDao;
import com.server.dao.Impl.GoodDaoImpl;
import com.server.dao.Impl.SellDaoImpl;
import com.server.dao.Impl.VipDaoImpl;
import com.server.dao.SellDao;
import com.server.dao.VipDao;
import com.server.pojo.Goods;
import com.server.pojo.SellInfo;
import com.server.pojo.Vip;
import com.server.service.CashierService;

/**
 * @Author:zdc
 * @Date 2021/8/12 19:38
 * @Version 1.0
 */
public class CashierServiceImpl implements CashierService {
    private VipDao vd = new VipDaoImpl();
    private GoodDao gd = new GoodDaoImpl();
    private SellDao sd = new SellDaoImpl();

    @Override
    public String queryVipByNumber(String message) {
        //查询vip用户
        Vip vip = vd.findVipByNumber(message);
        //如果存在,则返回会员号
        return vip!=null?JSON.toJSONString(vip):"会员卡不存在！";
    }

    @Override
    public String shell(String message) {
        //解析json
        SellInfo si = JSON.parseObject(message, SellInfo.class);
        //调用数据库查看该商品是否存在
        Goods goods = gd.queryGoodByNumber(si.getGoodNumber());
        //如果不存在
        if (goods==null){
            return "该商品不存在！";
        }
        //如果存在，判断库存是否充足，若不充足
        if (goods.getInventory()<si.getQuantity()){
            return "库存不足！";
        }
        //进行结算
        gd.updateInventory(si);
        //生成消费记录
        sd.insertShellInfo(si);
        si.setGoodName(goods.getName());
        //判断是否是会员付款
        if (si.getVipNumber()!=null){
            si.setGoodPrice(goods.getVipPrice());
        }else{
            si.setGoodPrice(goods.getPrice());
        }
        return JSON.toJSONString(si);
    }
}
