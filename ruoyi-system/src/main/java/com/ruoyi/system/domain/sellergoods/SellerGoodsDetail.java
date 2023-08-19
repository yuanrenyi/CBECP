package com.ruoyi.system.domain.sellergoods;

import com.ruoyi.system.domain.GoodsPicInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售方查看的商品详情
 */
@Data
public class SellerGoodsDetail {
    /**
     * 商品id
     */
    private String id;

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

    /** 分类 */
    private List<Long> goodsClassificationArr;

    /**
     * 图片信息
     */
    private List<GoodsPicInfo> picList;

    /**
     * 仓库信息列表
     */
    private List<SellerGoodsStorehouseInfo> storehouseInfoList;

}
