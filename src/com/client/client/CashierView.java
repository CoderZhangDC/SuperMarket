package com.client.client;

import com.client.client.utils.CashierUtil;
import com.client.client.utils.EmpUtil;
import com.server.pojo.Employee;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:12
 * @Version 1.0
 */
public class CashierView {
    private Employee cashier;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);

    public CashierView() {
    }

    public CashierView(Employee cashier, Socket socket) {
        this.cashier = cashier;
        this.socket = socket;
    }

    public void indexView() {
        System.out.println("---------------欢迎您" + cashier.getUsername() + "(收银员)---------------");
        System.out.println("1.收银结算\t\t\t2.会员积分查询\t\t3.开通会员");
        System.out.println("4.上班打卡\t\t\t5.下班打卡\t\t\t6.退出");
        //获取用户输入
        String input = sc.next();
        switch (input) {
            case "1":
                settle();
                break;
            case "2":
                findVipScore();
                break;
            case "3":
                openVip();
                break;
            case "4":
                clockIn();
                break;
            case "5":
                clockOff();
                break;
            case "6":
                System.out.println("退出成功！");
                System.exit(1);
                break;
            default:
                System.out.println("输入有误！");
                indexView();
                break;
        }
    }

    //下班打卡
    private void clockOff() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpUtil.clockOff(dos, dis, cashier);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //上班打卡
    private void clockIn() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpUtil.clockIn(dos, dis, cashier);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //开通会员
    private void openVip() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            CashierUtil.addVip(dos, dis);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询会员积分
    private void findVipScore() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            CashierUtil.findVipScore(dos, dis);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //收银结算
    private void settle() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            CashierUtil.Shell(dos, dis,cashier);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
