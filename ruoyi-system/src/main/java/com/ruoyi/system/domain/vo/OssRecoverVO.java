package com.ruoyi.system.domain.vo;

import lombok.Data;

@Data
public class OssRecoverVO {

    private String accessid;

    private String signature;

    private String dir;

    private String host;

    private String expire;
}
