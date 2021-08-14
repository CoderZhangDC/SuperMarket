package com.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.server.dao.*;
import com.server.dao.Impl.*;
import com.server.pojo.Employee;
import com.server.pojo.Vip;
import com.server.service.AdminService;
import com.server.utils.VipNumberUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/11 20:58
 * @Version 1.0
 */
public class AdminServiceImpl implements AdminService {
    private EmpDao ed = new EmpDaoImpl();
    private RoleDao rd =new RoleDaoImpl();
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
    public String addEmp(String message,String role) {
        //把信息转换成Employee类
        Employee employee = JSON.parseObject(message, Employee.class);
        //根据不同的角色获取不同的角色id
        employee.setRole(rd.queryRoleIdByName(role));
        //调用数据库添加员工
        int i = ed.insertEmp(employee);
        //如果i=1，则证明插入成功
        return i==1?"添加成功！":"添加失败！";
    }

    @Override
    public String updateEmp(String message) {
        //把信息转换成Employee类
        Employee employee = JSON.parseObject(message, Employee.class);
        //调用数据库修改员工
        System.out.println(employee);
        int i = ed.updateEmp(employee);
        //如果i=1，则证明修改成功
        return i==1?"修改成功！":"修改失败！";
    }

    @Override
    public String deleteEmp(String message,String role) {
        //把信息转换成Employee类
        Employee employee = new Employee();
        employee.setNumber(message);
        //根据角色查询角色id
        employee.setRole(rd.queryRoleIdByName(role));
        //调用数据库删除员工
        int i = ed.deleteEmp(employee);
        //如果i=1，则证明删除成功
        return i==1?"删除成功！":"删除失败！";
    }

    @Override
    public String queryAllClock() {
        //调用数据库查询员考勤信息
        return JSON.toJSONString(cd.queryAllClock());
    }

    @Override
    public String queryTodayClock() {
        //调用数据库查询员工考勤信息
        return JSON.toJSONString(cd.queryTodayClock());
    }

    @Override
    public String queryTotalSell() {
        //调用数据库查询总营业额
        return JSON.toJSONString(sd.queryTotalSell());
    }

    @Override
    public String queryTodaySell() {
        //调用数据库查询今日营业额
        return JSON.toJSONString(sd.queryTodaySell());
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
        return i==1?"添加成功！":"添加失败！";
    }

    @Override
    public String updateVip(String message) {
        Vip vip = JSON.parseObject(message, Vip.class);
        //调用数据库修改会员信息
        int i = vd.updateVip(vip);
        return i==1?"修改成功！":"修改失败！";
    }

    @Override
    public String deleteVip(String message) {
        //调用数据库删除会员信息
        int  i = vd.deleteVip(JSON.parseObject(message,String.class));
        return i==1?"删除成功！":"删除失败！";
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
        return i==1?"添加成功！":"添加失败，该工作日已存在！";
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
        return i==1?"删除成功！":"删除失败，该工作日不存在！";
    }


}
