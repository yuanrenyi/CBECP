package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.NoArgsConstructor;

/**
 * 供应商提现记录与订单记录关联对象 supplier_withdrawal_order_ref
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierWithdrawalOrderRef extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 供应商提现记录ID */
    @Excel(name = "供应商提现记录ID")
    private Long supplierWithdrawalId;

    /** 订单记录表ID */
    @Excel(name = "订单记录表ID")
    private Long orderInfoId;

}
