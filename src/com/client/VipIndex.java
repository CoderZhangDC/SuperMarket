package com.client;

import com.alibaba.fastjson.JSON;
import com.server.pojo.Goods;
import com.server.pojo.ScoreInfo;
import com.server.pojo.Vip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:13
 * @Version 1.0
 */
public class VipIndex {
    private Vip vip;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);

    public VipIndex() {
    }

    public VipIndex(Vip vip, Socket socket) {
        this.vip = vip;
        this.socket = socket;
    }

    public void indexView() {
        System.out.println("---------------欢迎您" + vip.getName() + "(会员)---------------");
        System.out.println("请选择操作：1.积分查询\t\t2.积分兑换\t\t3.退出");
        //获取用户输入
        String input = sc.next();
        switch (input){
            case "1":
                findVipInfo();
                break;
            case "2":
                scoreExchange();
                break;
            case "3":
                System.out.println("退出成功！");
                System.exit(1);
                break;
            default:
                System.out.println("输入有误！");
                indexView();
                break;
        }
    }

    private void scoreExchange() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //获取商品信息
            dos.writeUTF("Buyer_Query_allGoods:");
            String s1 = dis.readUTF();
            List<Goods> goodsList = JSON.parseArray(s1, Goods.class);
            //打印商品
            System.out.println("商品编号\t\t商品名称\t\t\t所需积分\t\t\t库存");
            for (Goods g:goodsList){
                System.out.println(g.getNumber()+"\t\t\t"+g.getName()+"\t\t\t"+g.getPrice().multiply(new BigDecimal("10"))+"\t\t\t"+g.getInventory());
            }
            System.out.println("你的剩余积分："+vip.getScore());
            while (true) {
                //获取用户要兑换的商品编号
                System.out.println("请输入你要兑换的商品编号：");
                String goodNumber = sc.next();
                System.out.println("请输入要兑换的数量：");
                String goodQuantity = sc.next();
                ScoreInfo scoreInfo = null;
                //判断是否输入格式有误
                try {

                    scoreInfo = new ScoreInfo();
                    scoreInfo.setScore(vip.getScore());
                    scoreInfo.setVipNumber(vip.getNumber());
                    scoreInfo.setGoodNumber(Integer.parseInt(goodNumber));
                    scoreInfo.setGoodQuantity(Integer.parseInt(goodQuantity));
                    if (scoreInfo.getGoodQuantity()<=0){
                        System.out.println("输入的数量必须大于0！");
                        continue;
                    }
                }catch (Exception e){
                    System.out.println("输入格式有误！");
                    continue;
                }
                //发送服务器请求兑换商品
                dos.writeUTF("Vip_Score_Exchange:"+JSON.toJSONString(scoreInfo));
                //接收服务器响应
                String s = dis.readUTF();
                //如果返回该商品不存在
                if (s.equals("兑换失败，该商品不存在！")){
                    System.out.println(s);
                    continue;
                }
                //如果返回积分不足
                if (s.equals("兑换失败，积分不足！")){
                    System.out.println(s);
                    continue;
                }
                //如果返回库存不足
                if (s.equals("兑换失败，库存不足！")){
                    System.out.println(s);
                    continue;
                }
                //兑换成功
                System.out.println("兑换成功！");
                //修改当前用户的session积分
                vip.setScore(vip.getScore()-Integer.parseInt(s));
                break;
            }
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void findVipInfo() {
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            //发送服务器请求获取vip信息
            dos.writeUTF("Cashier_Vip_query:"+vip.getNumber());
            //获取服务器响应
            String s = dis.readUTF();
            System.out.println("--------会员信息--------");
            //解析JSON
            Vip vip = JSON.parseObject(s, Vip.class);
            System.out.println("会员卡号："+vip.getNumber());
            System.out.println("会员姓名："+vip.getName());
            System.out.println("会员积分："+vip.getScore());
            indexView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
