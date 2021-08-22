package com.client.view;

import com.alibaba.fastjson.JSON;
import com.client.utils.CheckUtil;
import com.client.utils.DateFormatUtil;
import com.server.pojo.Employee;
import com.server.pojo.SellInfo;
import com.server.pojo.Vip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @Author:zdc
 * @Date 2021/8/12 21:27
 * @Version 1.0
 */
public class CashierView {
    private static Scanner sc = new Scanner(System.in);

    public static void Shell(DataOutputStream dos, DataInputStream dis, Employee cashier) throws IOException {
        List<SellInfo> sellInfos = new ArrayList<>();
        Vip vip = null;
        System.out.println("是否使用会员卡（Y/N）：");
        String useVip = sc.next();
        //若使用会员卡支付
        if (useVip.equalsIgnoreCase("Y")) {
            //获取会员卡号
            while (true) {
                System.out.println("请输入会员卡号/绑定手机号（输入0退出）：");
                String vipNumber = sc.next();
                if (vipNumber.equals("0")){
                    break;
                }
                //请求服务器查看会员卡是否存在
                dos.writeUTF("Cashier_Vip_query:"+ vipNumber);
                //接受服务器响应
                String s = dis.readUTF();
                //如果会员卡不存在，则跳出本次循环
                if (s.equals("会员卡不存在！")) {
                    System.out.println("会员卡不存在！请重新输入！");
                    continue;
                }
                //如果存在，则创建vip对象
                vip = JSON.parseObject(s, Vip.class);
                break;
            }
        }
        //进行购物
        while (true){
            System.out.println("请输入商品编号（输入-1结束）：");
            String goodNumber = sc.next();
            //输入-1退出
            if (goodNumber.equals("-1")){
                break;
            }
            System.out.println("请输入购买数量：");
            String buyNumber = sc.next();
            SellInfo si;
            try {
                //创建销售记录对象
                si  = new SellInfo();
                si.setGoodNumber(Integer.parseInt(goodNumber));
                si.setQuantity(Integer.parseInt(buyNumber));
                //判断输入的数组是否小于等于0
                if (si.getQuantity()<=0){
                    System.out.println("输入的数量必须大于0！");
                    continue;
                }
                si.setEmpNumber(cashier.getNumber());
                //如果使用了会员卡
                if (vip!=null){
                    si.setVipNumber(vip.getNumber());
                }
            }catch (Exception e){
                System.out.println("输入格式有误！请重新输入！");
                continue;
            }
            //如果输入没错误，提交信息到服务器
            dos.writeUTF("Cashier_Shell:"+JSON.toJSONString(si));
            //获取服务器响应
            String s = dis.readUTF();
            //如果返回，该商品不存在
            if (s.equals("该商品不存在！")){
                System.out.println("该商品不存在！请重新输入！");
                continue;
            }
            //如果返回，库存不足
            if (s.equals("库存不足！")){
                System.out.println("库存不足！");
                continue;
            }
            //如果购买成功，放入销售记录集合中
            sellInfos.add(JSON.parseObject(s,SellInfo.class));
        }
        System.out.println("---------------世纪华联超市-------------------");
        System.out.println("账单打印时间："+ DateFormatUtil.datetimeFormat(new Date())+"\t收银员："+cashier.getNumber());
        System.out.println("---------------------------------------------");
        System.out.println("序号\t\t商品名称\t\t\t数量\t\t价格（元）");
        BigDecimal totalPrice = new BigDecimal("0.00");
        for (int i=0;i<sellInfos.size();i++){
            if (sellInfos.get(i).getVipNumber()!=null){
                System.out.println(i+1+"\t\t\t"+sellInfos.get(i).getGoodName()+"\t\t\t"+sellInfos.get(i).getQuantity()+"\t\t\t"+sellInfos.get(i).getGoodPrice().multiply(new BigDecimal(sellInfos.get(i).getQuantity())));
            }else{
                System.out.println(i+1+"\t\t\t"+sellInfos.get(i).getGoodName()+"\t\t\t"+sellInfos.get(i).getQuantity()+"\t\t\t"+sellInfos.get(i).getGoodPrice().multiply(new BigDecimal(sellInfos.get(i).getQuantity())));
            }
            //计算总价格
            totalPrice = totalPrice.add(sellInfos.get(i).getGoodPrice().multiply(new BigDecimal(sellInfos.get(i).getQuantity())));
        }
        System.out.println("\t\t\t\t\t\t\t\t\t\t本次消费："+totalPrice);
    }

    //会员开通
    public static void addVip(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true){
            //获取用户输入
            System.out.println("请填写信息（姓名-电话）（输入0退出）：");
            String message = sc.next();
            if (message.equals("0")){
                break;
            }
            String[] split = message.split("-");
            //创建vip对象
            Vip vip = new Vip();
            try {
                //判断手机号输入是否有误
                if (!CheckUtil.isValidPhoneNumber(split[1])){
                    continue;
                }
                vip.setName(split[0]);
                vip.setPhone(split[1]);
            }catch (Exception e){
                System.out.println("输入格式有误！");
                continue;
            }
            //向服务器发起短信验证请求
            dos.writeUTF("Send_Sms_phone:"+vip.getPhone());
            //接受验证码
            String s1 = dis.readUTF();
            //如果该手机被停用
            if (s1.equals("该手机号已停用！")){
                System.out.println(s1);
                continue;
            }
            //验证码校验
            while (true) {
                System.out.println("请输入验证码（输入0退出）：");
                String checkCode = sc.next();
                //退出
                if (checkCode.equals("0")){
                    return;
                }
                //判断是否输入一致
                if (s1.equals(checkCode)){
                    break;
                }
                System.out.println("验证码输入错误！");
            }
            //输入没问题，进行服务器请求添加vip
            dos.writeUTF("Admin_Vip_add:"+ JSON.toJSONString(vip));
            //获取服务器响应
            String s = dis.readUTF();
            //判断是否添加成功
            System.out.println(s);
            if (s.equals("添加失败！")) {
                continue;
            }
            break;
        }
    }

    //查询会员积分
    public static void findVipScore(DataOutputStream dos, DataInputStream dis) throws IOException {
        while (true){
            System.out.println("输入会员卡号/手机号：");
            String vipNumber = sc.next();
            //请求服务器查询会员账号
            dos.writeUTF("Cashier_Vip_query:"+vipNumber);
            //接受服务器响应
            String s = dis.readUTF();
            if (s.equals("会员卡不存在！")){
                System.out.println(s);
                continue;
            }
            //转换成Vip
            Vip vip = JSON.parseObject(s, Vip.class);
            System.out.println("会员姓名："+vip.getName());
            System.out.println("会员积分："+vip.getScore());
            break;
        }
    }

}
