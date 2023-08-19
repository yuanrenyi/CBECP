package com.ruoyi.system.domain.sellergoods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售方看到的商品列表中的仓库信息
 */
@Data
public class SellerGoodsStorehouseInfo {

    /**
     * 名称
     */
    private String goodsName;

    /**
     * 重量
     */
    private BigDecimal goodsWeight;

    /**
     * 条形码
     */
    private String goodsBarcode;


    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 外部编码
     */
    private String goodsOutCode;

    /**
     * 品牌
     */
    private String goodsBrand;

    /**
     * 类别
     */
    private String goodsClassification;

    /**
     * 效期
     */
    private String expirDate;

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 仓库ID
     */
    private String storehouseId;

    /**
     * 仓库名称
     */
    private String storehouseName;

    /**
     * 库存
     */
    private Integer stockNum;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 裸价
     */
    private BigDecimal nakedPrice;

    /**
     * 续重运费
     */
    private BigDecimal freightRenewalAmount;

    /**
     * 毛重
     */
    private BigDecimal grossWeight;

    /**
     * 首重运费
     */
    private BigDecimal freightAmount;

    /**
     * 最终运费
     */
    private BigDecimal finalFreightAmount;

    /**
     * 上架状态 0-未上架 1-已上架
     */
    private Integer onShelfStatus;

    /**
     * 平台自定义条形码
     */
    private String platBarcode;

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

    /** 分类 */
    private List<Long> goodsClassificationArr;

}
