package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Area;
import com.ruoyi.system.domain.City;
import com.ruoyi.system.domain.Province;
import com.ruoyi.system.mapper.CNInfoMapper;
import com.ruoyi.system.service.ICNInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CNInfoServiceImpl implements ICNInfoService {
    @Autowired
    private CNInfoMapper cnInfoMapper;

    @Override
    public List<Province> getAllProvince() {
        return cnInfoMapper.getAllProvince();
    }

    @Override
    public List<City> getCityByProvinceID(Integer provinceID) {
        return cnInfoMapper.getCityByProvinceID(provinceID);
    }

    @Override
    public List<Area> getAreaByCityID(Integer cityID) {
        return cnInfoMapper.getAreaByCityID(cityID);
    }
}
