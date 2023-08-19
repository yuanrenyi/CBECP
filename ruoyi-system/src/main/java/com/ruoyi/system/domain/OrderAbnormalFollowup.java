package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常订单跟进对象 order_abnormal_followup
 *
 * @author ruoyi
 * @date 2023-01-18
 */
@Data
public class OrderAbnormalFollowup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 订单表ID */
    @Excel(name = "订单表ID")
    private Long orderId;

    /** 跟进情况说明 */
    @Excel(name = "跟进情况说明")
    private String followupDesc;

    /**
     * 跟进时间
     */
    private String followupTime;

    /**
     * 提交人
     */
    private String followupName;
}
