package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品动销vo
 */
@Data
public class GoodsSaleVo {
    private String goodsId;

    private String goodsOutCode;

    private String goodsBarcode;

    private String goodsName;

    private Long quantity;

    private BigDecimal price;

    private Integer stockNum;

    private BigDecimal orgIncome;

}
