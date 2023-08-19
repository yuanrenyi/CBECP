package com.ruoyi.system.domain;

import lombok.Data;

import java.util.List;

/**
 * 市级信息
 */
@Data
public class City {
    private Integer cityID;

    private String city;

    private Integer fatherID;

    private List<Area> areaList;
}
