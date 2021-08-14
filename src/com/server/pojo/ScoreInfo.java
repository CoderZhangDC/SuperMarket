package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/13 16:22
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreInfo {
    private String vipNumber;
    private int goodNumber;
    private int goodQuantity;
    private Date time;
    private int score;
}
