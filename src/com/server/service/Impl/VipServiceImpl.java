package com.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.server.dao.GoodDao;
import com.server.dao.Impl.GoodDaoImpl;
import com.server.dao.Impl.VipDaoImpl;
import com.server.dao.VipDao;
import com.server.pojo.Goods;
import com.server.pojo.ScoreInfo;
import com.server.service.VipService;

import java.math.BigDecimal;

/**
 * @Author:zdc
 * @Date 2021/8/13 16:32
 * @Version 1.0
 */
public class VipServiceImpl implements VipService {
    private GoodDao gd = new GoodDaoImpl();
    private VipDao vd = new VipDaoImpl();

    @Override
    public String scoreExchange(String message) {
        //解析JSON
        ScoreInfo scoreInfo = JSON.parseObject(message, ScoreInfo.class);
        //调用数据库查询该商品是否存在
        Goods goods = gd.queryGoodByNumber(scoreInfo.getGoodNumber());
        //如果不存在，返回提示信息
        if (goods==null){
            return "兑换失败，该商品不存在！";
        }
        //判断会员积分是否足够
        int goodScore = goods.getPrice().multiply(new BigDecimal("10")).multiply(new BigDecimal(scoreInfo.getGoodQuantity())).intValue();
        if (scoreInfo.getScore()< goodScore){
            return "兑换失败，积分不足！";
        }
        //调用数据库修改积分表
        vd.updateVipScore(scoreInfo.getVipNumber(),goodScore);
        //调用数据库添加积分兑换数据
        vd.insertScoreInfo(scoreInfo);
        return JSON.toJSONString(goodScore);
    }
}
