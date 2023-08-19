package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品信息对象 goods_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
@ToString
public class GoodsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

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

    /** 分类 */
    private List<Long> goodsClassificationArr;

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


    /** 商品ID */
    private String id;

    /** 所在仓库ID */
    @Excel(name = "所在仓库ID")
    private String storehouseId;

    /**
     * 所在仓库名称
     */
    @Excel(name = "所在仓库名称")
    private String storehouseName;

    /** 所属供应商ID */
    @Excel(name = "所属供应商ID")
    private String supplierId;

    /**
     * 所属供应商名称
     */
    @Excel(name = "所属供应商名称")
    private String supplierName;



    /** 锁定库存数量 */
    @Excel(name = "锁定库存数量")
    private Integer stockLockNum;


    /** 上架状态 0-未上架,1-已上架 */
    @Excel(name = "上架状态 0-未上架,1-已上架")
    private Integer onShelfStatus;


    /** 毛重 */
    @Excel(name = "毛重")
    private BigDecimal grossWeight;


    /** 最终运费 */
    @Excel(name = "最终运费")
    private BigDecimal finalFreightAmount;

    /**
     * 审核状态  0-待审核 1-审核通过 2-审核未通过
     */
    private Integer auditStatus;

    /**
     * 审核人
     */
    private String auditBy;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 是否推荐为推荐商品 0-未推荐 1-推荐
     */
    private Integer recommend;

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
     * 删除标志 1-未删除 2-已删除
     */
    private Integer delFlag;

    /**
     * 图片版本
     */
    private String picVersion;

    /**
     * 商品外部编码
     */
    @Excel(name = "外部商品编码")
    private String goodsOutCode;

    /**
     * 销售方id
     */
    private String sellerId;

    /**
     * 销售方上架状态 0：未上架，1：已上架
     */
    private Integer sellerOnshelfStatus;

    /**
     * 库存数量（起始值）
     */
    private Integer stockNumStart;

    /**
     * 库存数量（终止值）
     */
    private Integer stockNumEnd;

    /**
     * 成本价（起始值）
     */
    private BigDecimal costPriceStart;

    /**
     * 成本价（终止值）
     */
    private BigDecimal costPriceEnd;

    /**
     * 是否有图片 1-是 2-否
     */
    private Integer hasPic ;

    /** 图片类型 1-主图 2-详情图 3-视频 4-缩略图 */
    private String picType;

    /**
     * 是否已分类 1-是 2-否
     */
    private Integer hasCatalog;

    /**
     * 效期
     */
    private String expirDate;

    /**
     * 效期备注
     */
    private String expirDateRemark;

    /**
     * 有效期查询-起始
     */
    private String expirDateBegin;

    /**
     * 有效期查询-终止
     */
    private String expirDateEnd;

    /**
     * 关键字
     */
    @Excel(name = "关键字")
    private String keyWord;

    /**
     * 购买须知
     */
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

    private String platBarcode;

    /**
     * 排序字段
     * 可选：
     *      cost_price : 成本价
     *      stock_num : 库存数量
     */
    private String sortColumn;

    /**
     * 排序方法 ： asc=升序  desc=降序
     */
    private String sortOrder;

    /**
     * 兼容前端排序字段
     * @param sortColumn
     */
    public void setSortColumn(String sortColumn) {
        if (StringUtils.isNotEmpty(sortColumn))
        {
            // 兼容前端排序类型
            if ("costPrice".equals(sortColumn))
            {
                sortColumn = "cost_price";
            }
            else if ("stockNum".equals(sortColumn))
            {
                sortColumn = "stock_num";
            }
            this.sortColumn = sortColumn;
        }
    }

    /**
     * 兼容前端排序类型
     * @param sortOrder
     */
    public void setSortOrder(String sortOrder)
    {
        if (StringUtils.isNotEmpty(sortOrder))
        {
            // 兼容前端排序类型
            if ("ascending".equals(sortOrder))
            {
                sortOrder = "asc";
            }
            else if ("descending".equals(sortOrder))
            {
                sortOrder = "desc";
            }
            this.sortOrder = sortOrder;
        }
    }
}
