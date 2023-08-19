package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售方上架商品对象 seller_goods_shelf
 * 
 * @author ruoyi
 * @date 2023-02-10
 */
@Data
public class SellerGoodsShelf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增ID */
    private Long id;

    /** 销售方id */
    @Excel(name = "销售方id")
    private String sellerId;

    /** 销售方名称 */
    @Excel(name = "销售方名称")
    private String sellerName;

    /** 上架商品id */
    @Excel(name = "上架商品id")
    private String goodsId;

}
