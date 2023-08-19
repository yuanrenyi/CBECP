package com.ruoyi.web.controller.center;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.CatalogInfo;
import com.ruoyi.system.service.ICatalogInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品分类信息Controller
 *
 * @author ruoyi
 * @date 2023-04-13
 */
@RestController
@RequestMapping("/catalog/info")
public class CatalogInfoController extends BaseController
{
    @Autowired
    private ICatalogInfoService catalogInfoService;

    /**
     * 查询商品分类信息列表
     */
    @GetMapping("/list")
    public AjaxResult list(CatalogInfo catalogInfo)
    {
        List<CatalogInfo> list = catalogInfoService.selectCatalogInfoList(catalogInfo);
        return AjaxResult.success(list);
    }

    /**
     * 查询所有商品分类(包含递归子集的完整信息)
     */
    @GetMapping("/list/all")
    public AjaxResult listAll(CatalogInfo catalogInfo)
    {
        List<CatalogInfo> list = catalogInfoService.getCatalogListAll(catalogInfo);
        return AjaxResult.success(list);
    }


    /**
     * 导出商品分类信息列表
     */
    @PreAuthorize("@ss.hasPermi('catalog:info:export')")
    @Log(title = "商品分类信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CatalogInfo catalogInfo)
    {
        List<CatalogInfo> list = catalogInfoService.selectCatalogInfoList(catalogInfo);
        ExcelUtil<CatalogInfo> util = new ExcelUtil<CatalogInfo>(CatalogInfo.class);
        util.exportExcel(response, list, "商品分类信息数据");
    }

    /**
     * 获取商品分类信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(catalogInfoService.selectCatalogInfoById(id));
    }

    /**
     * 新增商品分类信息
     */
    @PreAuthorize("@ss.hasPermi('catalog:info:add')")
    @Log(title = "商品分类信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CatalogInfo catalogInfo)
    {
        if(StringUtils.isBlank(catalogInfo.getCatalogName())) {
            return AjaxResult.error("请填写分类名称");
        }
        if(Objects.isNull(catalogInfo.getParentId())) {
            return AjaxResult.error("请选择上级分类");
        }
        catalogInfo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(catalogInfoService.insertCatalogInfo(catalogInfo));
    }

    /**
     * 修改商品分类信息
     */
    @PreAuthorize("@ss.hasPermi('catalog:info:edit')")
    @Log(title = "商品分类信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CatalogInfo catalogInfo)
    {
        if(StringUtils.isBlank(catalogInfo.getCatalogName())) {
            return AjaxResult.error("请填写分类名称");
        }
        if(Objects.isNull(catalogInfo.getParentId())) {
            return AjaxResult.error("请选择上级分类");
        }
        catalogInfo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(catalogInfoService.updateCatalogInfo(catalogInfo));
    }

    /**
     * 删除商品分类信息
     */
    @PreAuthorize("@ss.hasPermi('catalog:info:remove')")
    @Log(title = "商品分类信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(catalogInfoService.deleteCatalogInfoByIds(ids));
    }
}

