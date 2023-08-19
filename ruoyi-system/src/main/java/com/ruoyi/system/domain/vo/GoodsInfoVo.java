package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.GoodsInfo;
import com.ruoyi.system.domain.GoodsPicInfo;
import lombok.Data;

import java.util.List;

@Data
public class GoodsInfoVo extends GoodsInfo {
    /**
     * 已上架的销售方名称
     */
    private List<String> onplatSeller;

    /**
     * 图片信息
     */
    private List<GoodsPicInfo> picInfo;
}
