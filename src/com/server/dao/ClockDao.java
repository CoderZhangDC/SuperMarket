package com.server.dao;

import com.server.pojo.ClockInfo;

import java.util.List;

public interface ClockDao {
    //查询员工考勤信息
    List<ClockInfo> queryAllClock();

    //查询指定日期的打卡记录
    List<ClockInfo> queryClockByDate(String date);

    //查询指定日期的异常打卡记录
    List<ClockInfo> queryErrorClockByDate(String date);

    //获取员工今天打卡情况
    ClockInfo queryClockInByNumber(String empNumber);

    //员工上班打卡
    void insertClockIn(String empNumber);

    //员工下班打卡（已打过上班卡）
    void updateClockOff(String empNumber);

    //员工下班打卡（未打过上班卡）
    void insertClockOff(String empNumber);

    //根据员工编号查找员工的考勤记录
    List<ClockInfo> queryClockByEmpNumber(String message);

    //员工补上班卡
    int updateClockIn(ClockInfo clockInfo);

    //员工补下班卡
    int updateClockOff2(ClockInfo clockInfo);
}
