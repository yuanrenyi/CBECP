package com.ruoyi.system.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class PushOrderReqVo {

    /**
     * 交易订单号
     */
    private List<String> outTradeNos;
}
