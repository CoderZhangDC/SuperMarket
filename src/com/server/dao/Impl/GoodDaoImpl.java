package com.server.dao.Impl;

import com.server.dao.GoodDao;
import com.server.pojo.Goods;
import com.server.pojo.SellInfo;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/12 19:46
 * @Version 1.0
 */
public class GoodDaoImpl implements GoodDao {
    @Override
    public Goods queryGoodByNumber(int goodNumber) {
        ResultSet rs = JDBCUtil.executeQuery("select * from goods where c_number=?", String.valueOf(goodNumber));
        Goods goods = null;
        try {
            //如果存在
            if (rs.next()){
                goods = new Goods();
                goods.setNumber(Integer.parseInt(rs.getString("c_number")));
                goods.setName(rs.getString("c_name"));
                goods.setPrice(rs.getBigDecimal("c_price"));
                goods.setVipPrice(rs.getBigDecimal("vip_price"));
                goods.setInventory(rs.getInt("inventory"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return goods;
    }

    @Override
    public void updateInventory(SellInfo si) {
        JDBCUtil.executeUpdate("update goods set inventory=inventory-? where c_number = ?",si.getQuantity(),si.getGoodNumber());
    }

    @Override
    public List<Goods> queryGoodByInventory() {
        List<Goods> goodsList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from goods where inventory<100");
        try {
            while (rs.next()){
                Goods goods = new Goods();
                goods.setName(rs.getString("c_name"));
                goods.setInventory(rs.getInt("inventory"));
                goods.setNumber(Integer.parseInt(rs.getString("c_number")));
                goodsList.add(goods);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return goodsList;
    }

    @Override
    public int addGoods(Goods goods) {
        return JDBCUtil.executeUpdate("insert into goods(c_name,c_price,vip_price,inventory) values(?,?,?,?)",
                goods.getName(), goods.getPrice(), goods.getVipPrice(), goods.getInventory());
    }

    @Override
    public void updateInventoryGoods(Goods goods) {
        JDBCUtil.executeUpdate("update goods set inventory=? where c_number = ?",goods.getInventory(),goods.getNumber());

    }
}
