package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class CatalogInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增ID */
    @Excel(name = "分类ID")
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String catalogName;

    /** 分类等级 */
    @Excel(name = "分类等级")
    private Integer catalogLevel;

    /** 上级分类id */
    @Excel(name = "上级分类id")
    private Long parentId;

    /** 删除标志 0-未删除 1-已删除 */
    private Integer delFlag;

    /**
     * 分类图标
     */
    private String catalogIcon;

    /**
     * 序号
     */
    private Integer sortNo;

    /**
     * 下级分类
     */
    private List<CatalogInfo> children;


}
