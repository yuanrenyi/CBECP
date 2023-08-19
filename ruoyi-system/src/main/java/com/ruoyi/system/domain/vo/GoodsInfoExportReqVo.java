package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.GoodsInfo;
import lombok.Data;

import java.util.List;

@Data
public class GoodsInfoExportReqVo extends GoodsInfo {

    /**
     * 商品id数组
     */
    private List<String> ids;
}
