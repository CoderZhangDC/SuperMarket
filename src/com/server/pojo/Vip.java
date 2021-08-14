package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:02
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vip {
    private String number;
    private String name;
    private int score;
    private String phone;
    private Date date;

}
