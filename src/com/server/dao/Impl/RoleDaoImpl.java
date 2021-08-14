package com.server.dao.Impl;

import com.server.dao.RoleDao;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;

/**
 * @Author:zdc
 * @Date 2021/8/12 11:35
 * @Version 1.0
 */
public class RoleDaoImpl implements RoleDao {

    @Override
    public int queryRoleIdByName(String role) {
        int id=0;
        ResultSet rs = JDBCUtil.executeQuery("select id from role where r_name=?",role);
        try {
            if (rs.next()){
                id = rs.getInt("id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return id;
    }
}
