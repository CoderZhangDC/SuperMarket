package com.client.client;

import com.client.client.utils.BuyerUtil;
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
public class BuyerView {
    private Employee buyer;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);

    public BuyerView() {
    }

    public BuyerView(Employee buyer, Socket socket) {
        this.buyer = buyer;
        this.socket = socket;
    }

    public void indexView() {
        System.out.println("---------------欢迎您" + buyer.getUsername() + "(采购员)---------------");
        System.out.println("1.进行商品补货\t\t2.查询进货单\t\t3.上班打卡");
        System.out.println("4.下班打卡\t\t\t5.退出");
        //获取用户输入
        String input = sc.next();
        switch (input){
            case "1":
                addGoods();
                break;
            case "2":
                findGoods();
                break;
            case "3":
                clockIn();
                break;
            case "4":
                clockOff();
                break;
            case "5":
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
            EmpUtil.clockOff(dos, dis,buyer);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //上班打卡
    private void clockIn() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpUtil.clockIn(dos, dis,buyer);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询进货单
    private void findGoods() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            BuyerUtil.findGoods(dos, dis);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //进行商品补货
    private void addGoods() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            BuyerUtil.addGoods(dos, dis);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
