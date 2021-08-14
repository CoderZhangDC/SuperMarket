package com.server.dao;

import com.server.pojo.WorkDate;

import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/13 9:01
 * @Version 1.0
 */
public interface WorkDateDao {
    //查询所有工作日
    List<WorkDate> queryWorkDate();

    //添加工作日
    int addWorkDate(String message);

    //删除工作日
    int delWorkDate(String message);
}
