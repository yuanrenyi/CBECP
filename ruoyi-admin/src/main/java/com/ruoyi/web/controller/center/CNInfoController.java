package com.ruoyi.web.controller.center;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.ICNInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cninfo")
public class CNInfoController {

    @Autowired
    private ICNInfoService icnInfoService;

    /**
     * 获取所有省份信息（不包含下级）
     * @return
     */
    @GetMapping("/allProvince")
    public AjaxResult allProvince() {
        return AjaxResult.success(icnInfoService.getAllProvince());
    }

    /**
     * 根据省ID获取所有市
     * @param provinceID
     * @return
     */
    @GetMapping("/{provinceID}/city")
    public AjaxResult cityByProvinceID(@PathVariable("provinceID") Integer provinceID) {
        return AjaxResult.success(icnInfoService.getCityByProvinceID(provinceID));
    }

    /**
     * 根据市ID获取所有区县
     */
    @GetMapping("/{cityID}/area")
    public AjaxResult areaByCityID(@PathVariable("cityID") Integer cityID) {
        return AjaxResult.success(icnInfoService.getAreaByCityID(cityID));
    }
}
