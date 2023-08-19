package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售方信息对象 seller_info
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
@ToString
public class SellerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售方ID */
    private String id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String compName;

    /** 项目负责人 */
    @Excel(name = "项目负责人")
    private String pmName;

    /** 负责人电话 */
    @Excel(name = "负责人电话")
    private String pmPhone;

    /** 微信群 */
    @Excel(name = "微信群")
    private String wechatGroup;

    /** 省 */
    @Excel(name = "省")
    private String compProvince;

    /** 市 */
    @Excel(name = "市")
    private String compCity;

    /**
     * 省名称
     */
    private String compProvinceName;

    /**
     * 市名称
     */
    private String compCityName;

    /** 企业规模 */
    @Excel(name = "企业规模")
    private String compScale;

    /** 是否已开会议 0-未开 1-已开 */
    @Excel(name = "是否已开会议 0-未开 1-已开")
    private Integer ifMeeting;

    /** 会议时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会议时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date meetingTime;

    /** 培训人 */
    @Excel(name = "培训人")
    private String trainer;

    /** 人员数量 */
    @Excel(name = "人员数量")
    private Integer trainNum;

    /** 是否有微信小程序 0-无 1-有 */
    @Excel(name = "是否有微信小程序 0-无 1-有")
    private Integer ifWechatMiniapp;

    /** 合作模式 */
    @Excel(name = "合作模式")
    private String cooperationMode;

    /** 会议跟进情况 */
    @Excel(name = "会议跟进情况")
    private String meetingCondition;

    /** 合同进度 */
    @Excel(name = "合同进度")
    private String contractProgress;

    /** 款项时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "款项时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;

    /**
     * 用户编码
     */
    @Excel(name = "用户编码")
    private String userCode;

    /**
     * 海关备案名称
     */
    @Excel(name = "海关备案名称")
    private String enterpriseName;

    /**
     * 海关备案编码
     */
    @Excel(name = "海关备案编码")
    private String customsCode;

    /**
     * 渠道编码
     */
    @Excel(name = "渠道编码")
    private String cnCode;

    /**
     * 179公告对接地址
     */
    @Excel(name = "179公告对接地址")
    private String encryptUrl;

    /**
     * 系统ID，MallSystem表ID
     */
    @Excel(name = "系统ID，MallSystem表ID")
    private Integer msid;

    /**
     * 微信二级子商户号
     */
    @Excel(name = "微信二级子商户号")
    private String wxmchid;

    /**
     * 微信清关关区编码
     */
    @Excel(name = "微信清关关区编码")
    private String customName;

    /**
     * 是否为代理商 0-否  1-是
     */
    private Integer ifAgent;

    /**
     * 是否为代理商
     */
    @Excel(name = "是否代理商")
    private String ifAgentStr;

    public String getIfAgentStr() {
        if(Objects.isNull(ifAgent)) {
            return "";
        }
        return ifAgent == 0 ? "否":"是";
    }

    @Excel(name = "价格比例（%）")
    private BigDecimal proportion;

    /**
     * 上级代理商ID
     */
    private String parentAgentId;

    /**
     * 上级代理商名称
     */
    @Excel(name = "上级代理商")
    private String parentAgentName;
}
