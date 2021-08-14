package com.server.service.Impl;

import com.server.dao.ClockDao;
import com.server.dao.Impl.ClockDaoImpl;
import com.server.pojo.ClockInfo;
import com.server.service.EmployeeService;

/**
 * @Author:zdc
 * @Date 2021/8/12 17:26
 * @Version 1.0
 */
public class EmployeeServiceImpl implements EmployeeService {
    private ClockDao cd = new ClockDaoImpl();

    @Override
    public String clockIn(String empNumber) {
        //获取员工今天的打卡情况
        ClockInfo ci = cd.queryClockInByNumber(empNumber);
        //若ci不为null，则证明打过卡
        if (ci!=null){
            return "上班打卡失败，请勿重复打卡！";
        }
        //若不存在，则进行数据库添加打卡记录
        cd.insertClockIn(empNumber);
        return "上班打卡成功！";
    }

    @Override
    public String clockOff(String empNumber) {
        //获取员工今天的打卡情况
        ClockInfo ci = cd.queryClockInByNumber(empNumber);
        //若ci不为null，则证明打过上班卡
        if (ci!=null){
            //如果用户已打过下班卡，返回提示信息
            if (ci.getClockOffTime()!=null){
                return "下班打卡失败，请勿重复打卡！";
            }
            //如果用户没打过下班卡，更新数据库用户打卡信息
            cd.updateClockOff(empNumber);
            return "下班打卡成功！";
        }
        //如果用户没打上班卡，插入打卡记录
        cd.insertClockOff(empNumber);
        return "下班打卡成功！";
    }
}
