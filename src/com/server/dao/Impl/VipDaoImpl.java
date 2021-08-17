package com.server.dao.Impl;

import com.client.client.utils.DateFormatUtil;
import com.server.dao.VipDao;
import com.server.pojo.ScoreInfo;
import com.server.pojo.Vip;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/11 19:56
 * @Version 1.0
 */
public class VipDaoImpl implements VipDao {
    @Override
    public Vip findVipByNumber(String number) {
        //如果输入的是null，直接返回null
        if (number.equals("null")){
            return null;
        }
        Vip vip = null;
        ResultSet rs = JDBCUtil.executeQuery("select * from vip where v_remark=1 and (v_number=? or v_phone=?)", number,number);
        try {
            if (rs.next()){
                vip = new Vip();
                vip.setNumber(rs.getString("v_number"));
                vip.setDate(rs.getDate("v_date"));
                vip.setName(rs.getString("v_name"));
                vip.setScore(rs.getInt("v_score"));
                vip.setPhone(rs.getString("v_phone"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        System.out.println(vip);
        return vip;
    }

    @Override
    public List<Vip> queryAllVip() {
        List<Vip> vipList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from vip where v_remark=1 and v_number != 'null'");
        try {
            while (rs.next()){
                Vip vip = new Vip();
                vip.setNumber(rs.getString("v_number"));
                vip.setDate(rs.getDate("v_date"));
                vip.setName(rs.getString("v_name"));
                vip.setScore(rs.getInt("v_score"));
                vip.setPhone(rs.getString("v_phone"));
                vipList.add(vip);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return vipList;
    }

    @Override
    public String queryLastVipNumber() {
        ResultSet rs = JDBCUtil.executeQuery("select v_number from vip order by v_number desc limit 1");
        String number = null;
        try {
            if (rs.next()){
                number = rs.getString("v_number");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }

    @Override
    public int insertVip(Vip vip) {
        return JDBCUtil.executeUpdate("insert into vip values(?,?,?,?,?,1)", vip.getNumber(), vip.getName(), vip.getScore(), vip.getPhone(), vip.getDate());
    }

    @Override
    public int updateVip(Vip vip) {
        System.out.println(vip);
        return JDBCUtil.executeUpdate("update vip set v_name=?,v_score=?,v_phone=? where v_number=?",
                vip.getName(),vip.getScore(),vip.getPhone(),vip.getNumber());
    }

    @Override
    public int deleteVip(String message) {
        return JDBCUtil.executeUpdate("update vip set v_remark=0 where v_number=?",message);
    }

    @Override
    public void updateVipScore(String vipNumber, int goodScore) {
        JDBCUtil.executeUpdate("update vip set v_score=v_score-? where v_number=?",goodScore,vipNumber);
    }

    @Override
    public void insertScoreInfo(ScoreInfo scoreInfo) {
        JDBCUtil.executeUpdate("insert into score_info(s_vip_number,s_time,s_quantity,s_g_number) values(?,?,?,?)",
                scoreInfo.getVipNumber(), DateFormatUtil.datetimeFormat(new Date()),scoreInfo.getGoodQuantity(),scoreInfo.getGoodNumber());
    }
}
