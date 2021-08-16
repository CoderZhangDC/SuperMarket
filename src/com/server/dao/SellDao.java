package com.server.dao;

import com.server.pojo.Report;
import com.server.pojo.SellInfo;

import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/12 14:45
 * @Version 1.0
 */
public interface SellDao {
    //查询销售额
    String querySellByType(String type);

    //插入消费记录
    void insertShellInfo(SellInfo si);

    //查看财务报表
    List<Report> queryReport();

    //查询时间段的销售额
    String querySellByRange(List<String> list);
}
