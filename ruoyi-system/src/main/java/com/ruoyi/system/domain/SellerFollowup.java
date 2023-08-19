package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售方跟进记录对象 seller_followup
 *
 * @author ruoyi
 * @date 2023-01-17
 */
@Data
public class SellerFollowup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 销售方ID */
    @Excel(name = "销售方ID")
    private String sellerId;

    /** 销售方名称 */
    @Excel(name = "销售方名称")
    private String sellerName;

    /** 跟进时间 */
    @Excel(name = "跟进时间")
    private String followupTime;

    /** 跟进节点 */
    @Excel(name = "跟进节点")
    private String followupItem;

    /** 跟进说明 */
    @Excel(name = "跟进说明")
    private String followupDesc;

    /**
     * 跟进人姓名
     */
    @Excel(name = "跟进人姓名")
    private String followupName;

}
