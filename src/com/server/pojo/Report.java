package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:zdc
 * @Date 2021/8/13 9:50
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private int shellQuantity;
    private Date shellTime;
    private String empName;
    private String goodName;
    private BigDecimal price;
    private BigDecimal vipPrice;
}
