package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsInfoExportRespVo {

    @Excel(name = "序号")
    private Integer seqNo;

    @Excel(name = "仓库名称")
    private String storehouseName;

    @Excel(name = "商品编码")
    private String goodsCode;

    @Excel(name = "外部编码")
    private String goodsOutCode;

    @Excel(name = "商品条形码")
    private String goodsBarcode;

    @Excel(name = "HS编码")
    private String hsCode;

    @Excel(name = "商品名称")
    private String goodsName;

    @Excel(name = "品牌")
    private String goodsBrand;

    @Excel(name = "分类")
    private String goodsClassification;

    /** 分类 */
    private List<Long> goodsClassificationArr;

    @Excel(name = "单位")
    private String goodsUnit;

    @Excel(name = "重量（G）")
    private BigDecimal goodsWeight;

    @Excel(name = "成本价（元）")
    private BigDecimal costPrice;

    @Excel(name = "裸价（元）")
    private BigDecimal nakedPrice;

    @Excel(name = "建议零售价（元）")
    private BigDecimal msrp;

    @Excel(name = "指导价JD（元）")
    private BigDecimal jdGuidancePrice;

    @Excel(name = "指导价TM（元）")
    private BigDecimal tmGuidancePrice;

    @Excel(name = "指导价WY（元）")
    private BigDecimal wyGuidancePrice;

    @Excel(name = "税率（%）")
    private BigDecimal taxRate;

    @Excel(name = "库存")
    private Integer stockNum;

    @Excel(name = "锁定库存")
    private Integer stockLockNum;

    @Excel(name = "对接货品Id")
    private String dockingGoodsId;

    private Integer platOnShelf;

    @Excel(name = "上架状态")
    private String platOnShelfStr;

    @Excel(name = "运费")
    private BigDecimal freightAmount;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "毛重")
    private BigDecimal grossWeight;

    @Excel(name = "最终运费")
    private BigDecimal finalFreightAmount;

    @Excel(name = "对接渠道")
    private String channelName = "";

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
