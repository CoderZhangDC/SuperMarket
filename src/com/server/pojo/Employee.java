package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/11 15:56
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String number;
    private String username;
    private String password;
    private String sex;
    private String phone;
    private int role;
    private int remark;
    private Date registerTime;
    private int reClockCount;
    private int salary;

    //角色的名称
    private String roleString;

}
