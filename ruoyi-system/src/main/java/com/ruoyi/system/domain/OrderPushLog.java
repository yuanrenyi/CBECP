package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class OrderPushLog extends BaseEntity {

    private Integer id;

    private String reqInfo;

    private String respInfo;

}
