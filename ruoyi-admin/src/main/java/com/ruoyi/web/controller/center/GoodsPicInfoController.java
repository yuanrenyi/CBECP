package com.ruoyi.web.controller.center;

import java.util.List;
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
import com.ruoyi.system.domain.GoodsPicInfo;
import com.ruoyi.system.service.IGoodsPicInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品图片Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/goods/pic")
public class GoodsPicInfoController extends BaseController
{
    @Autowired
    private IGoodsPicInfoService goodsPicInfoService;

    /**
     * 查询商品图片列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GoodsPicInfo goodsPicInfo)
    {
        startPage();
        List<GoodsPicInfo> list = goodsPicInfoService.selectGoodsPicInfoList(goodsPicInfo);
        return getDataTable(list);
    }

    /**
     * 导出商品图片列表
     */
    @Log(title = "商品图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsPicInfo goodsPicInfo)
    {
        List<GoodsPicInfo> list = goodsPicInfoService.selectGoodsPicInfoList(goodsPicInfo);
        ExcelUtil<GoodsPicInfo> util = new ExcelUtil<GoodsPicInfo>(GoodsPicInfo.class);
        util.exportExcel(response, list, "商品图片数据");
    }

    /**
     * 获取商品图片详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsPicInfoService.selectGoodsPicInfoById(id));
    }

    /**
     * 新增商品图片
     */
    @PostMapping
    public AjaxResult add(@RequestBody GoodsPicInfo goodsPicInfo)
    {
        if(StringUtils.isEmpty(goodsPicInfo.getPlatBarcode()) && StringUtils.isEmpty(goodsPicInfo.getGoodsId())) {
            return AjaxResult.error("请输入商品条形码后再上传图片");
        }
        goodsPicInfo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(goodsPicInfoService.insertGoodsPicInfo(goodsPicInfo));
    }

    /**
     * 修改商品图片
     */
    @Log(title = "商品图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsPicInfo goodsPicInfo)
    {
        goodsPicInfo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(goodsPicInfoService.updateGoodsPicInfo(goodsPicInfo));
    }

    /**
     * 删除商品图片
     */
    @Log(title = "商品图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsPicInfoService.deleteGoodsPicInfoByIds(ids));
    }
}
