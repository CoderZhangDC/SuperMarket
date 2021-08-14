package com.server.dao.Impl;

import com.server.dao.EmpDao;
import com.server.pojo.Employee;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/11 21:00
 * @Version 1.0
 */
public class EmpDaoImpl implements EmpDao {

    @Override
    public List<Employee> queryEmpByRole(String role) {
        List<Employee> cashiers = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 and role.r_name='"+role+"'");
        try {
            while (rs.next()){
                Employee employee = new Employee();
                employee.setPassword(rs.getString("password"));
                employee.setUsername(rs.getString("username"));
                employee.setNumber(rs.getString("number"));
                employee.setPhone(rs.getString("phone"));
                employee.setRole(rs.getInt("role"));
                employee.setSex(rs.getString("sex"));
                employee.setRemark(rs.getInt("remark"));
                cashiers.add(employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return cashiers;
    }

    @Override
    public int insertEmp(Employee employee) {
       return JDBCUtil.executeUpdate("insert into employee(number,username,password,sex,phone,role,remark) values (?,?,?,?,?,?,1)"
               ,employee.getNumber(),employee.getUsername(),employee.getPassword(),employee.getSex(),employee.getPhone(),employee.getRole());
    }

    @Override
    public int updateEmp(Employee employee) {
        return JDBCUtil.executeUpdate("update employee set username=?,password=?,sex=?,phone=? where number=?",
                employee.getUsername(), employee.getPassword(), employee.getSex(), employee.getPhone(), employee.getNumber());
    }

    @Override
    public int deleteEmp(Employee employee) {
        return JDBCUtil.executeUpdate("update employee set remark=0 where number=? and role=?", employee.getNumber(),employee.getRole());
    }

    @Override
    public List<Employee> queryAllEmp() {
        List<Employee> employeeList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1");
        try {
            while (rs.next()){
                Employee employee = new Employee();
                employee.setPassword(rs.getString("password"));
                employee.setUsername(rs.getString("username"));
                employee.setNumber(rs.getString("number"));
                employee.setPhone(rs.getString("phone"));
                employee.setRole(rs.getInt("role"));
                employee.setSex(rs.getString("sex"));
                employee.setRemark(rs.getInt("remark"));
                employee.setRoleString(rs.getString("r_name"));
                employeeList.add(employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return employeeList;
    }

    @Override
    public Employee findEmpByNumber(String number) {
        Employee employee = new Employee();
        ResultSet rs = JDBCUtil.executeQuery("select * from employee where number=?", number);
        try {
            if (rs.next()){
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setNumber(rs.getString("number"));
                employee.setPhone(rs.getString("phone"));
                employee.setRole(rs.getInt("role"));
                employee.setSex(rs.getString("sex"));
                employee.setRemark(rs.getInt("remark"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return employee;
    }


}
