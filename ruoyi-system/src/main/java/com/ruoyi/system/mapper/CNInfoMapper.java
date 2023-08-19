package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Area;
import com.ruoyi.system.domain.City;
import com.ruoyi.system.domain.Province;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository("CNInfoMapper")
public interface CNInfoMapper {
    /**
     * 获取所有省份信息
     * @return
     */
    List<Province> getAllProvince();

    List<City> getAllCity();

    List<Area> getAllArea();

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
