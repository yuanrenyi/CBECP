package com.ruoyi.system.domain.sellergoods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售方看到的商品列表信息
 */
@Data
public class SellerGoodsInfo {
    /**
     * 条形码
     */
    private String goodsBarcode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 主图地址
     */
    private String picUrl;

    /**
     * 建议零售价
     */
    private BigDecimal msrp;

    /**
     * 增值税税率
     */
    private BigDecimal vatRate;

    /**
     * 关税税率
     */
    private BigDecimal tariffRate;

    /**
     * 消费税税率
     */
    private BigDecimal exciseTaxRate;

    /**
     * 仓库信息列表
     */
    private List<SellerGoodsStorehouseInfo> storehouseInfoList;

    /**
     * 平台自定义条形码
     */
    private String platBarcode;

}
