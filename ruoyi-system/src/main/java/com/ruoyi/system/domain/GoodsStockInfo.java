package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品锁库存对象 goods_stock_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
public class GoodsStockInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 商品ID */
    @Excel(name = "商品ID")
    private String goodsId;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 申请锁定库存数量 */
    @Excel(name = "申请锁定库存数量")
    private Integer stockLockNum;

    /** 实际更改库存数量 */
    @Excel(name = "实际更改库存数量")
    private Integer stockApplyNum;

    /** 申请锁库存的销售方ID */
    @Excel(name = "销售方ID")
    private String sellerId;
    /**
     * 销售方名称
     */
    @Excel(name = "销售方名称")
    private String sellerName;

    /** 锁定库存状态 0-已取消 1-锁定中 2-已完成 */
    @Excel(name = "锁定库存状态 0-已取消 1-锁定中 2-已完成")
    private Integer lockStatus;

}
