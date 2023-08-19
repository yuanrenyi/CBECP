package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.NoArgsConstructor;

/**
 * 销售方提现记录与订单记录关联对象 seller_withdrawal_order_ref
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerWithdrawalOrderRef extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 销售方提现记录表ID */
    @Excel(name = "销售方提现记录表ID")
    private Long sellerWithdrawalId;

    /** 订单记录表ID */
    @Excel(name = "订单记录表ID")
    private Long orderInfoId;
}
