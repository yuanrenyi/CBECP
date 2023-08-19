package com.ruoyi.system.service;

import com.ruoyi.system.domain.Area;
import com.ruoyi.system.domain.City;
import com.ruoyi.system.domain.Province;

import java.util.List;

public interface ICNInfoService {
    /**
     * 获取所有省份信息
     * @return
     */
    List<Province> getAllProvince();

    /**
     * 根据省ID获取市列表
     * @param provinceID
     * @return
     */
    List<City> getCityByProvinceID(Integer provinceID);

    /**
     * 根据市ID获取区县列表
     * @param cityID
     * @return
     */
    List<Area> getAreaByCityID(Integer cityID);
}
