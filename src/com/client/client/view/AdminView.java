package com.client.client.view;

import com.alibaba.fastjson.JSON;
import com.client.client.utils.CheckUtil;
import com.client.client.utils.DateFormatUtil;
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
public class AdminView {
    private static Scanner sc = new Scanner(System.in);

    //查询员工
    public static void queryEmp(DataOutputStream dos, DataInputStream dis,String role) throws IOException {
        if (role.equals("cashier")){
            System.out.println("-----------------------------收银员名单-----------------------------");
            dos.writeUTF("Admin_CashierAdmin_query:");
        }else{
            System.out.println("-----------------------------采购员名单-----------------------------");
            dos.writeUTF("Admin_BuyerAdmin_query:");
        }
        //获取员工信息
        String empString = dis.readUTF();
        //转换成集合
        List<Employee> employeeList = JSON.parseArray(empString, Employee.class);
        //打印输出
        System.out.println("编号\t\t姓名\t\t性别\t电话\t\t\t注册时间\t\t\t\t\t剩余补卡次数");
        for (Employee e:employeeList){
            System.out.println(e.getNumber()+"\t\t"+e.getUsername()+"\t\t"+e.getSex()+"\t\t"+e.getPhone()+"\t\t"+ DateFormatUtil.datetimeFormat(e.getRegisterTime())+"\t\t"+e.getReClockCount());
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
                //判断编号和手机号和性别和密码输入是否有误
                if (!CheckUtil.isNumber(split[0])||!CheckUtil.isPassword(split[2])||!CheckUtil.isSex(split[3])||!CheckUtil.isValidPhoneNumber(split[4])){
                        continue;
                }
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
            System.out.println("请输入要修改的员工编号/手机号：");
            String input = sc.next();
            //向服务器发送请求，获取员工信息
            dos.writeUTF("Admin_EmpNumber_query:"+input);
            //获取服务器响应
            String s1 = dis.readUTF();
            //判断是否存在
            if (s1.equals("该员工不存在！")){
                System.out.println(s1);
                continue;
            }
            //如果存在
            Employee employee = JSON.parseObject(s1, Employee.class);
            //打印员工信息
            System.out.println("编号\t\t姓名\t\t性别\t电话\t\t\t注册时间");
            System.out.println(employee.getNumber()+"\t\t"+employee.getUsername()+"\t\t"+employee.getSex()+"\t\t"+employee.getPhone()+"\t\t"+DateFormatUtil.datetimeFormat(employee.getRegisterTime()));
            while (true){
                System.out.println("请输入要修改的信息（1.姓名 2.密码 3.性别 4.电话 5.退出）");
                String cashierInfo = sc.next();
                //根据不同的条件修改不同的字段
                if (cashierInfo.equals("1")){
                    System.out.println("请输入姓名：");
                    String name = sc.next();
                    employee.setUsername(name);
                }else if (cashierInfo.equals("2")){
                    System.out.println("请输入密码：");
                    String password = sc.next();
                    //判断密码是否输入有误
                    if (!CheckUtil.isPassword(password)){
                        continue;
                    }
                    employee.setPassword(password);
                }else if (cashierInfo.equals("3")){
                    System.out.println("请输入性别：");
                    String sex = sc.next();
                    //判断性别是否输入有误
                    if (!CheckUtil.isSex(sex)){
                        continue;
                    }
                    employee.setSex(sex);
                }else if (cashierInfo.equals("4")){
                    System.out.println("请输入电话号码：");
                    String phone = sc.next();
                    //判断手机号是否输入有误
                    if (!CheckUtil.isValidPhoneNumber(phone)){
                        continue;
                    }
                    employee.setPhone(phone);
                }else if (cashierInfo.equals("5")){
                    //退出
                    break;
                }else {
                    System.out.println("输入有误！");
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
        if (clockInfoList.size()!=0) {
            //打印输出
            System.out.println("工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间\t\t\t\t\t上班情况\t\t下班情况");
            for (ClockInfo c : clockInfoList) {
                System.out.println(DateFormatUtil.dateFormat(c.getClockDate()) + "\t\t" + c.getEmpNumber() + "\t\t"
                        + c.getEmpName() + "\t\t" + DateFormatUtil.datetimeFormat(c.getClockInTime()) + "\t\t"
                        + DateFormatUtil.datetimeFormat(c.getClockOffTime()) + "\t\t" + c.getClockInStatus() + "\t\t"
                        + c.getClockOffStatus());
            }
        }else{
            System.out.println("没有记录！");
        }
    }


    //查询销售额
    public static void querySell(DataOutputStream dos, DataInputStream dis,String type) throws IOException {
        switch (type){
            case "today":
                //发送服务器查询请求
                dos.writeUTF("Admin_Sell_queryToday:");
                //获取服务器响应
                System.out.println("今日营业额为："+dis.readUTF()+"元");
                break;
            case "month":
                //发送服务器查询请求
                dos.writeUTF("Admin_Sell_queryMonth:");
                //获取服务器响应
                System.out.println("本月营业额为："+dis.readUTF()+"元");
                break;
            case "season":
                //发送服务器查询请求
                dos.writeUTF("Admin_Sell_querySeason:");
                //获取服务器响应
                System.out.println("本季度营业额为："+dis.readUTF()+"元");
                break;
            case "total":
                //发送服务器查询请求
                dos.writeUTF("Admin_Sell_queryTotal:");
                //获取服务器响应
                System.out.println("总营业额为："+dis.readUTF()+"元");
                break;
        }
    }

    //查询指定时间段的营业额
    public static void queryRangeSell(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请输入第一个时间（格式2021-08-03）：");
            String time1 = sc.next();
            System.out.println("请输入第二个时间（格式2021-08-03）：");
            String time2 = sc.next();
            //判断是否输入有误
            if (!CheckUtil.isDate(time1)||!CheckUtil.isDate(time2)){
                System.out.println("输入有误！");
                continue;
            }
            List<String> time = new ArrayList<>();
            time.add(time1);
            time.add(time2);
            //访问服务器查询
            dos.writeUTF("Admin_Sell_queryRange:"+JSON.toJSONString(time));
            //获取服务器响应
            String s = dis.readUTF();
            System.out.println(time1+"~"+time2+"的营业额为"+s+"元");
            break;
        }
    }

    //删除VIP
    public static void delVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true){
            System.out.println("请输入要注销的会员编号(输入0退出)：");
            String vipNumber = sc.next();
            if (vipNumber.equals("0")){
                break;
            }
            //发送服务器请求删除会员
            dos.writeUTF("Admin_Vip_del:"+ vipNumber);
            //接受服务器响应
            String s = dis.readUTF();
            //判断是否删除成功
            System.out.println(s);
            if (s.equals("注销失败！")) {
                continue;
            }
            break;
        }
    }

    //修改vip
    public static void updateVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        Vip vip1 = null;
        while (true) {
            System.out.println("请输入要修改的会员编号/手机号（输入0退出）：");
            String vipNumber = sc.next();
            //退出
            if (vipNumber.equals("0")) {
                return;
            }
            dos.writeUTF("Admin_Vip_query_number:" + vipNumber);
            String s1 = dis.readUTF();
            vip1 = JSON.parseObject(s1, Vip.class);
            //如果查询结果为空
            if (vip1 != null) {
                break;
            }
            System.out.println("该会员号不存在！");
        }
        while (true) {
            System.out.println("姓名：" + vip1.getName() + "\n性别：" + vip1.getScore() + "\n手机号码：" + vip1.getPhone());
            System.out.println("请输入要修改的信息（姓名-积分-电话）（输入0退出）：");
            String message = sc.next();
            if (message.equals("0")){
                break;
            }
            String[] split = message.split("-");
            //创建会员对象
            Vip vip = new Vip();
            try {
                //判断手机号输入是否有误
                if (!CheckUtil.isValidPhoneNumber(split[2])) {
                    continue;
                }
                vip.setNumber(vip1.getNumber());
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
            if (s.equals("修改失败！")) {
                System.out.println("修改失败，该手机号已被注册过！");
                continue;
            }
            System.out.println(s);
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
                //判断手机号输入是否有误
                if (!CheckUtil.isValidPhoneNumber(split[1])){
                    continue;
                }
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
            if (s.equals("添加失败！")) {
                System.out.println("添加失败，该手机号已被注册过！");
                continue;
            }
            System.out.println(s);
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

    //查询工作日
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

    //添加工作日
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
            }
        }
    }

    //删除工作日
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
            }
        }
    }

    //查询指定日期的考勤情况(全部/异常)
    public static void queryClockByDate(DataOutputStream dos, DataInputStream dis,String type) throws IOException {
        while (true) {
            System.out.println("请输入日期（格式2021-08-16）(输入0退出)：");
            String date = sc.next();
            if (date.equals("0")){
                //退出
                break;
            }
            //判断日期格式是否有误
            if (!CheckUtil.isDate(date)) {
                continue;
            }
            //根据类型发送服务器请求获取考勤数据
            if (type.equals("all")){
                dos.writeUTF("Admin_Clock_queryDate:" + date);
            }else{
                dos.writeUTF("Admin_ErrorClock_queryDate:"+date);
            }
            //获取服务器响应
            String s = dis.readUTF();
            List<ClockInfo> clockInfoList = JSON.parseArray(s, ClockInfo.class);
            //打印输出
            if (clockInfoList.size()!=0) {
                System.out.println("工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间\t\t\t\t\t上班情况\t\t下班情况");
                for (ClockInfo c : clockInfoList) {
                    System.out.println(DateFormatUtil.dateFormat(c.getClockDate()) + "\t\t" + c.getEmpNumber() + "\t\t"
                            + c.getEmpName() + "\t\t" + DateFormatUtil.datetimeFormat(c.getClockInTime()) + "\t\t"
                            + DateFormatUtil.datetimeFormat(c.getClockOffTime()) + "\t\t" + c.getClockInStatus() + "\t\t"
                            + c.getClockOffStatus());
                }
            }else{
                System.out.println("没有记录！");
            }
            break;
        }
    }

    //员工上下班补卡
    public static void adminClock(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请输入员工编号：");
            String empNumber = sc.next();
            //调用数据库查询员工考勤记录
            dos.writeUTF("Admin_Clock_queryEmp:" + empNumber);
            //获取服务器响应
            String s = dis.readUTF();
            if (s.equals("找不到该员工！")) {
                System.out.println(s);
                continue;
            }
            List<ClockInfo> clockInfoList = JSON.parseArray(s, ClockInfo.class);
            //打印输出
            System.out.println("工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间\t\t\t\t\t上班情况\t\t下班情况");
            for (int i=0;i<clockInfoList.size();i++) {
                System.out.println(DateFormatUtil.dateFormat(clockInfoList.get(i).getClockDate()) + "\t\t" + clockInfoList.get(i).getEmpNumber() + "\t\t"
                        + clockInfoList.get(i).getEmpName() + "\t\t" + DateFormatUtil.datetimeFormat(clockInfoList.get(i).getClockInTime()) + "\t\t"
                        + DateFormatUtil.datetimeFormat(clockInfoList.get(i).getClockOffTime()) + "\t\t" + clockInfoList.get(i).getClockInStatus() + "\t\t"
                        + clockInfoList.get(i).getClockOffStatus());
            }
            //补卡
            while (true) {
                System.out.println("请选择你的操作：1.补上班卡 2.补下班卡 3.返回");
                String input = sc.next();
                switch (input) {
                    case "1":
                        //判断是否拥有卡需要补
                        boolean flag = false;
                        //打印没有打上班卡的选项
                        System.out.println("编号\t工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间");
                        for (int i = 0; i < clockInfoList.size(); i++) {
                            if (clockInfoList.get(i).getClockInTime()==null) {
                                System.out.println(i + "\t\t" + DateFormatUtil.dateFormat(clockInfoList.get(i).getClockDate()) + "\t\t" + clockInfoList.get(i).getEmpNumber() + "\t\t"
                                        + clockInfoList.get(i).getEmpName() + "\t\t" + DateFormatUtil.datetimeFormat(clockInfoList.get(i).getClockInTime()) + "\t\t"
                                        + DateFormatUtil.datetimeFormat(clockInfoList.get(i).getClockOffTime()));
                                flag=true;
                            }
                        }
                        //如果有卡要补
                        if (flag) {
                            //选择要补卡的选项
                            while (true) {
                                System.out.println("请输入要补的上班卡编号：");
                                String number = sc.next();
                                try {
                                    //判断是否为空，为空才允许补卡，切输入的是否正确
                                    if (clockInfoList.get(Integer.parseInt(number)).getClockInTime() == null) {
                                        //向服务器发送补卡请求
                                        dos.writeUTF("Admin_ReClockIn:" + JSON.toJSONString(clockInfoList.get(Integer.parseInt(number))));
                                        //接受服务器响应
                                        String s1 = dis.readUTF();
                                        System.out.println(s1);
                                    }else{
                                        System.out.println("你输入的编号有误！");
                                    }
                                } catch (Exception e) {
                                    System.out.println("输入有误！");
                                    continue;
                                }
                                break;
                            }
                        }else{
                            System.out.println("你没有需要补的卡！");
                        }
                        break;
                    case "2":
                        //判断是否拥有卡需要补
                        boolean flag2 = false;
                        //打印没有打下班卡的选项
                        System.out.println("编号\t工作日期\t\t\t员工编号\t\t员工姓名\t\t上班时间\t\t\t\t\t下班时间");
                        for (int i = 0; i < clockInfoList.size(); i++) {
                            if (clockInfoList.get(i).getClockOffTime()==null) {
                                System.out.println(i + "\t\t" + DateFormatUtil.dateFormat(clockInfoList.get(i).getClockDate()) + "\t\t" + clockInfoList.get(i).getEmpNumber() + "\t\t"
                                        + clockInfoList.get(i).getEmpName() + "\t\t" + DateFormatUtil.datetimeFormat(clockInfoList.get(i).getClockInTime()) + "\t\t"
                                        + DateFormatUtil.datetimeFormat(clockInfoList.get(i).getClockOffTime()));
                                flag2=true;
                            }
                        }
                        if (flag2) {
                            //选择要补卡的选项
                            while (true) {
                                System.out.println("请输入要补的下班卡编号：");
                                String number = sc.next();
                                try {
                                    //判断是否为空，为空才允许补卡，切输入的是否正确
                                    if (clockInfoList.get(Integer.parseInt(number)).getClockOffTime() == null) {
                                        //向服务器发送补卡请求
                                        dos.writeUTF("Admin_ReClockOff:" + JSON.toJSONString(clockInfoList.get(Integer.parseInt(number))));
                                        //接受服务器响应
                                        String s1 = dis.readUTF();
                                        System.out.println(s1);
                                    }else{
                                        System.out.println("你输入的编号有误！");
                                    }
                                } catch (Exception e) {
                                    System.out.println("输入有误！");
                                    continue;
                                }
                                break;
                            }
                        }else{
                            System.out.println("你没有需要补的卡！");
                        }
                        break;
                    case "3":
                        break;
                    default:
                        System.out.println("输入有误！");
                        continue;
                }
                break;
            }
            break;
        }
    }

    //查询员工薪资
    public static void findEmpSalary(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true){
            System.out.println("请输入你的操作：1.查询top10员工薪水 2.查询薪水最低的员工top5 3.查询指定区间的员工薪水 4.返回");
            String input2 = sc.next();
            switch (input2){
                //查询top10员工薪水
                case "1":
                    dos.writeUTF("Admin_EmpSalary_query_top10:");
                    break;
                //根据薪资区间查询
                case "3":
                    while (true) {
                        System.out.println("请输入工资区间（格式3000-4555）：");
                        String salary = sc.next();
                        String[] split = salary.split("-");
                        List<Integer> salaryRange = new ArrayList<>();
                        //判断格式是否输入有误
                        try {
                            salaryRange.add(Integer.parseInt(split[0]));
                            salaryRange.add(Integer.parseInt(split[1]));
                        }catch (Exception e){
                            System.out.println("输入格式有误！");
                            continue;
                        }
                        //发送服务器请求
                        dos.writeUTF("Admin_EmpSalary_query_range:"+ JSON.toJSONString(salaryRange));
                        break;
                    }
                    break;
                //根据最低薪水top5查询
                case "2":
                    dos.writeUTF("Admin_EmpSalary_query_top5:");
                    break;
                //返回
                case "4":
                    return;
                default:
                    System.out.println("输入有误！");
                    continue;
            }
            String s1 = dis.readUTF();
            //获取响应
            List<Employee> employeeList1 = JSON.parseArray(s1, Employee.class);
            if (employeeList1.size()==0){
                System.out.println("没有查询到符合薪水条件的员工！");
                continue;
            }
            //打印结果
            System.out.println("编号\t\t姓名\t\t性别\t薪资");
            for (Employee e:employeeList1){
                System.out.println(e.getNumber()+"\t\t"+e.getUsername()+"\t\t"+e.getSex()+"\t\t"+e.getSalary());
            }
        }
    }

    //查询离职员工
    public static void findEmpByResign(DataOutputStream dos, DataInputStream dis) throws IOException {
        dos.writeUTF("Admin_Emp_query_resign:");
        String s = dis.readUTF();
        List<Employee> employees = JSON.parseArray(s, Employee.class);
        if (employees.size()==0){
            System.out.println("没有查询到符合条件的员工！");
        }
        //打印结果
        System.out.println("编号\t\t姓名\t\t性别\t电话\t\t\t注册时间\t\t\t\t\t剩余补卡次数");
        for (Employee e:employees){
            System.out.println(e.getNumber()+"\t\t"+e.getUsername()+"\t\t"+e.getSex()+"\t\t"+e.getPhone()+"\t\t"+ DateFormatUtil.datetimeFormat(e.getRegisterTime())+"\t\t"+e.getReClockCount());
        }
    }

    public static void findEmpByCondition(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true) {
            System.out.println("请选择你的查询条件：1.根据姓名\t2.根据性别\t3.根据注册时间\t4.返回");
            String s = sc.next();
            switch (s) {
                //根据姓名字段
                case "1":
                    System.out.println("请输入姓名字段：");
                    String name = sc.next();
                    dos.writeUTF("Admin_Emp_query_name:"+name);
                    break;
                //根据性别
                case "2":
                    String sex = "";
                    while (true) {
                        System.out.println("请输入性别（男/女）：");
                        sex = sc.next();
                        //如果输入格式有误
                        if (!CheckUtil.isSex(sex)) {
                            System.out.println("性别格式输入有误！");
                            continue;
                        }
                        break;
                    }
                    dos.writeUTF("Admin_Emp_query_sex:"+sex);
                    break;
                //根据注册年份
                case "3":
                    int registerYear = 0;
                    while (true) {
                        System.out.println("请输入注册年份：");
                        //如果输入格式有误
                        try {
                            registerYear = Integer.parseInt(sc.next());
                        } catch (Exception e) {
                            System.out.println("日期格式输入有误！");
                            continue;
                        }
                        break;
                    }
                    dos.writeUTF("Admin_Emp_query_registerYear:" + registerYear);
                    break;
                case "4":
                    //返回
                    return;
                default:
                    System.out.println("输入有误！");
                    continue;
            }
            //获取响应
            String s1 = dis.readUTF();
            List<Employee> employees = JSON.parseArray(s1, Employee.class);
            if (employees.size()==0){
                System.out.println("没有查询到符合条件的员工！");
                continue;
            }
            //打印结果
            System.out.println("编号\t\t姓名\t\t性别\t电话\t\t\t注册时间\t\t\t\t\t剩余补卡次数");
            for (Employee e:employees){
                System.out.println(e.getNumber()+"\t\t"+e.getUsername()+"\t\t"+e.getSex()+"\t\t"+e.getPhone()+"\t\t"+ DateFormatUtil.datetimeFormat(e.getRegisterTime())+"\t\t"+e.getReClockCount());
            }
            break;
        }
    }

    public static void findEmpByNumber(DataOutputStream dos, DataInputStream dis, Scanner sc) throws IOException {
        while (true) {
            System.out.println("请输入你要查询的员工编号/手机号码：");
            String empNumber = sc.next();
            dos.writeUTF("Admin_EmpNumber_query:" + empNumber);
            String s = dis.readUTF();
            //如果该员工不存在
            if (s.equals("该员工不存在！")) {
                System.out.println(s);
                continue;
            }
            //存在则打印信息
            Employee e = JSON.parseObject(s, Employee.class);
            //打印输出
            System.out.println("编号\t\t姓名\t\t性别\t职位\t电话\t\t\t注册时间\t\t\t\t\t剩余补卡次数");
            System.out.println(e.getNumber() + "\t\t" + e.getUsername() + "\t\t" + e.getSex() + "\t" + e.getRoleString() + "\t\t" + e.getPhone() + "\t\t" + DateFormatUtil.datetimeFormat(e.getRegisterTime()) + "\t\t" + e.getReClockCount());
            break;
        }
    }

    public static void findEmp(DataOutputStream dos, DataInputStream dis) throws IOException {
        //请求服务查询所有成员
        dos.writeUTF("Admin_Emp_query:");
        //获得服务器响应
        String employeeString = dis.readUTF();
        //解析
        List<Employee> employeeList = JSON.parseArray(employeeString, Employee.class);
        //打印输出
        System.out.println("---------------成员名单---------------");
        System.out.println("编号\t\t姓名\t\t性别\t职位\t电话\t\t\t注册时间\t\t\t\t\t剩余补卡次数");
        for (Employee e : employeeList) {
            System.out.println(e.getNumber() + "\t\t" + e.getUsername() + "\t\t" + e.getSex() + "\t" + e.getRoleString() + "\t\t" + e.getPhone() + "\t\t" + DateFormatUtil.datetimeFormat(e.getRegisterTime()) + "\t\t" + e.getReClockCount());
        }
    }

}
