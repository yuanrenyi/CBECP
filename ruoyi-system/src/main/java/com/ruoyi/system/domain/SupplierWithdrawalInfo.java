package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商提现记录对象 supplier_withdrawal_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
public class SupplierWithdrawalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private String supplierId;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal withdrawalAmount;

    /** 审核状态 0-未审核 1-审核通过 2-审核未通过 */
    @Excel(name = "审核状态 0-未审核 1-审核通过 2-审核未通过")
    private Integer auditStatus;

    /** 提现前可用余额 */
    @Excel(name = "提现前可用余额")
    private BigDecimal withdrawalBeforeAmount;

    /** 提现后可用余额 */
    @Excel(name = "提现后可用余额")
    private BigDecimal withdrawalAfterAmount;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /**
     * 提现记录关联订单信息表ID
     */
    private List<Long> orderInfoIds;

    /**
     * 提现记录对应的订单信息
     */
    private List<OrderInfo> orderInfoList;
}
