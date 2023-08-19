package com.ruoyi.system.domain.vo;

import lombok.Data;

@Data
public class AmountCountRank {
    /**
     * 销售方id
     */
    private String sellerId;

    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 销售额
     */
    private Long amountCount;
}
