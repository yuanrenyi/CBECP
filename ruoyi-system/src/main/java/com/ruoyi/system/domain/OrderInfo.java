package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单信息对象 order_info
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Data
@ToString
public class OrderInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 仓库ID */
    @Excel(name = "仓库ID", needMerge = true)
    private String storehouseId;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称", needMerge = true)
    private String storehouseName;

    /** 销售方ID */
    @Excel(name = "销售方ID", needMerge = true)
    private String sellerId;

    /**
     * 销售方名称
     */
    @Excel(name = "销售方名称", needMerge = true)
    private String sellerName;

    /** 供应商ID */
    @Excel(name = "供应商ID", needMerge = true)
    private String supplierId;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称", needMerge = true)
    private String supplierName;

    /** 订单类型，默认1 */
    @Excel(name = "订单类型，默认1", needMerge = true)
    private Integer orderType;

    /** 系统单号，自动生成 */
    @Excel(name = "系统单号，自动生成", needMerge = true)
    private String sorderNo;

    /** 商户订单号 */
    @Excel(name = "商户订单号", needMerge = true)
    private String declareOrderNo;

    /** 交易订单号（申报海关订单号） */
    @Excel(name = "交易订单号", needMerge = true)
    private String outTradeNo;

    /** 订单状态 */
    @Excel(name = "订单状态", needMerge = true)
    private String orderStatus;

    /** 物流公司 */
    @Excel(name = "物流公司", needMerge = true)
    private String expressName;

    /** 物流单号 */
    @Excel(name = "物流单号", needMerge = true)
    private String shipOrderNumber;

    /** 店铺名称 */
    @Excel(name = "店铺名称", needMerge = true)
    private String shopNick;

    /** 购买人注册号 */
    @Excel(name = "购买人注册号", needMerge = true)
    private String buyerRegNo;

    /** 购买人姓名 */
    @Excel(name = "购买人姓名", needMerge = true)
    private String buyerName;

    /** 购买人电话 */
    @Excel(name = "购买人电话", needMerge = true)
    private String buyerTelephone;

    /** 证件类型 1：身份证号 */
    @Excel(name = "证件类型 1：身份证号", needMerge = true)
    private Integer buyerIdType;

    /** 购买人证件号 */
    @Excel(name = "购买人证件号", needMerge = true)
    private String buyerIdNumber;

    /** 收件人昵称 */
    @Excel(name = "收件人昵称", needMerge = true)
    private String consignee;

    /** 收件人电话 */
    @Excel(name = "收件人电话", needMerge = true)
    private String consigneeTelephone;

    /** 收件人地址 */
    @Excel(name = "收件人地址", needMerge = true)
    private String consigneeAddress;

    /** 收件人邮政编码 */
    @Excel(name = "收件人邮政编码", needMerge = true)
    private String consigneeZipCode;

    /** 收件人省 */
    @Excel(name = "收件人省", needMerge = true)
    private String consigneeProvince;

    /** 收件人市 */
    @Excel(name = "收件人市", needMerge = true)
    private String consigneeCity;

    /** 收件人区 */
    @Excel(name = "收件人区", needMerge = true)
    private String consigneeDistrict;

    /** 商品总价 */
    @Excel(name = "商品总价", needMerge = true)
    private BigDecimal totalAmount;

    /** 实际支付金额 */
    @Excel(name = "实际支付金额", needMerge = true)
    private BigDecimal realTotalAmount;

    /** 综合税税费 */
    @Excel(name = "综合税税费", needMerge = true)
    private BigDecimal taxTotalAmount;

    /** 折扣/优惠费用 */
    @Excel(name = "折扣/优惠费用", needMerge = true)
    private BigDecimal discountAmount;

    /** 运费 */
    @Excel(name = "运费", needMerge = true)
    private BigDecimal freightAmount;

    /** 支付方式，01：支付宝，02：微信，10：通联 */
    @Excel(name = "支付方式，01：支付宝，02：微信，10：通联", needMerge = true)
    private String payMode;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", needMerge = true)
    private Date payTime;

    /** 支付单号/支付流水号 */
    @Excel(name = "支付单号/支付流水号", needMerge = true)
    private String gatewayOrderId;

    /** 订单新增时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单新增时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", needMerge = true)
    private Date addTime;

    /** 三方单号，推送供应商后返回的单号 */
    @Excel(name = "三方单号，推送供应商后返回的单号", needMerge = true)
    private String lgOrderCode;

    /** 回调时间，供应商回调订单状态、物流信息的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "回调时间，供应商回调订单状态、物流信息的时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", needMerge = true)
    private Date callbackTime;

    /** 增值税 */
    @Excel(name = "增值税", needMerge = true)
    private BigDecimal vat;

    /** 关税 */
    @Excel(name = "关税", needMerge = true)
    private BigDecimal customsTax;

    /** 消费税 */
    @Excel(name = "消费税", needMerge = true)
    private BigDecimal consumptionTax;

    /** 订单状态说明 */
    @Excel(name = "订单状态说明", needMerge = true)
    private String orderStatusRemark;

    /** 订单回调状态，默认0 */
    @Excel(name = "订单回调状态，默认0", needMerge = true)
    private Integer callbackMallStatus;

    /** 订单回调状态说明 */
    @Excel(name = "订单回调状态说明", needMerge = true)
    private String callbackMallStatusValue;

    /** 联盟运费 */
    @Excel(name = "联盟运费", needMerge = true)
    private BigDecimal allianceFreightAmount;

    /** 指定物流编码 */
    @Excel(name = "指定物流编码", needMerge = true)
    private String appointExpress;


    /**
     * 售后状态
     */
    @Excel(name = "售后状态", needMerge = true)
    private Integer aftermarketStatus;


    /**
     * 销售方提现状态 0-未提现 1-提现中 2-已提现
     */
    private Integer sellerWithdrawalStatus;

    /**
     * 供应商提现状态 0-未提现 1-提现中 2-已提现
     */
    private Integer supplierWithdrawalStatus;

    /**
     * 销售方可提现金额
     */
    private BigDecimal sellerWithdrawalAmount;

    /**
     * 供应商可提现金额
     */
    private BigDecimal supplierWithdrawalAmount;

    /**
     * 异常状态 0-正常1-异常
     */
    private Integer abnormalStatus;

    /**
     * 标记异常原因
     */
    private String abnormalDesc;

    /**
     * 售后状态说明
     */
    private String aftermarketStatusDesc;


    private List<String> sellerIds;

    /**
     * 推送订单状态 0-未推送 1-推送成功 2-推送失败
     */
    private Integer pushOrderStatus;

    private String addTimeBegin;

    private String addTimeEnd;

    private String payTimeBegin;

    private String payTimeEnd;


}
