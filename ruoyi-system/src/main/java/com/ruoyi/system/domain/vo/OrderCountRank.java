package com.ruoyi.system.domain.vo;

import lombok.Data;

@Data
public class OrderCountRank {
    /**
     * 销售方id
     */
    private String sellerId;

    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 订单数量
     */
    private Long orderCount;

}
