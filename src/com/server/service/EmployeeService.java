package com.server.service;

public interface EmployeeService {
    //员工上班打卡
    String clockIn(String message);

    //员工下班打卡
    String clockOff(String message);
}
