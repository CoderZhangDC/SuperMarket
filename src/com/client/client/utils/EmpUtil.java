package com.client.client.utils;

import com.server.pojo.Employee;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @Author:zdc
 * @Date 2021/8/12 19:05
 * @Version 1.0
 */
public class EmpUtil {

    //员工上班打卡
    public static void clockIn(DataOutputStream dos, DataInputStream dis, Employee employee) throws IOException {
        //向服务器提交打卡请求
        dos.writeUTF("Emp_Clock_In:"+employee.getNumber());
        //接受服务器的打卡结果
        String s = dis.readUTF();
        //打印打卡结果
        System.out.println(s);
    }

    //员工下班打卡
    public static void clockOff(DataOutputStream dos, DataInputStream dis,Employee employee) throws IOException {
        //向服务器提交打卡请求
        dos.writeUTF("Emp_Clock_Off:"+employee.getNumber());
        //接受服务器的打卡结果
        String s = dis.readUTF();
        //打印打卡结果
        System.out.println(s);
    }

}
