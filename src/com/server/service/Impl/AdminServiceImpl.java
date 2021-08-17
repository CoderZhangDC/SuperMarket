package com.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.client.client.utils.DateFormatUtil;
import com.server.dao.*;
import com.server.dao.Impl.*;
import com.server.pojo.ClockInfo;
import com.server.pojo.Employee;
import com.server.pojo.Vip;
import com.server.service.AdminService;
import com.server.utils.VipNumberUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/11 20:58
 * @Version 1.0
 */
public class AdminServiceImpl implements AdminService {
    private EmpDao ed = new EmpDaoImpl();
    private RoleDao rd = new RoleDaoImpl();
    private ClockDao cd = new ClockDaoImpl();
    private SellDao sd = new SellDaoImpl();
    private VipDao vd = new VipDaoImpl();
    private WorkDateDao wd = new WorkDateDaoImpl();

    @Override
    public String queryEmpByRole(String role) {
        //调用数据库查询所有的员工
        return JSON.toJSONString(ed.queryEmpByRole(role));
    }

    @Override
    public String addEmp(String message, String role) {
        //把信息转换成Employee类
        Employee employee = JSON.parseObject(message, Employee.class);
        //根据不同的角色获取不同的角色id
        employee.setRole(rd.queryRoleIdByName(role));
        //调用数据库添加员工
        int i = ed.insertEmp(employee);
        //如果i=1，则证明插入成功
        return i == 1 ? "添加成功！" : "添加失败！";
    }

    @Override
    public String updateEmp(String message) {
        //把信息转换成Employee类
        Employee employee = JSON.parseObject(message, Employee.class);
        //调用数据库修改员工
        System.out.println(employee);
        int i = ed.updateEmp(employee);
        //如果i=1，则证明修改成功
        return i == 1 ? "修改成功！" : "修改失败！";
    }

    @Override
    public String deleteEmp(String message, String role) {
        //把信息转换成Employee类
        Employee employee = new Employee();
        employee.setNumber(message);
        //根据角色查询角色id
        employee.setRole(rd.queryRoleIdByName(role));
        //调用数据库删除员工
        int i = ed.deleteEmp(employee);
        //如果i=1，则证明删除成功
        return i == 1 ? "删除成功！" : "删除失败！";
    }

    @Override
    public String queryAllClock() {
        //调用数据库查询员考勤信息
        return JSON.toJSONString(cd.queryAllClock());
    }

    @Override
    public String queryTodayClock() {
        //调用数据库查询员工考勤信息
        return JSON.toJSONString(cd.queryClockByDate(DateFormatUtil.dateFormat(new Date())));
    }

    @Override
    public String queryTotalSell() {
        //调用数据库查询总营业额
        return sd.querySellByType("total");
    }

    @Override
    public String queryTodaySell() {
        //调用数据库查询今日营业额
        return sd.querySellByType("today");
    }

    @Override
    public String queryMonthSell() {
        return sd.querySellByType("month");
    }

    @Override
    public String querySeasonSell() {
        return sd.querySellByType("season");
    }

    @Override
    public String queryRangeSell(String message) {
        //解析JSON
        List<String> list = JSON.parseArray(message, String.class);
        //调用数据库查询
        return sd.querySellByRange(list);
    }

    @Override
    public String queryEmpByCondition(String message, String type) {
        return JSON.toJSONString(ed.findEmpByCondition(message,type));
    }

    @Override
    public String queryEmpOfResign() {
        return JSON.toJSONString(ed.queryResignEmp());
    }

    @Override
    public String querySalaryTop(String type) {
        return JSON.toJSONString(ed.queryEmpBySalaryTop(type));
    }

    @Override
    public String querySalaryRange(String message) {
        //解析JSON
        List<Integer> integers = JSON.parseArray(message, Integer.class);
        //如果前面大于后面的值，交换
        if (integers.get(0)>integers.get(1)){
            int temp = 0;
            temp = integers.get(0);
            integers.set(0,integers.get(1));
            integers.set(1,temp);
        }
        //调用数据库查询
        return JSON.toJSONString(ed.queryEmpBySalaryRange(integers.get(0),integers.get(1)));
    }

    @Override
    public String queryVipByNumber(String message) {
        return JSON.toJSONString(vd.findVipByNumber(message));
    }

    @Override
    public String queryAllVip() {
        return JSON.toJSONString(vd.queryAllVip());
    }

    @Override
    public String addVip(String message) {
        Vip vip = JSON.parseObject(message, Vip.class);
        //查询数据库最后一位vip的number
        String number = vd.queryLastVipNumber();
        //生成新的会员卡号
        vip.setNumber(VipNumberUtil.getVipNumber(number));
        vip.setDate(new Date());
        //调用数据库进行添加VIP
        int i = vd.insertVip(vip);
        return i == 1 ? "添加成功！" : "添加失败！";
    }

    @Override
    public String updateVip(String message) {
        Vip vip = JSON.parseObject(message, Vip.class);
        //调用数据库修改会员信息
        int i = vd.updateVip(vip);
        return i == 1 ? "修改成功！" : "修改失败！";
    }

    @Override
    public String deleteVip(String message) {
        //调用数据库修改会员激活标识
        int i = vd.deleteVip(message);
        return i == 1 ? "注销成功！" : "注销失败！";
    }

    @Override
    public String queryAllEmp() {
        //调用数据库查询所有员工信息
        return JSON.toJSONString(ed.queryAllEmp());
    }

    @Override
    public String queryWorkDate() {
        return JSON.toJSONString(wd.queryWorkDate());
    }

    @Override
    public String addWorkDate(String message) {
        //判断日期格式是否有问题？
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(message);
        } catch (ParseException e) {
            //如果有问题直接返回
            return "输入格式有误！";
        }
        //调用数据库添加
        int i = wd.addWorkDate(message);
        return i == 1 ? "添加成功！" : "添加失败，该工作日已存在！";
    }

    @Override
    public String delWorkDate(String message) {
        //判断日期格式是否有问题？
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(message);
        } catch (ParseException e) {
            //如果有问题直接返回
            return "输入格式有误！";
        }
        int i = wd.delWorkDate(message);
        return i == 1 ? "删除成功！" : "删除失败，该工作日不存在！";
    }

    @Override
    public String queryEmpByNumber(String message) {
        Employee employee = ed.findEmpByNumber(message);
        //如果查询结果为空
        if (employee == null) {
            return "该员工不存在！";
        }
        //如果存在
        return JSON.toJSONString(employee);
    }

    @Override
    public String queryClockByDate(String message) {
        List<ClockInfo> clockInfoList = cd.queryClockByDate(message);
        return JSON.toJSONString(clockInfoList);
    }

    @Override
    public String queryErrorClockByDate(String message) {
        List<ClockInfo> clockInfoList = cd.queryErrorClockByDate(message);
        return JSON.toJSONString(clockInfoList);
    }

    @Override
    public String queryClockByEmp(String message) {
        //查询该员工是否存在
        Employee employee = ed.findEmpByNumber(message);
        //如果不存在
        if (employee == null) {
            return "找不到该员工！";
        }
        //如果存在
        List<ClockInfo> clockInfoList = cd.queryClockByEmpNumber(message);
        return JSON.toJSONString(clockInfoList);
    }

    /**
     * @param message
     * @param type    补卡类型（上班/下班）
     * @return
     */
    @Override
    public String queryReClock(String message, String type) {
        //解析JSON
        ClockInfo clockInfo = JSON.parseObject(message, ClockInfo.class);
        //查询用户的补卡次数是否充足
        Employee emp = ed.findEmpByNumber(clockInfo.getEmpNumber());
        //如果补卡机会少于等于0
        if (emp.getReClockCount() <= 0) {
            return "该员工的补卡次数已经用完！";
        }
        //如果充足
        //调用数据库减少用户补卡次数
        ed.updateEmpReClockCount(clockInfo.getEmpNumber());
        //进行补卡
        int i = 0;
        if (type.equals("in")) {
            i = cd.updateClockIn(clockInfo);
        } else {
            i = cd.updateClockOff2(clockInfo);
        }
        return i == 0 ? "补卡失败！" : "补卡成功！";
    }

}
