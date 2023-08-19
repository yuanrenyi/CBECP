package com.ruoyi.system.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsInfoUploadUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号")
    private String seqNo;

    @Excel(name = "外部编码")
    private String goodsOutCode;

    @Excel(name = "商品编码")
    private String goodsCode;

    @Excel(name = "对接货品Id")
    private String dockingGoodsId;

    @Excel(name = "商品条形码")
    private String goodsBarcode;

    @Excel(name = "裸价")
    private BigDecimal nakedPrice;

    @Excel(name = "成本价")
    private BigDecimal costPrice;

    @Excel(name = "建议零售价（元）")
    private BigDecimal msrp;

    @Excel(name = "指导价JD（元）")
    private BigDecimal jdGuidancePrice;

    @Excel(name = "指导价TM（元）")
    private BigDecimal tmGuidancePrice;

    @Excel(name = "指导价WY（元）")
    private BigDecimal wyGuidancePrice;

    @Excel(name = "库存")
    private Integer stockNum;

    @Excel(name = "上架状态")
    private String platOnShelfStr;

    private Integer platOnShelf;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "效期", importFormat = "yyyy/MM/dd")
    private String expirDate;

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

    @Excel(name = "商品名称")
    private String goodsName;

    @Excel(name = "效期备注")
    private String expirDateRemark;

}
