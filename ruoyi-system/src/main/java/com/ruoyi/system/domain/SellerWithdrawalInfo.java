package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售方提现记录对象 seller_withdrawal_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
public class SellerWithdrawalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 销售方ID */
    @Excel(name = "销售方ID")
    private String sellerId;

    /**
     * 销售方名称
     */
    @Excel(name = "销售方名称")
    private String sellerName;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal withdrawalAmount;

    /** 提现前可用余额 */
    @Excel(name = "提现前可用余额")
    private BigDecimal withdrawalBeforeAmount;

    /** 提现后可用余额 */
    @Excel(name = "提现后可用余额")
    private BigDecimal withdrawalAfterAmount;

    /**
     * 提现记录关联订单信息表ID
     */
    private List<Long> orderInfoIds;

    /**
     * 提现记录对应的订单信息
     */
    private List<OrderInfo> orderInfoList;
}
