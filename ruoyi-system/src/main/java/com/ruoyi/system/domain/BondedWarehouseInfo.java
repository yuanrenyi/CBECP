package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 保税仓对象 bonded_warehouse_info
 */
@Data
public class BondedWarehouseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增ID */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String bondedWarehouseName;

    /** 等级 */
    @Excel(name = "等级")
    private Integer bondedWarehouseLevel;

    /** 上级id */
    @Excel(name = "上级id")
    private Long parentId;

    /** 删除标志1-未删除 2-已删除 */
    private Integer delFlag;

    /**
     * 下级保税仓
     */
    private List<BondedWarehouseInfo> children;
}
