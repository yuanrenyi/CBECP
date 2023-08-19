package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.SellerInfo;
import com.ruoyi.system.domain.SellerServicePlan;
import lombok.Data;

/**
 * 销售方Vo
 */
@Data
public class SellerInfoVo extends SellerInfo {
    /**
     * 销售方服务方案
     */
    private SellerServicePlan servicePlan;
}
