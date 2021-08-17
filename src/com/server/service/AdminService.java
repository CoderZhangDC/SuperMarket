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

    //通过员工编号查询员工信息
    String queryEmpByNumber(String message);

    //查询指定日期的考勤信息
    String queryClockByDate(String message);

    //查看指定日期的异常考勤信息
    String queryErrorClockByDate(String message);

    //根据员工编号查询员工考勤记录
    String queryClockByEmp(String message);

    //补卡
    String queryReClock(String message,String type);

    //查询本月销售额度
    String queryMonthSell();

    //查询本季度销售额度
    String querySeasonSell();

    //查询指定日期段的销售额度
    String queryRangeSell(String message);

    //根据条件查找
    String queryEmpByCondition(String message,String type);

    //查找已离职的员工
    String queryEmpOfResign();

    //查询薪水前10或后5
    String querySalaryTop(String type);

    //薪水区间查询
    String querySalaryRange(String message);

    //更加number查找vip
    String queryVipByNumber(String message);
}
