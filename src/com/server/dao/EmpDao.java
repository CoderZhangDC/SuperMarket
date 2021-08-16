package com.server.dao;

import com.server.pojo.Employee;

import java.util.List;

/**
 * @Author:zdc
 * @Date 2021/8/11 21:00
 * @Version 1.0
 */
public interface EmpDao {
    //通过角色查询员工信息
    List<Employee> queryEmpByRole(String role);

    //插入员工信息
    int insertEmp(Employee employee);

    //修改员工信息
    int updateEmp(Employee employee);

    //删除员工信息
    int deleteEmp(Employee Employee);

    //查询所有员工
    List<Employee> queryAllEmp();

    //根据员工编号查找员工
    Employee findEmpByNumber(String number);

    //修改用户的补卡次数
    void updateEmpReClockCount(String empNumber);

    //根据条件查找
    List<Employee> findEmpByCondition(String message,String type);

    //查询离职员工
    List<Employee> queryResignEmp();

    //根据薪水排序查询查询，升序/降序
    List<Employee> queryEmpBySalaryTop(String type);

    //查询指定区间的薪水员工
    List<Employee> queryEmpBySalaryRange(Integer integer, Integer integer1);
}
