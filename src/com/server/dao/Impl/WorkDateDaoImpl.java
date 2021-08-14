package com.server.dao.Impl;

import com.server.dao.WorkDateDao;
import com.server.pojo.WorkDate;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/13 9:02
 * @Version 1.0
 */
public class WorkDateDaoImpl implements WorkDateDao {

    @Override
    public List<WorkDate> queryWorkDate() {
        List<WorkDate> workDateList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from work_date");
        try {
            while (rs.next()){
                WorkDate workDate = new WorkDate();
                workDate.setId(rs.getInt("work_date_id"));
                workDate.setDate(rs.getDate("work_date"));
                workDateList.add(workDate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workDateList;
    }

    @Override
    public int addWorkDate(String message) {
        return JDBCUtil.executeUpdate("insert into work_date(work_date) values (?)", message);
    }

    @Override
    public int delWorkDate(String message) {
        return JDBCUtil.executeUpdate("delete from work_date where work_date=?",message);
    }
}
