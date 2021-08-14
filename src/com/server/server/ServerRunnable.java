package com.server.server;

import com.server.service.*;
import com.server.service.Impl.*;
import com.server.utils.CloseUtil;

import java.io.*;
import java.net.Socket;

/**
 * @Author:zdc
 * @Date 2021/8/11 17:08
 * @Version 1.0
 */
public class ServerRunnable implements Runnable {
    private Socket socket;
    private LoginService ls = new LoginServiceImpl();
    private AdminService as = new AdminServiceImpl();
    private EmployeeService es = new EmployeeServiceImpl();
    private CashierService cs = new CashierServiceImpl();
    private BuyerService bs = new BuyerServiceImpl();
    private VipService vs = new VipServiceImpl();

    public ServerRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            while (true) {
                InputStream inputStream = socket.getInputStream();
                dis =new DataInputStream(inputStream);
                OutputStream outputStream = socket.getOutputStream();
                dos =new DataOutputStream(outputStream);
                //读取用户的操作
                String input = dis.readUTF();
                //获取用户的操作类型
                String action = input.substring(0, input.indexOf(":"));
                //获取用户操作附带的信息
                String message = input.substring(input.indexOf(":") + 1);
                //根据不同的操作类型进行不同的业务处理
                switch (action){
                    //员工登录
                    case "Emp_login":
                        dos.writeUTF(ls.EmpLogin(message));
                        break;
                    //会员登录
                    case "Vip_login":
                        dos.writeUTF(ls.VipLogin(message));
                        break;
                    //查询收银员名单
                    case "Admin_CashierAdmin_query":
                        dos.writeUTF(as.queryEmpByRole("收银员"));
                        break;
                    //添加收银员
                    case "Admin_CashierAdmin_add":
                        dos.writeUTF(as.addEmp(message,"收银员"));
                        break;
                    //更新收银员
                    case "Admin_CashierAdmin_update":
                        dos.writeUTF(as.updateEmp(message));
                        break;
                    //删除收银员
                    case "Admin_CashierAdmin_delete":
                        dos.writeUTF(as.deleteEmp(message,"收银员"));
                        break;
                    //查询采购员名单
                    case "Admin_BuyerAdmin_query":
                        dos.writeUTF(as.queryEmpByRole("采购员"));
                        break;
                    //添加采购员
                    case "Admin_BuyerAdmin_add":
                        dos.writeUTF(as.addEmp(message,"采购员"));
                        break;
                    //更新采购员
                    case "Admin_BuyerAdmin_update":
                        dos.writeUTF(as.updateEmp(message));
                        break;
                    //删除采购员
                    case "Admin_BuyerAdmin_delete":
                        dos.writeUTF(as.deleteEmp(message,"采购员"));
                        break;
                    //查看全部考勤
                    case "Admin_Clock_queryAll":
                        dos.writeUTF(as.queryAllClock());
                        break;
                    //查询今日考勤
                    case "Admin_Clock_queryToday":
                        dos.writeUTF(as.queryTodayClock());
                        break;
                    //查询总营业额
                    case "Admin_Sell_queryTotal":
                        dos.writeUTF(as.queryTotalSell());
                        break;
                    //查询今日营业额
                    case "Admin_Sell_queryToday":
                        dos.writeUTF(as.queryTodaySell());
                        break;
                    //查询所有会员信息
                    case "Admin_Vip_query":
                        dos.writeUTF(as.queryAllVip());
                        break;
                    //添加会员
                    case "Admin_Vip_add":
                        dos.writeUTF(as.addVip(message));
                        break;
                    //修改会员
                    case "Admin_Vip_update":
                        dos.writeUTF(as.updateVip(message));
                        break;
                    //删除会员
                    case "Admin_Vip_del":
                        dos.writeUTF(as.deleteVip(message));
                        break;
                    //查询所有员工
                    case "Admin_Emp_query":
                        dos.writeUTF(as.queryAllEmp());
                        break;
                    //员工上班打卡
                    case "Emp_Clock_In":
                        dos.writeUTF(es.clockIn(message));
                        break;
                    //员工下班打卡
                    case "Emp_Clock_Off":
                        dos.writeUTF(es.clockOff(message));
                        break;
                    //查看会员是否存在
                    case "Cashier_Vip_query":
                        dos.writeUTF(cs.queryVipByNumber(message));
                        break;
                    //收银员售货
                    case "Cashier_Shell":
                        dos.writeUTF(cs.shell(message));
                        break;
                    //查询商品库存少于100
                    case "Buyer_Goods_Inventory":
                        dos.writeUTF(bs.queryInventory());
                        break;
                    //查询商品中是否存在该商品
                    case "Buyer_Goods_query":
                        dos.writeUTF(bs.queryGoodByNumber(message));
                        break;
                    //添加商品
                    case "Buyer_Goods_add":
                        dos.writeUTF(bs.addGoods(message));
                        break;
                    //修改商品库存
                    case "Buyer_Goods_addInventory":
                        dos.writeUTF(bs.addGoodInventory(message));
                        break;
                    //工作日查询
                    case "WorkDate_query":
                        dos.writeUTF(as.queryWorkDate());
                        break;
                    //工作日
                    case "WorkDate_add":
                        dos.writeUTF(as.addWorkDate(message));
                        break;
                    //删除工作日
                    case "WorkDate_del":
                        dos.writeUTF(as.delWorkDate(message));
                        break;
                    //积分兑换
                    case "Vip_Score_Exchange":
                        dos.writeUTF(vs.scoreExchange(message));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CloseUtil.close(socket, dis, dos);
        }
    }

}
