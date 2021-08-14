package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDate {
    private int id;
    private Date date;
}
