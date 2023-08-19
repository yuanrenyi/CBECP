package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品图片对象 goods_pic_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPicInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 商品ID */
    @Excel(name = "商品ID")
    private String goodsId;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String picUrl;

    /** 图片类型 1-主图 2-详情图 3-视频 4-缩略图 */
    @Excel(name = "图片类型 1-主图 2-详情图 3-视频 4-缩略图")
    private String picType;

    /**
     * 删除标志 1-未删除 2-已删除
     */
    private Integer delFlag;

    /**
     * 图片版本
     */
    private String picVersion;

    /**
     * 排序值
     */
    private String sortNo;

    /**
     * 平台自定义条形码
     */
    private String platBarcode;

}
