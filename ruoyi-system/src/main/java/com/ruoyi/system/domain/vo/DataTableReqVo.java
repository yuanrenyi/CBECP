package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.GoodsInfo;
import lombok.Data;

@Data
public class DataTableReqVo extends GoodsInfo {
    private String sellerId;

    private String supplierId;

}
