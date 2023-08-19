package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品改价记录
 */
@Data
public class GoodsPriceInfo {
    private Integer id;

    private String goodsId;

    private String goodsName;

    private BigDecimal beforePrice;

    private BigDecimal afterPrice;

    private Long optUserId;

    private String optUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;
}
