package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售方服务方案对象 seller_service_plan
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
public class SellerServicePlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 销售方ID */
    @Excel(name = "销售方ID")
    private String compId;

    /**
     * 销售方名称
     */
    @Excel(name = "销售方名称")
    private String sellerName;

    /** 服务方案（word）地址 */
    @Excel(name = "服务方案")
    private String servicePlan;

    /** word文档名称 */
    @Excel(name = "word文档名称")
    private String servicePlanName;

    /** 是否为最新方案 0-否 1-是 */
    @Excel(name = "是否为最新方案 0-否 1-是")
    private Integer ifNews;
}
