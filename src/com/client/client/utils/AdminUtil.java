package com.client.client.utils;

import com.alibaba.fastjson.JSON;
import com.server.pojo.ClockInfo;
import com.server.pojo.Employee;
import com.server.pojo.Vip;
import com.server.pojo.WorkDate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/12 8:58
 * @Version 1.0
 */
public class AdminUtil {
    private static Scanner sc = new Scanner(System.in);

    //查询员工
    public static void queryEmp(DataOutputStream dos, DataInputStream dis,String role) throws IOException {
        if (role.equals("cashier")){
            System.out.println("---------------收银员名单---------------");
            dos.writeUTF("Admin_CashierAdmin_query:");
        }else{
            System.out.println("---------------采购员名单---------------");
            dos.writeUTF("Admin_BuyerAdmin_query:");
        }
        //获取员工信息
        String empString = dis.readUTF();
        //转换成集合
        List<Employee> employeeList = JSON.parseArray(empString, Employee.class);
        //打印输出
        System.out.println("编号\t\t姓名\t\t性别\t电话");
        for (Employee e:employeeList){
            System.out.println(e.getNumber()+"\t\t"+e.getUsername()+"\t\t"+e.getSex()+"\t\t"+e.getPhone());
        }
    }

    //添加员工
    public static void addEmp(DataOutputStream dos, DataInputStream dis,String role) throws IOException {
        while (true) {
            System.out.println("请填写信息(编号-姓名-密码-性别-电话)");
            //获取用户输入信息
            String cashierInfo = sc.next();
            String[] split = cashierInfo.split("-");
            //创建员工对象
            Employee employee = new Employee();
            try {
                employee.setNumber(split[0]);
                employee.setUsername(split[1]);
                employee.setPassword(split[2]);
                employee.setSex(split[3]);
                employee.setPhone(split[4]);
            } catch (Exception e) {
                //输入格式有误，跳出本次循环，重新输入
                System.out.println("输入格式有误！");
                continue;
            }
            //发送添加请求给服务器
            if (role.equals("cashier")){
                dos.writeUTF("Admin_CashierAdmin_add:" + JSON.toJSONString(employee));
            }else{
                dos.writeUTF("Admin_BuyerAdmin_add:" + JSON.toJSONString(employee));
            }
            //获取服务端响应
            String s = dis.readUTF();
            System.out.println(s);
            //若添加成功跳出循环，否则重写输入
            if (s.equals("添加成功！")){
                break;
            }
        }
    }

    //修改员工
    public static void updateEmp(DataOutputStream dos, DataInputStream dis,String role) throws IOException {
        while (true){
            System.out.println("请输入要修改的员工编号：");
            String empNumber = sc.next();
            System.out.println("请输入要修改的信息（姓名-密码-性别-电话）");
            String cashierInfo = sc.next();
            String[] split = cashierInfo.split("-");
            //创建员工对象
            Employee employee = new Employee();
            try {
                employee.setNumber(empNumber);
                employee.setUsername(split[0]);
                employee.setPassword(split[1]);
                employee.setSex(split[2]);
                employee.setPhone(split[3]);
            } catch (Exception e) {
                //输入格式有误，跳出本次循环，重新输入
                System.out.println("输入格式有误！");
                continue;
            }
            //发送服务器请求
            if (role.equals("cashier")){
                dos.writeUTF("Admin_CashierAdmin_update:"+ JSON.toJSONString(employee));
            }else{
                dos.writeUTF("Admin_BuyerAdmin_update:"+ JSON.toJSONString(employee));
            }
            //返回修改结果
            String s = dis.readUTF();
            System.out.println(s);
            if (s.equals("修改失败！")){
                continue;
            }
            break;
        }
    }

    //删除员工
    public static void delEmp(DataOutputStream dos, DataInputStream dis,String empType) throws IOException {
        while (true){
            System.out.println("请输入要删除的员工编号：");
            String s = sc.next();
            //向服务器发送请求
            if (empType.equals("cashier")){
                dos.writeUTF("Admin_CashierAdmin_delete:"+s);
            }else{
                dos.writeUTF("Admin_BuyerAdmin_delete:"+s);
            }
            //接受服务器响应
            String s1 = dis.readUTF();
            System.out.println(s1);
            if (s.equals("删除失败！")){
                continue;
            }
            break;
        }
    }

    //查询所有考勤记录
    public static void queryAllClock(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器请求查询所有全部考勤
        dos.writeUTF("Admin_Clock_queryAll:");
        //读取服务器响应
        String clockString = dis.readUTF();
        //转换成集合
        List<ClockInfo> clockInfoList = JSON.parseArray(clockString, ClockInfo.class);
        //打印输出
        System.out.println("工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间\t\t\t\t\t上班情况\t\t下班情况");
        for (ClockInfo c:clockInfoList){
            System.out.println(DateFormatUtil.dateFormat(c.getClockDate())+"\t\t"+c.getEmpNumber()+"\t\t"
                    + c.getEmpName()+"\t\t"+DateFormatUtil.datetimeFormat(c.getClockInTime())+"\t\t"
                    +DateFormatUtil.datetimeFormat(c.getClockOffTime())+"\t\t" +c.getClockInStatus()+"\t\t"
                    +c.getClockOffStatus());
        }
    }

    //查询今日的考勤
    public static void queryTodayClock(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器请求查询今日考勤
        dos.writeUTF("Admin_Clock_queryToday:");
        //读取服务器响应
        String clockString = dis.readUTF();
        //转换成集合
        List<ClockInfo> clockInfoList = JSON.parseArray(clockString, ClockInfo.class);
        //打印输出
        System.out.println("工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间\t\t\t\t\t上班情况\t\t下班情况");
        for (ClockInfo c:clockInfoList){
            System.out.println(DateFormatUtil.dateFormat(c.getClockDate())+"\t\t"+c.getEmpNumber()+"\t\t"
                    + c.getEmpName()+"\t\t"+DateFormatUtil.datetimeFormat(c.getClockInTime())+"\t\t"
                    +DateFormatUtil.datetimeFormat(c.getClockOffTime())+"\t\t" +c.getClockInStatus()+"\t\t"
                    +c.getClockOffStatus());
        }
    }

    //查询今日销售额
    public static void queryTodaySell(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器查询请求
        dos.writeUTF("Admin_Sell_queryTotal:");
        //获取服务器响应
        String totalSell = dis.readUTF();
        System.out.println("总营业额为："+totalSell+"元");
    }

    //查询总销售额
    public static void queryTotalSell(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器查询请求
        dos.writeUTF("Admin_Sell_queryToday:");
        //获取服务器响应
        String todaySell = dis.readUTF();
        System.out.println("今日营业额为："+todaySell+"元");
    }


    //删除VIP
    public static void delVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true){
            System.out.println("请输入要删除的会员编号：");
            String vipNumber = sc.next();
            //发送服务器请求删除会员
            dos.writeUTF("Admin_Vip_del:"+ JSON.toJSONString(vipNumber));
            //接受服务器响应
            String s = dis.readUTF();
            //判断是否删除成功
            System.out.println(s);
            if (s.equals("删除失败！")) {
                continue;
            }
            break;
        }
    }

    //修改vip
    public static void updateVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请输入要修改的会员编号：");
            String vipNumber = sc.next();
            System.out.println("请输入要修改的信息（姓名-积分-电话）：");
            String message = sc.next();
            String[] split = message.split("-");
            //创建会员对象
            Vip vip = new Vip();
            try {
                vip.setNumber(vipNumber);
                vip.setName(split[0]);
                vip.setScore(Integer.parseInt(split[1]));
                vip.setPhone(split[2]);
            } catch (Exception e) {
                //输入格式有误，跳出本次循环，重新输入
                System.out.println("输入格式有误！");
                continue;
            }
            //发送服务器请求修改会员
            dos.writeUTF("Admin_Vip_update:" + JSON.toJSONString(vip));
            //接受服务器响应
            String s = dis.readUTF();
            //判断是否添加成功
            System.out.println(s);
            if (s.equals("修改失败！")) {
                continue;
            }
            break;
        }
    }

    //添加vip
    public static void addVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请填写信息（姓名-电话）");
            String message = sc.next();
            String[] split = message.split("-");
            //创建会员对象
            Vip vip = new Vip();
            try {
                vip.setName(split[0]);
                vip.setPhone(split[1]);
            } catch (Exception e) {
                //输入格式有误，跳出本次循环，重新输入
                System.out.println("输入格式有误！");
                continue;
            }
            //发送服务器请求添加会员
            dos.writeUTF("Admin_Vip_add:" + JSON.toJSONString(vip));
            //接受服务器响应
            String s = dis.readUTF();
            //判断是否添加成功
            System.out.println(s);
            if (s.equals("添加失败！")) {
                continue;
            }
            break;
        }
    }

    //查询vip
    public static void queryAllVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器请求查询所有会员
        dos.writeUTF("Admin_Vip_query:");
        //接受服务器响应
        String vipString = dis.readUTF();
        //转换成集合
        List<Vip> vipList = JSON.parseArray(vipString, Vip.class);
        //打印输出
        System.out.println("会员卡号\t\t\t\t姓名\t\t办卡日期\t\t\t电话\t\t\t积分");
        for (Vip v : vipList) {
            System.out.println(v.getNumber() + "\t\t" + v.getName() + "\t\t" + DateFormatUtil.dateFormat(v.getDate()) + "\t\t"
                    + v.getPhone() + "\t\t" + v.getScore());
        }
    }

    public static void queryWorkDate(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器请求获取工作日
        dos.writeUTF("WorkDate_query:");
        //接收响应
        String s = dis.readUTF();
        //转换成workDate格式
        List<WorkDate> workDateList = new ArrayList<>();
        workDateList = JSON.parseArray(s,WorkDate.class);
        //打印输出
        for (int i=0;i<workDateList.size();i++){
            System.out.print(workDateList.get(i).getId()+"."+ DateFormatUtil.dateFormat(workDateList.get(i).getDate())+"\t\t");
            //换行
            if (i%6==5){
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void addWorkDate(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请输入工作日（格式2020-8-13）：");
            String workDate = sc.next();
            //发送服务器请求添加工作日
            dos.writeUTF("WorkDate_add:" + workDate);
            //获取服务器响应
            String s1 = dis.readUTF();
            System.out.println(s1);
            if (s1.equals("添加成功！")) {
                break;
            } else {
                continue;
            }
        }
    }

    public static  void delWorkDate(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请输入要删除的工作日（格式2020-8-13）：");
            String workDate = sc.next();
            //发送服务器请求删除工作日
            dos.writeUTF("WorkDate_del:" + workDate);
            //获取服务器响应
            String s1 = dis.readUTF();
            System.out.println(s1);
            if (s1.equals("删除成功！")) {
                break;
            } else {
                continue;
            }
        }
    }


}
