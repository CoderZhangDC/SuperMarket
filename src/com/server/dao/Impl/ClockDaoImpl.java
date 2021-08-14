package com.server.dao.Impl;

import com.client.client.utils.DateFormatUtil;
import com.server.dao.ClockDao;
import com.server.pojo.ClockInfo;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/12 13:26
 * @Version 1.0
 */
public class ClockDaoImpl implements ClockDao {

    @Override
    public List<ClockInfo> queryAllClock() {
        List<ClockInfo> clockInfos = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from check_info,employee where check_info.employee_no=employee.number");
        try {
            while (rs.next()){
                ClockInfo ci = new ClockInfo();
                ci.setEmpNumber(rs.getString("employee_no"));
                ci.setEmpName(rs.getString("username"));
                ci.setClockDate(rs.getDate("work_date"));
                ci.setClockInTime(rs.getDate("clock_in_time"));
                ci.setClockOffTime(rs.getDate("clock_off_time"));
                ci.setClockInStatus(rs.getString("diff_in_status"));
                ci.setClockOffStatus(rs.getString("diff_off_time"));
                clockInfos.add(ci);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return clockInfos;
    }

    @Override
    public List<ClockInfo> queryTodayClock() {
        List<ClockInfo> clockInfos = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from check_info,employee where check_info.employee_no=employee.number and work_date=?", DateFormatUtil.dateFormat(new Date()));
        try {
            while (rs.next()){
                ClockInfo ci = new ClockInfo();
                ci.setEmpNumber(rs.getString("employee_no"));
                ci.setEmpName(rs.getString("username"));
                ci.setClockDate(rs.getDate("work_date"));
                ci.setClockInTime(rs.getDate("clock_in_time"));
                ci.setClockOffTime(rs.getDate("clock_off_time"));
                ci.setClockInStatus(rs.getString("diff_in_status"));
                ci.setClockOffStatus(rs.getString("diff_off_time"));
                clockInfos.add(ci);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return clockInfos;
    }

    @Override
    public ClockInfo queryClockInByNumber(String empNumber) {
        //查询员工打卡记录
        ResultSet rs = JDBCUtil.executeQuery(" select * from check_info where work_date=? and employee_no=?", DateFormatUtil.dateFormat(new Date()), empNumber);
        ClockInfo ci = null;
        try {
            //如果存在，创建对象
            if (rs.next()){
                ci = new ClockInfo();
                ci.setEmpNumber(rs.getString("employee_no"));
                ci.setClockInTime(rs.getDate("clock_in_time"));
                ci.setClockOffTime(rs.getDate("clock_off_time"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ci;
    }

    @Override
    public void insertClockIn(String empNumber) {
        JDBCUtil.executeUpdate("insert into clock_info(employee_no,clock_in_time,clock_date) values(?,?,?)"
                ,empNumber, DateFormatUtil.datetimeFormat(new Date()),DateFormatUtil.dateFormat(new Date()));
    }

    @Override
    public void updateClockOff(String empNumber) {
        JDBCUtil.executeUpdate("update clock_info set clock_off_time = ? where employee_no = ?",
                DateFormatUtil.datetimeFormat(new Date()),empNumber);
    }

    @Override
    public void insertClockOff(String empNumber) {
        JDBCUtil.executeUpdate("insert into clock_info(employee_no,clock_off_time,clock_date) values(?,?,?)"
                ,empNumber, DateFormatUtil.datetimeFormat(new Date()),DateFormatUtil.dateFormat(new Date()));
    }

}
