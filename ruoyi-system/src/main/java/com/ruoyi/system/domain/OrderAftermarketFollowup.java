package com.ruoyi.system.domain;

import lombok.Data;

import java.util.Date;

/**
 * 售后订单状态改变记录
 */
@Data
public class OrderAftermarketFollowup {
    private Long id;

    private Long orderId;

    private Integer aftermarketStatus;

    private String aftermarketStatusDesc;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;

    private String createName;

}
