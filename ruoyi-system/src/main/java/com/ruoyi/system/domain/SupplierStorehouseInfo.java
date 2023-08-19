package com.ruoyi.system.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商仓库信息对象 supplier_storehouse_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
@ToString
public class SupplierStorehouseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库ID */
    private String id;

    /** 所属供应商ID */
    @Excel(name = "所属供应商ID")
    private String supplierId;

    /**
     * 供应商名称
     */
    @Excel(name = "所属供应商名称")
    private String supplierName;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String storehouseName;

    /** 仓库类型 */
    @Excel(name = "仓库类型")
    private String storehouseType;

    /** 货主ID */
    @Excel(name = "货主ID")
    private String consignorId;

    /** 外部仓库编码 */
    @Excel(name = "外部仓库编码")
    private String storehouseOutNo;

    /** token */
    @Excel(name = "token")
    private String storehouseToken;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contractName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contractPhone;

    /** 所在省 */
    private String storehouseProvince;

    /** 所在市 */
    private String storehouseCity;

    /** 所在区 */
    private String storehouseCounty;

    /** 邮税状态(1-不包邮不包税、2-包邮不包税、3-不包邮包税、4-包邮包税） */
    private Integer postalTaxType;

    @Excel(name = "邮税状态")
    private String postalTaxTypeStr;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal freightAmount;

    /** 续重运费 */
    @Excel(name = "续重运费")
    private BigDecimal freightRenewalAmount;

    /** 对接渠道 */
    @Excel(name = "对接渠道")
    private String dockingChannel;

    /** 是否支持拆单 0-不支持 1-支持 */
    private Integer ifSplitOrder;

    @Excel(name = "是否支持拆单")
    private String ifSplitOrderStr;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String addressDetail;

    /**
     * scname
     */
    @Excel(name = "scname")
    private String scname;

    /**
     * 净重+WeightIncrease=毛重，单位G
     */
    @Excel(name = "WeightIncrease(单位：G)")
    private Integer weightIncrease;

    /**
     * 续重
     */
    @Excel(name = "续重")
    private BigDecimal freightIncrease;

    /**
     * 所属BU
     */
    @Excel(name = "所属BU")
    private String belongingBu;

    /**
     * 接口秘钥
     */
    @Excel(name = "接口秘钥")
    private String signaTure;

    /**
     * 省名称
     */
    @Excel(name = "所在省")
    private String provinceName;

    /**
     * 市名称
     */
    @Excel(name = "所在市")
    private String cityName;

    /**
     * 区县名称
     */
    @Excel(name = "所在区县")
    private String countyName;

}
