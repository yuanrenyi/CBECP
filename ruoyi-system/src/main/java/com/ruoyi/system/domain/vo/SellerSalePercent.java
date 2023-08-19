package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 销售方销售额占比分布
 */
@Data
public class SellerSalePercent {

    /**
     * 销售方ID
     */
    private String sellerId;

    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 销售总金额
     */
    private BigDecimal saleAmount;

    /**
     * 销售总金额占比（%）
     */
    private BigDecimal percent;
}
