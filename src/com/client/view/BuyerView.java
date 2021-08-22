package com.client.view;

import com.alibaba.fastjson.JSON;
import com.server.pojo.Goods;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/12 23:12
 * @Version 1.0
 */
public class BuyerView {

    private static Scanner sc = new Scanner(System.in);

    //添加商品
    public static void addGoods(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true){
            System.out.println("请输入要补货的商品编号：");
            String goodNumber = sc.next();
            //查询服务器数据库是否存在该商品
            try {
                dos.writeUTF("Buyer_Goods_query:"+Integer.parseInt(goodNumber));
            }catch (Exception e){
                System.out.println("格式有误！");
                continue;
            }
            //获取服务器响应
            String s = dis.readUTF();
            //判断商品是否存在
            if (s.equals("该商品不存在！")){
                while (true) {
                    //不存在进行商品添加
                    System.out.println("请输入商品名称：");
                    String goodName = sc.next();
                    System.out.println("请输入商品价格：");
                    String goodPrice = sc.next();
                    System.out.println("请输入进货数量：");
                    String goodInventory = sc.next();
                    Goods goods = new Goods();
                    try {
                        goods.setName(goodName);
                        goods.setInventory(Integer.parseInt(goodInventory));
                        goods.setPrice(new BigDecimal(goodPrice));
                        goods.setVipPrice(new BigDecimal(goodPrice).multiply(new BigDecimal(0.8)));
                    }catch (Exception e){
                        System.out.println("输入格式有误！");
                        continue;
                    }
                    //发送服务器请求，添加商品
                    dos.writeUTF("Buyer_Goods_add:"+ JSON.toJSONString(goods));
                    //获取服务器响应
                    String s1 = dis.readUTF();
                    System.out.println(s1);
                    break;
                }
            }else{
                //商品存在，则进行库存修改
                System.out.println("请输入补货数量：");
                String number = sc.next();
                Goods goods = JSON.parseObject(s, Goods.class);
                //数量添加
                goods.setInventory(goods.getInventory()+Integer.parseInt(number));
                //发送服务器请求修改库存
                dos.writeUTF("Buyer_Goods_addInventory:"+JSON.toJSONString(goods));
                //响应服务器结果
                String s1 = dis.readUTF();
                System.out.println(s1);
            }
            break;
        }
    }

    //查询商品
    public static void findGoods(DataOutputStream dos, DataInputStream dis) throws IOException {
        //发送服务器请求，获取货单
        dos.writeUTF("Buyer_Goods_Inventory:");
        //接受服务器响应结果
        String s = dis.readUTF();
        //解析JSON
        List<Goods> goodsList = JSON.parseArray(s, Goods.class);
        //打印结果
        System.out.println("商品编号\t\t商品名称\t\t商品库存");
        for (Goods g:goodsList){
            System.out.println(g.getNumber()+"\t\t\t"+g.getName()+"\t\t"+g.getInventory());
        }
    }

}
