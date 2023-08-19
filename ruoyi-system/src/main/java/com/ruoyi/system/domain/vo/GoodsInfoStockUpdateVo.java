package com.ruoyi.system.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsInfoStockUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号")
    private String seqNo;

    @Excel(name = "商品编码")
    private String goodsCode;

    @Excel(name = "可用库存")
    private Integer stockNum;

}
