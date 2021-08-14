package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/11 16:00
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellInfo {
    private int goodNumber;
    private int quantity;
    private Date time;
    private String empNumber;
    private String vipNumber;

    private String goodName;
    private BigDecimal goodPrice;
}
