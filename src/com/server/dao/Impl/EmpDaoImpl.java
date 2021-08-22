package com.server.dao.Impl;

import com.client.utils.DateFormatUtil;
import com.server.dao.EmpDao;
import com.server.pojo.Employee;
import com.server.utils.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
                employee.setRegisterTime(rs.getTimestamp("register_time"));
                employee.setReClockCount(rs.getInt("re_clock_count"));
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
       return JDBCUtil.executeUpdate("insert into employee(number,username,password,sex,phone,role,remark,register_time,re_clock_count,salary) values (?,?,?,?,?,?,1,?,5,?)"
               ,employee.getNumber(),employee.getUsername(),employee.getPassword(),employee.getSex(),employee.getPhone(),
               employee.getRole(), DateFormatUtil.datetimeFormat(new Date()),employee.getSalary());
    }

    @Override
    public int updateEmp(Employee employee) {
        return JDBCUtil.executeUpdate("update employee set username=?,password=?,sex=?,phone=?,salary=? where number=?",
                employee.getUsername(), employee.getPassword(), employee.getSex(), employee.getPhone(), employee.getSalary(), employee.getNumber());
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
                employee.setRegisterTime(rs.getTimestamp("register_time"));
                employee.setReClockCount(rs.getInt("re_clock_count"));
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
        Employee employee = null;
        ResultSet rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and number=? or phone=?", number,number);
        try {
            if (rs.next()){
                employee =  new Employee();
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setNumber(rs.getString("number"));
                employee.setPhone(rs.getString("phone"));
                employee.setRole(rs.getInt("role"));
                employee.setSex(rs.getString("sex"));
                employee.setRemark(rs.getInt("remark"));
                employee.setRoleString(rs.getString("r_name"));
                employee.setRegisterTime(rs.getTimestamp("register_time"));
                employee.setSalary(rs.getInt("salary"));
                employee.setReClockCount(rs.getInt("re_clock_count"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return employee;
    }

    @Override
    public void updateEmpReClockCount(String empNumber) {
        JDBCUtil.executeUpdate("update employee set re_clock_count=re_clock_count-1 where number=?",empNumber);
    }

    @Override
    public List<Employee> findEmpByCondition(String message,String type) {
        List<Employee> employeeList = new ArrayList<>();
        ResultSet rs = null;
        //根据不同的条件查询
        if (type.equals("name")){
            rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 and username like ?","%"+message+"%");
        }else if (type.equals("sex")){
            rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 and sex = ?",message);
        }else if (type.equals("registerYear")){
            rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 and register_time like ?",message+"%");
        }
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
                employee.setRegisterTime(rs.getTimestamp("register_time"));
                employee.setReClockCount(rs.getInt("re_clock_count"));
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
    public List<Employee> queryResignEmp() {
        List<Employee> employeeArrayList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=0");
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
                employee.setRegisterTime(rs.getTimestamp("register_time"));
                employee.setReClockCount(rs.getInt("re_clock_count"));
                employeeArrayList.add(employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return employeeArrayList;
    }

    @Override
    public List<Employee> queryEmpBySalaryTop(String type) {
        List<Employee> employeeArrayList = new ArrayList<>();
        ResultSet rs = null;
        if (type.equals("top10")) {
            //查询前10
            rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 order by salary desc limit 10;");
        }else if (type.equals("top5")){
            //查询后5
            rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 order by salary asc limit 5;");
        }
        try {
            while (rs.next()){
                Employee employee = new Employee();
                employee.setUsername(rs.getString("username"));
                employee.setNumber(rs.getString("number"));
                employee.setPhone(rs.getString("phone"));
                employee.setRole(rs.getInt("role"));
                employee.setSex(rs.getString("sex"));
                employee.setSalary(rs.getInt("salary"));
                employeeArrayList.add(employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return employeeArrayList;
    }

    @Override
    public List<Employee> queryEmpBySalaryRange(Integer integer, Integer integer1) {
        List<Employee> employeeArrayList = new ArrayList<>();
        ResultSet rs = JDBCUtil.executeQuery("select * from employee,role where employee.role=role.id and remark=1 and salary between ? and ? order by salary desc",integer,integer1);
        try {
            while (rs.next()){
                Employee employee = new Employee();
                employee.setUsername(rs.getString("username"));
                employee.setNumber(rs.getString("number"));
                employee.setPhone(rs.getString("phone"));
                employee.setRole(rs.getInt("role"));
                employee.setSex(rs.getString("sex"));
                employee.setSalary(rs.getInt("salary"));
                employeeArrayList.add(employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs);
        }
        return employeeArrayList;
    }


}
