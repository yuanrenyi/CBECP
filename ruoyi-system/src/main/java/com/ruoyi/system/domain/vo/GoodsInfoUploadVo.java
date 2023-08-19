package com.ruoyi.system.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class GoodsInfoUploadVo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号")
    private String seqNo;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String goodsCode;

    /** 商品条形码 */
    @Excel(name = "商品条形码")
    private String goodsBarcode;

    /** HS编码 */
    @Excel(name = "HS编码")
    private String hsCode;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;
    /** 品牌 */
    @Excel(name = "品牌")
    private String goodsBrand;

    /** 分类 */
    @Excel(name = "分类")
    private String goodsClassification;

    /** 单位 */
    @Excel(name = "单位")
    private String goodsUnit;

    /** 重量 */
    @Excel(name = "重量（G）")
    private BigDecimal goodsWeight;

    /** 成本价 */
    @Excel(name = "成本价（元）")
    private BigDecimal costPrice;

    /** 建议零售价 */
    @Excel(name = "建议零售价（元）")
    private BigDecimal msrp;

    /** 裸价 */
    @Excel(name = "裸价（元）")
    private BigDecimal nakedPrice;
    /** JD指导价 */
    @Excel(name = "指导价JD（元）")
    private BigDecimal jdGuidancePrice;

    /** TM指导价 */
    @Excel(name = "指导价TM（元）")
    private BigDecimal tmGuidancePrice;

    /** WY指导价 */
    @Excel(name = "指导价WY（元）")
    private BigDecimal wyGuidancePrice;

    /** 税率% */
    @Excel(name = "税率（%）")
    private BigDecimal taxRate;

    /** 库存数量 */
    @Excel(name = "库存")
    private Integer stockNum;

    /** 对接货品ID */
    @Excel(name = "对接货品Id")
    private String dockingGoodsId;

    /**
     * 是否自建
     */
    @Excel(name = "是否自建")
    private Integer ifBuildByOneself;

    /**
     * 是否产品包
     */
    @Excel(name = "是否产品包")
    private Integer ifPackage;

    /**
     * 产品包关联商品条码
     */
    @Excel(name = "产品包关联商品条码")
    private String barCodeList;

    /**
     * 产品包关联商品数量
     */
    @Excel(name = "产品包关联商品数量")
    private String numberList;

    /**
     * 平台上架状态
     */
    @Excel(name = "上架状态")
    private Integer platOnShelf;

    @Excel(name = "备注")
    private String remark;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal freightAmount;

    /** 续重运费 */
    @Excel(name = "续重运费")
    private BigDecimal freightRenewalAmount;

    @Excel(name = "效期")
    private String expirDate;

    @Excel(name = "效期备注")
    private String expirDateRemark;

    @Excel(name = "关键字")
    private String keyWord;

    @Excel(name = "购买须知")
    private String buyNotice;

    @Excel(name = "规格型号")
    private String specModel;

    @Excel(name = "主要型号")
    private String mainModel;

    @Excel(name = "主要成分")
    private String basis;

    @Excel(name = "原产地")
    private String originCountry;

}
