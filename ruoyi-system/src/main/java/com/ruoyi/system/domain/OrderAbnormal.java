package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.NoArgsConstructor;

/**
 * 异常订单对象 order_abnormal
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAbnormal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 订单表ID */
    @Excel(name = "订单表ID")
    private Long orderInfoId;

    /** 标记原因 */
    @Excel(name = "标记原因")
    private String abnormalDesc;

}
