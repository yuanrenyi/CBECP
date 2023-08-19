package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DataTableRespVo {
    /**
     * 订单总数
     */
    private Long orderCount;

    /**
     * 总销售金额
     */
    private BigDecimal orderAmountCount;

    /**
     * 最近7天销售数据
     */
    private List<SaleAmountVo> saleData7Days;

}
