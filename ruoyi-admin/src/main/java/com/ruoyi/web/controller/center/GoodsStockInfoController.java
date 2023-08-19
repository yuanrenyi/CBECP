package com.ruoyi.web.controller.center;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.service.IUserCompanyService;
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
import com.ruoyi.system.domain.GoodsStockInfo;
import com.ruoyi.system.service.IGoodsStockInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品锁库存Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/goods/stock")
public class GoodsStockInfoController extends BaseController
{
    @Autowired
    private IGoodsStockInfoService goodsStockInfoService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 查询商品锁库存列表
     */
    @PreAuthorize("@ss.hasPermi('goods:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsStockInfo goodsStockInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            goodsStockInfo.setSellerId(userCompany.getCompanyId());
        }
        startPage();
        List<GoodsStockInfo> list = goodsStockInfoService.selectGoodsStockInfoList(goodsStockInfo);
        return getDataTable(list);
    }

    /**
     * 导出商品锁库存列表
     */
    @PreAuthorize("@ss.hasPermi('goods:stock:export')")
    @Log(title = "商品锁库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsStockInfo goodsStockInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            goodsStockInfo.setSellerId(userCompany.getCompanyId());
        }
        List<GoodsStockInfo> list = goodsStockInfoService.selectGoodsStockInfoList(goodsStockInfo);
        ExcelUtil<GoodsStockInfo> util = new ExcelUtil<GoodsStockInfo>(GoodsStockInfo.class);
        util.exportExcel(response, list, "商品锁库存数据");
    }

    /**
     * 获取商品锁库存详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsStockInfoService.selectGoodsStockInfoById(id));
    }

    /**
     * 新增商品锁库存
     */
    @PreAuthorize("@ss.hasPermi('goods:stock:add')")
    @Log(title = "商品锁库存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsStockInfo goodsStockInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            goodsStockInfo.setSellerId(userCompany.getCompanyId());
        }
        goodsStockInfo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(goodsStockInfoService.insertGoodsStockInfo(goodsStockInfo));
    }

    /**
     * 修改商品锁库存
     */
    @PreAuthorize("@ss.hasPermi('goods:stock:edit')")
    @Log(title = "商品锁库存", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsStockInfo goodsStockInfo)
    {
        return toAjax(goodsStockInfoService.updateGoodsStockInfo(goodsStockInfo));
    }

    /**
     * 删除商品锁库存
     */
    @PreAuthorize("@ss.hasPermi('goods:stock:remove')")
    @Log(title = "商品锁库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsStockInfoService.deleteGoodsStockInfoByIds(ids));
    }
}
