package com.client;

import com.alibaba.fastjson.JSON;
import com.client.view.BuyerView;
import com.client.view.EmpView;
import com.server.pojo.Employee;
import com.server.pojo.Goods;

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
public class BuyerIndex {
    private Employee buyer;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);

    public BuyerIndex() {
    }

    public BuyerIndex(Employee buyer, Socket socket) {
        this.buyer = buyer;
        this.socket = socket;
    }

    public void indexView() {
        System.out.println("---------------欢迎您" + buyer.getUsername() + "(采购员)---------------");
        System.out.println("1.进行商品补货\t2.查询进货单\t\t\t3.商品下架");
        System.out.println("4.上班打卡\t\t5.下班打卡\t\t\t6.修改个人信息\n7.退出");
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
                delGoods();
                break;
            case "4":
                clockIn();
                break;
            case "5":
                clockOff();
                break;
            case "6":
                updateInfo();
                break;
            case "7":
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
            EmpView.clockOff(dos, dis,buyer);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //上班打卡
    private void clockIn() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpView.clockIn(dos, dis,buyer);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询进货单
    private void findGoods() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            BuyerView.findGoods(dos, dis);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //商品下架
    private void delGoods() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //获取已经上架的所有商品
            dos.writeUTF("Buyer_Query_allGoods:");
            String s = dis.readUTF();
            List<Goods> goods = JSON.parseArray(s, Goods.class);
            //打印输出
            System.out.println("商品编号\t\t商品名称\t\t商品库存");
            for (Goods g:goods){
                System.out.println(g.getNumber()+"\t\t\t"+g.getName()+"\t\t"+g.getInventory());
            }
            while (true) {
                System.out.println("请选择你要下架的商品编号（输入0退出）：");
                String input = sc.next();
                //退出
                if (input.equals("0")){
                    break;
                }
                //发送服务器下架请求
                dos.writeUTF("Buyer_del_goods:"+input);
                String s1 = dis.readUTF();
                if (s1.equals("下架成功！")){
                    System.out.println(s1);
                    break;
                }else{
                    System.out.println("下架失败，该商品不存在！");
                }
            }
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //进行商品补货
    private void addGoods() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            BuyerView.addGoods(dos, dis);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //修改个人信息
    private void updateInfo() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            EmpView.updateEmpInfo(dos, dis,buyer);
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
