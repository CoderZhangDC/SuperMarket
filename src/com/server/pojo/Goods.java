package com.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author:zdc
 * @Date 2021/8/11 15:57
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int number;
    private String name;
    private BigDecimal price;
    private BigDecimal vipPrice;
    private int inventory;
    private int remark;
}
