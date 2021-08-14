package com.server.dao;

import com.server.pojo.ClockInfo;

import java.util.List;

public interface ClockDao {
    //查询员工考勤信息
    List<ClockInfo> queryAllClock();

    //查询员工今日考勤信息
    List<ClockInfo> queryTodayClock();

    //获取员工今天打卡情况
    ClockInfo queryClockInByNumber(String empNumber);

    //员工上班打卡
    void insertClockIn(String empNumber);

    //员工下班打卡（已打过上班卡）
    void updateClockOff(String empNumber);

    //员工下班打卡（未打过上班卡）
    void insertClockOff(String empNumber);
}
