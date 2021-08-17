package com.client.client;

import com.alibaba.fastjson.JSON;
import com.server.pojo.Employee;
import com.server.pojo.Vip;
import com.server.utils.CloseUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:12
 * @Version 1.0
 */
public class LoginView {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //获取连接
        try (Socket socket = new Socket("127.0.0.1", 6666);) {
            System.out.println("--------------欢迎来到超市管理系统--------------");
            while (true) {
                System.out.println("请输入你的身份:1.员工 2.会员");
                String input = sc.next();
                switch (input) {
                    case "1":
                        empLogin(socket);
                        break;
                    case "2":
                        vipLogin(socket);
                        break;
                    default:
                        System.out.println("输入有误！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //员工登录
    public static void empLogin(Socket socket) throws IOException {
        //获取用户输入
        System.out.println("请输入你的账号和密码（输入0退出）:");
        String number = sc.next();
        if (number.equals("0")){
            System.exit(1);
        }
        String password = sc.next();
        Employee emp = new Employee();
        emp.setNumber(number);
        emp.setPassword(password);
        //获取输出流
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        try (DataOutputStream dos = new DataOutputStream(outputStream);
            DataInputStream dis = new DataInputStream(inputStream);) {
            //发送用户输入的信息到服务器
            dos.writeUTF("Emp_login:" + JSON.toJSONString(emp));
            //获取服务器响应
            String s = dis.readUTF();
            //判断服务器的响应,若为空字符串，则账号或密码错误
            if ("".equals(s)) {
                System.out.println("账号或密码错误！");
                empLogin(socket);
            }
            //登录成功，解析JSON
            Employee employee = JSON.parseObject(s, Employee.class);
            //判断员工角色
            int role = employee.getRole();
            //根据不同的角色，跳转到不同的页面
            switch (role) {
                case 1:
                    AdminIndex av = new AdminIndex(employee,socket);
                    av.indexView();
                    break;
                case 2:
                    CashierIndex cv = new CashierIndex(employee,socket);
                    cv.indexView();
                    break;
                case 3:
                    BuyerIndex bv = new BuyerIndex(employee,socket);
                    bv.indexView();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(socket);
        }
    }

    //会员登录
    public static void vipLogin(Socket socket) throws IOException {
        //获取用户输入的信息
        System.out.println("请输入会员号和手机号(输入0退出):");
        String vipNumber = sc.next();
        String phone = sc.next();
        Vip vip = new Vip();
        vip.setNumber(vipNumber);
        vip.setPhone(phone);
        //获取输出流
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        try (DataOutputStream dos = new DataOutputStream(outputStream);
             DataInputStream dis = new DataInputStream(inputStream);) {
            //发送用户输入的信息到服务器
            dos.writeUTF("Vip_login:" + JSON.toJSONString(vip));
            //获取服务器响应
            String s = dis.readUTF();
            //判断服务器的响应,若为空字符串，则账号或密码错误
            if ("".equals(s)) {
                System.out.println("会员号或手机号错误！");
                vipLogin(socket);
            }
            //登录成功，解析JSON
            Vip vip1 = JSON.parseObject(s, Vip.class);
            VipIndex vipView = new VipIndex(vip1,socket);
            vipView.indexView();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(socket);
        }
    }
}
