package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 销售额vo
 */
@Data
public class SaleAmountVo {
    /**
     * 日期
     */
    private String saleDate;

    /**
     * 金额
     */
    private BigDecimal saleAmount;

    /**
     * 订单数
     */
    private Integer saleCount;
}
