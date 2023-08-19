package com.ruoyi.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.NumberFormat;

/**
 * 订单商品对象 order_item
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Data
@ToString
public class OrderItem extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 订单表ID */
    private Long orderId;

    /** 仓库ID */
    private String storehouseId;

    /** 商品表ID */
    @Excel(name = "商品表ID")
    private String goodsId;

    /** 供应商ID */
    private String supplierId;

    /** 销售方ID */
    private String sellerId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String goodsCode;

    /** 商品条码 */
    @Excel(name = "商品条码")
    private String goodsBarcode;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long quantity;

    /** 售价（单价） */
    @Excel(name = "售价", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal price;

    /** 优惠后单价 */
    @Excel(name = "优惠后单价", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal discountPrice;

    /** 增值税 */
    @Excel(name = "增值税", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal vat;

    /** 商品详情链接地址 */
    @Excel(name = "商品详情链接地址")
    private String itemLink;

    /** 对接货品ID */
    @Excel(name = "对接货品ID")
    private String itemId;

    /** 综合税 */
    @Excel(name = "综合税", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal taxTotal;

    /** 关税 */
    @Excel(name = "关税", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal customsTax;

    /** 消费税 */
    @Excel(name = "消费税", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal consumptionTax;

    /** 当前裸价 */
    @Excel(name = "当前裸价", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal barePriceNow;

    /** 对外商品编码 */
    @Excel(name = "联盟供应价", numFormat = "#.####")
    @NumberFormat(pattern = "#.####")
    private BigDecimal alliancePrice;

    /** 对外商品编码 */
    @Excel(name = "对外商品编码")
    private String openProductCode;
}
