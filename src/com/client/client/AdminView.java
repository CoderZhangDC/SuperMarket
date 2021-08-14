package com.client.client;

import com.alibaba.fastjson.JSON;
import com.client.client.utils.AdminUtil;
import com.client.client.utils.EmpUtil;
import com.server.pojo.Employee;
import com.server.utils.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:12
 * @Version 1.0
 */
public class AdminView {
    private Employee admin;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);

    public AdminView() {
    }

    public AdminView(Employee admin, Socket socket) {
        this.admin = admin;
        this.socket = socket;
    }

    //管理员主页面
    public void indexView() {
        System.out.println("---------------欢迎您" + admin.getUsername() + "(管理员)---------------");
        System.out.println("1.收银员账号管理\t\t\t2.采购员账号管理\t\t3.员工出勤管理");
        System.out.println("4.查询超市的营业额\t\t5.会员管理\t\t\t6.查询所有人员信息");
        System.out.println("7.上班打卡\t\t\t\t8.下班打卡\t\t\t9.设置工作日");
        System.out.println("10.退出");
        //获取用户输入
        String input = sc.next();
        switch (input) {
            case "1":
                CashierAdmin();
                break;
            case "2":
                BuyerAdmin();
                break;
            case "3":
                clockAdmin();
                break;
            case "4":
                SaleAdmin();
                break;
            case "5":
                vipAdmin();
                break;
            case "6":
                findUser();
                break;
            case "7":
                clockIn();
                break;
            case "8":
                clockOff();
                break;
            case "9":
                workDateAdmin();
                break;
            case "10":
                CloseUtil.close(socket);
                System.out.println("退出成功！");
                System.exit(1);
                break;
            default:
                System.out.println("输入有误！");
                indexView();
                break;
        }
    }

    //工作日管理
    private void workDateAdmin() {
        System.out.println("请选择操作：1.查看工作日\t\t2.添加工作日\t\t3.删除工作日\t\t4.退出");
        String input = sc.next();
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            switch (input) {
                //查看工作日
                case "1":
                    AdminUtil.queryWorkDate(dos, dis);
                    break;
                //添加工作日
                case "2":
                    AdminUtil.addWorkDate(dos, dis);
                    break;
                //删除工作日
                case "3":
                    AdminUtil.delWorkDate(dos, dis);
                    break;
                case "4":
                    indexView();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
            workDateAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //下班打卡
    private void clockOff() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpUtil.clockOff(dos, dis,admin);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //上班打卡
    private void clockIn() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpUtil.clockIn(dos, dis,admin);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //查询人员信息
    private void findUser() {
        System.out.println("---------------成员名单---------------");
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //请求服务查询所有成员
            dos.writeUTF("Admin_Emp_query:");
            //获得服务器响应
            String employeeString = dis.readUTF();
            //解析
            List<Employee> employeeList = JSON.parseArray(employeeString, Employee.class);
            //打印输出
            System.out.println("编号\t\t姓名\t\t性别\t电话\t\t\t职位");
            for (Employee e:employeeList){
                System.out.println(e.getNumber()+"\t\t"+e.getUsername()+"\t\t"+e.getSex()+"\t\t"+e.getPhone()+"\t\t"+e.getRoleString());
            }
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //会员管理
    private void vipAdmin() {
        System.out.println("请选择操作：1.查看所有会员\t2.添加会员\t3.修改会员\t4.删除会员\t5.退出");
        String input = sc.next();
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //判断操作类型
            switch (input) {
                //查看所有会员
                case "1":
                    AdminUtil.queryAllVip(dos, dis);
                    break;
                //添加会员
                case "2":
                    AdminUtil.addVip(dos, dis);
                    break;
                //修改会员
                case "3":
                    AdminUtil.updateVip(dos, dis);
                    break;
                //删除会员
                case "4":
                    AdminUtil.delVip(dos, dis);
                    break;
                //返回主页
                case "5":
                    indexView();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
            vipAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询营业额
    private void SaleAdmin() {
        System.out.println("请选择操作：1.查询今日营业额\t2.查询总营业额\t3.退出");
        String input = sc.next();
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //判断操作类型
            switch (input) {
                //查询今日营业额
                case "1":
                    AdminUtil.queryTotalSell(dos, dis);
                    break;
                //查询总营业额
                case "2":
                    AdminUtil.queryTodaySell(dos, dis);
                    break;
                //返回主页
                case "3":
                    indexView();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
            SaleAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //出勤管理
    private void clockAdmin() {
        System.out.println("请选择操作：1.查看今日考勤\t\t2.查看全部考勤\t\t3.退出");
        String input = sc.next();
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            switch (input) {
                //查看今日考勤
                case "1":
                    AdminUtil.queryTodayClock(dos, dis);
                    break;
                //查看全部考勤
                case "2":
                    AdminUtil.queryAllClock(dos, dis);
                    break;
                //返回主页
                case "3":
                    indexView();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
            clockAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //采购员管理
    private void BuyerAdmin() {
        System.out.println("1.查询采购员\t\t2.添加采购员\t\t3.修改采购员\t\t4.删除采购员\t\t5.退出");
        System.out.println("请选择操作：");
        String input = sc.next();
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //判断操作类型
            switch (input) {
                //查询采购员
                case "1":
                    AdminUtil.queryEmp(dos, dis, "buyer");
                    break;
                //添加采购员
                case "2":
                    AdminUtil.addEmp(dos, dis, "buyer");
                    break;
                //修改采购员
                case "3":
                    AdminUtil.updateEmp(dos, dis, "buyer");
                    break;
                //删除采购员
                case "4":
                    AdminUtil.delEmp(dos, dis, "buyer");
                    break;
                case "5":
                    indexView();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
            BuyerAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //收银员管理
    private void CashierAdmin() {
        System.out.println("1.查询收银员\t\t2.添加收银员\t\t3.修改收银员\t\t4.删除收银员\t\t5.退出");
        System.out.println("请选择操作：");
        String input = sc.next();
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //判断操作类型
            switch (input) {
                //查询收银员
                case "1":
                    AdminUtil.queryEmp(dos, dis, "cashier");
                    break;
                //添加收银员
                case "2":
                    AdminUtil.addEmp(dos, dis, "cashier");
                    break;
                //修改收银员
                case "3":
                    AdminUtil.updateEmp(dos, dis, "cashier");
                    break;
                //删除收银员
                case "4":
                    AdminUtil.delEmp(dos, dis, "cashier");
                    break;
                case "5":
                    indexView();
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
            CashierAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
