package com.server.dao.Impl;

import com.client.utils.DateFormatUtil;
import com.server.dao.SellDao;
import com.server.pojo.Report;
import com.server.pojo.SellInfo;
import com.server.utils.JDBCUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/12 14:45
 * @Version 1.0
 */
public class SellDaoImpl implements SellDao {

    @Override
    public String querySellByType(String type) {
        //查询普通用户消费总额
        ResultSet rs = null;
        //查询会员消费总额
        ResultSet rs2 = null;
        if (type.equals("today")){
            rs= JDBCUtil.executeQuery("select sum(s_quantity*vip_price) vip_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is not null and s_time like ?", DateFormatUtil.dateFormat(new Date())+"%");
            rs2 = JDBCUtil.executeQuery("select sum(s_quantity*c_price) user_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is null and s_time like ?", DateFormatUtil.dateFormat(new Date())+"%");
        }else if (type.equals("total")){
            rs = JDBCUtil.executeQuery("select sum(s_quantity*vip_price) vip_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is not null");
            rs2 = JDBCUtil.executeQuery("select sum(s_quantity*c_price) user_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is null");
        }else if (type.equals("month")){
            rs = JDBCUtil.executeQuery("select sum(s_quantity*vip_price) vip_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is not null and DATE_FORMAT(s_time,'%Y%m') = DATE_FORMAT(CURDATE(),'%Y%m')");
            rs2 = JDBCUtil.executeQuery("select sum(s_quantity*c_price) user_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is null and DATE_FORMAT(s_time,'%Y%m') = DATE_FORMAT(CURDATE(),'%Y%m')");
        }else if (type.equals("season")){
            rs = JDBCUtil.executeQuery("select sum(s_quantity*vip_price) vip_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is not null and quarter(s_time)=quarter(now())");
            rs2 = JDBCUtil.executeQuery("select sum(s_quantity*c_price) user_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is null and quarter(s_time)=quarter(now())");
        }
        BigDecimal total = new BigDecimal("0.00");
        try {
            if (rs.next()){
                BigDecimal vip_total = rs.getBigDecimal("vip_total");
                if (vip_total!=null) {
                    total = total.add(vip_total);
                }
            }
            if (rs2.next()){
                BigDecimal user_total = rs2.getBigDecimal("user_total");
                if (user_total!=null){
                    total = total.add(user_total);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(rs2);
        }
        return String.valueOf(total);
    }


    @Override
    public void insertShellInfo(SellInfo si) {
        JDBCUtil.executeUpdate("insert into sell_info values(?,?,?,?,?)",
                si.getGoodNumber(),si.getQuantity(),DateFormatUtil.datetimeFormat(new Date()),
                si.getEmpNumber(),si.getVipNumber());
    }

    @Override
    public List<Report> queryReport() {
        List<Report> reportList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from sell_info,goods,employee  where \n" +
                "sell_info.s_c_number=goods.c_number\n" +
                "and sell_info.s_e_number=employee.number");
        try {
            while (rs.next()){
                Report report = new Report();
                report.setEmpName(rs.getString("username"));
                report.setGoodName(rs.getString("c_name"));
                report.setPrice(rs.getBigDecimal("c_price"));
                report.setVipPrice(rs.getBigDecimal("vip_price"));
                report.setShellQuantity(rs.getInt("s_quantity"));
                report.setShellTime(rs.getTimestamp("s_time"));
                reportList.add(report);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return reportList;
    }

    @Override
    public String querySellByRange(List<String> list) {
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        ResultSet rs = JDBCUtil.executeQuery("select sum(s_quantity*vip_price) vip_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is not null and s_time between ? and ?",list.get(0),list.get(1)+" 23:59:59");
        ResultSet rs2 = JDBCUtil.executeQuery("select sum(s_quantity*c_price) user_total from sell_info s,goods g where s.s_c_number=g.c_number and s.s_vip_number is null and s_time between ? and ?",list.get(0),list.get(1)+" 23:59:59");
        BigDecimal total = new BigDecimal("0.00");
        try {
            if (rs.next()){
                BigDecimal vip_total = rs.getBigDecimal("vip_total");
                if (vip_total!=null) {
                    total = total.add(vip_total);
                }
            }
            if (rs2.next()){
                BigDecimal user_total = rs2.getBigDecimal("user_total");
                if (user_total!=null){
                    total = total.add(user_total);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
            JDBCUtil.close(rs2);
        }
        return String.valueOf(total);
    }

}
