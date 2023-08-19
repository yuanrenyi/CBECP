package com.ruoyi.system.domain;

import lombok.Data;

import java.util.List;

/**
 * 省份信息
 */
@Data
public class Province {
    private Integer provinceID;

    private String province;

    private List<City> cityList;
}
