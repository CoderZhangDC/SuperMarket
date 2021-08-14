package com.server.service;

/**
 * @Author:zdc
 * @Date 2021/8/11 20:58
 * @Version 1.0
 */
public interface AdminService {

    //根据员工类型查询员工信息
    String queryEmpByRole(String role);

    //添加员工
    String addEmp(String message,String role);

    //更新收银员信息
    String updateEmp(String message);

    //删除收银员信息
    String deleteEmp(String message,String role);

    //查询所有员工的考勤情况
    String queryAllClock();

    //查询今日员工的考勤情况
    String queryTodayClock();

    //查询总营业额
    String queryTotalSell();

    //查询今日营业额
    String queryTodaySell();

    //查询所有会员信息
    String queryAllVip();

    //添加会员
    String addVip(String message);

    //修改会员
    String updateVip(String message);

    //删除会员
    String deleteVip(String message);

    //查询所有员工
    String queryAllEmp();

    //工作日查询
    String queryWorkDate();

    //添加工作日
    String addWorkDate(String message);

    //删除工作日
    String delWorkDate(String message);
}
