package com.server.dao;

import com.server.pojo.ScoreInfo;
import com.server.pojo.Vip;

import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/11 19:56
 * @Version 1.0
 */
public interface VipDao {
    //根据number获取vip信息
    Vip findVipByNumber(String number);

    //查询所有会员信息
    List<Vip> queryAllVip();

    //查询最后以为vip的number
    String queryLastVipNumber();

    //添加Vip
    int insertVip(Vip vip);

    //修改vip
    int updateVip(Vip vip);

    //删除vip
    int deleteVip(String message);

    //修改vip积分
    void updateVipScore(String vipNumber, int goodScore);

    //添加积分兑换记录
    void insertScoreInfo(ScoreInfo scoreInfo);

}
