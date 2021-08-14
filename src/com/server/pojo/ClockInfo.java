package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/11 15:53
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClockInfo {
    private String empNumber;
    private String empName;
    private Date clockInTime;
    private Date clockOffTime;
    private Date clockDate;
    private String clockInStatus;
    private String clockOffStatus;
}
