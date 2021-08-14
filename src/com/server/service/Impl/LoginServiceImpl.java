package com.server.service.Impl;

import com.alibaba.fastjson.JSON;
import com.server.dao.EmpDao;
import com.server.dao.Impl.EmpDaoImpl;
import com.server.dao.VipDao;
import com.server.dao.Impl.VipDaoImpl;
import com.server.pojo.Employee;
import com.server.pojo.Vip;
import com.server.service.LoginService;

/**
 * @Author:zdc
 * @Date 2021/8/11 19:17
 * @Version 1.0
 */
public class LoginServiceImpl implements LoginService {
    private EmpDao ed = new EmpDaoImpl();
    private VipDao vd = new VipDaoImpl();

    @Override
    public String EmpLogin(String message) {
        //通过JSON解析成Employee对象
        Employee employee = JSON.parseObject(message, Employee.class);
        //调用数据库查询
        Employee e = ed.findEmpByNumber(employee.getNumber());
        //如果不存在返回空字符串
        if (e==null){
            return "";
        }
        //如果存在，则进行下一步密码校验
        if (employee.getPassword().equals(e.getPassword())){
            return JSON.toJSONString(e);
        }
        //密码错误则返回空字符串
        return "";
    }

    @Override
    public String VipLogin(String message) {
        //通过JSON解析成Employee对象
        Vip vip = JSON.parseObject(message, Vip.class);
        //调用数据库查询
        Vip v = vd.findVipByNumber(vip.getNumber());
        //如果不存在返回空字符串
        if (v==null){
            return "";
        }
        //如果存在，则进行下一步密码校验
        if (vip.getPhone().equals(v.getPhone())){
            return JSON.toJSONString(v);
        }
        //密码错误则返回空字符串
        return "";
    }
}
