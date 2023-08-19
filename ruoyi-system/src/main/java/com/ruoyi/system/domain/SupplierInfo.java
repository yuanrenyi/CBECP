package com.ruoyi.system.domain;

import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 供应商信息对象 supplier_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
@ToString
public class SupplierInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供应商ID */
    private String id;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 昵称 */
    @Excel(name = "昵称")
    private String supplierNickName;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contractName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contractPhone;

    /** 添加人（采购业务员） */
    @Excel(name = "添加人", readConverterExp = "采=购业务员")
    private String purchaser;

    /** 提现账户 */
    @Excel(name = "提现账户")
    private String withdrawalAccount;

    /**
     * 货主id，唯一id不能重复
     */
    @Excel(name = "货主id")
    private String ownerUserId;

    /**
     * 秘钥
     */
    @Excel(name = "秘钥")
    private String secretkey;

    /**
     * 启用/禁用状态  0：启用  1：禁用
     */
    @Excel(name = "启用/禁用状态")
    private Integer enabled;

    /**
     * 提现手续费
     */
    @Excel(name = "提现手续费")
    private BigDecimal formalitiesFee;

    /**
     * 提现手续费类型，1：固定金额，2：百分比
     */
    @Excel(name = "提现手续费类型")
    private Integer formalitiesFeeType;

    /**
     * 保税仓id
     */
    private Long bondedWarehouseId;

    /**
     * 保税仓名称
     */
    @Excel(name = "保税仓名称")
    private String bondedWarehouseName;
}
