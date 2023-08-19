package com.ruoyi.system.domain.vo;

import lombok.Data;

@Data
public class GoodsPriceInfoReqVo {
    /**
     * 商品id 必传
     */
    private String goodsId;

    private String beginDate;

}
