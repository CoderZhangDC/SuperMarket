package com.client.client.view;

import com.alibaba.fastjson.JSON;
import com.client.client.utils.CheckUtil;
import com.server.pojo.Employee;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/12 19:05
 * @Version 1.0
 */
public class EmpView {
    private static Scanner sc = new Scanner(System.in);

    //员工上班打卡
    public static void clockIn(DataOutputStream dos, DataInputStream dis, Employee employee) throws IOException {
        //向服务器提交打卡请求
        dos.writeUTF("Emp_Clock_In:"+employee.getNumber());
        //接受服务器的打卡结果
        String s = dis.readUTF();
        //打印打卡结果
        System.out.println(s);
    }

    //员工下班打卡
    public static void clockOff(DataOutputStream dos, DataInputStream dis,Employee employee) throws IOException {
        //向服务器提交打卡请求
        dos.writeUTF("Emp_Clock_Off:"+employee.getNumber());
        //接受服务器的打卡结果
        String s = dis.readUTF();
        //打印打卡结果
        System.out.println(s);
    }

    //修改个人信息
    public static void updateEmpInfo(DataOutputStream dos, DataInputStream dis,Employee e) throws IOException {
        while (true) {
            //获取当前用户的信息
            dos.writeUTF("Admin_EmpNumber_query:"+e.getNumber());
            String s3 = dis.readUTF();
            Employee employee1 = JSON.parseObject(s3, Employee.class);
            System.out.println("姓名："+employee1.getUsername()+"\n性别："+employee1.getSex()+"\n手机号码："+employee1.getPhone());
            System.out.println("选择你的操作：1.姓名\t2.性别\t3.手机号码\t4.修改密码\t5.退出");
            String s = sc.next();
            switch (s){
                //修改姓名
                case "1":
                    System.out.println("请输入姓名：");
                    String name = sc.next();
                    //发送服务器请求
                    employee1.setUsername(name);
                    dos.writeUTF("Emp_Update_name:"+ JSON.toJSONString(employee1));
                    //获取服务器响应
                    String s1 = dis.readUTF();
                    System.out.println(s1);
                    break;
                //修改性别
                case "2":
                    while (true) {
                        System.out.println("请输入性别（男/女）：");
                        String sex = sc.next();
                        if (!CheckUtil.isSex(sex)){
                            System.out.println("输入格式有误！");
                            continue;
                        }
                        //发送服务器请求
                        employee1.setSex(sex);
                        dos.writeUTF("Emp_Update_sex:" + JSON.toJSONString(employee1));
                        //获取服务器响应
                        String s2 = dis.readUTF();
                        System.out.println(s2);
                        break;
                    }
                    break;
                //修改手机号
                case "3":
                    while (true) {
                        System.out.println("请输入手机号码：");
                        String phone = sc.next();
                        if (!CheckUtil.isValidPhoneNumber(phone)){
                            System.out.println("输入格式有误！");
                            continue;
                        }
                        //发送服务器请求
                        employee1.setPhone(phone);
                        dos.writeUTF("Emp_Update_phone:" + JSON.toJSONString(employee1));
                        //获取服务器响应
                        String s2 = dis.readUTF();
                        if (s2.equals("修改失败！")){
                            System.out.println("修改失败，该手机号已被使用！");
                        }else{
                            System.out.println(s2);
                        }
                        break;
                    }
                    break;
                //修改密码
                case "4":
                    while (true) {
                        System.out.println("请输入旧密码（输入0退出）：");
                        String oldPassword = sc.next();
                        //退出
                        if (oldPassword.equals("0")){
                            updateEmpInfo(dos,dis,e);
                            return;
                        }
                        //判断旧密码是否正确
                        if (oldPassword.equals(employee1.getPassword())){
                            break;
                        }
                        System.out.println("密码输入有误！");
                    }
                    //输入新密码
                    while (true) {
                        System.out.println("请输入新密码：");
                        String newPassword = sc.next();
                        //判断密码格式是否有误
                        if (!CheckUtil.isPassword(newPassword)) {
                            continue;
                        }
                        //发送服务器请求
                        employee1.setPassword(newPassword);
                        dos.writeUTF("Emp_Update_password:" + JSON.toJSONString(employee1));
                        String s2 = dis.readUTF();
                        System.out.println(s2);
                        break;
                    }
                    break;
                //退出
                case "5":
                    return;
                default:
                    System.out.println("输入有误！");
                    continue;
            }
        }
    }
}
