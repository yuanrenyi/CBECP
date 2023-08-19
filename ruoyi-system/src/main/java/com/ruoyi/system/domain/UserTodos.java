package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 用户待办/已办事项对象 user_todos
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Data
@ToString
public class UserTodos extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 待办事项说明 */
    @Excel(name = "待办事项说明")
    private String todoThing;

    /** 待办时间 */
    @Excel(name = "待办时间")
    private String todoTime;

    /** 待办状态 0-待办 1-已办 */
    @Excel(name = "待办状态 0-待办 1-已办 2-延期 3-作废")
    private Integer todoStatus;

    /**
     * 作废原因
     */
    private String reason;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /**
     * 临期标志 0-未临期 1-临期
     */
    private Integer recentFlag;
}
