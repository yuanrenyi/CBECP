package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 user_company
 *
 * @author ruoyi
 * @date 2023-01-04
 */
public class UserCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 用户所属公司类型 1-中央仓 2-销售方 3-供应商 */
    @Excel(name = "用户所属公司类型 1-中央仓 2-销售方 3-供应商")
    private Integer companyType;

    /** 用户所属公司ID */
    @Excel(name = "用户所属公司ID")
    private String companyId;

    /** 用户所属公司名称 */
    @Excel(name = "用户所属公司名称")
    private String companyName;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setCompanyType(Integer companyType)
    {
        this.companyType = companyType;
    }

    public Integer getCompanyType()
    {
        return companyType;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("companyType", getCompanyType())
                .append("companyId", getCompanyId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("companyName", getCompanyName())
                .toString();
    }
}
