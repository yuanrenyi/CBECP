package com.ruoyi.web.controller.center;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.service.ISysDictDataService;
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
import com.ruoyi.system.domain.SupplierStorehouseInfo;
import com.ruoyi.system.service.ISupplierStorehouseInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商仓库信息Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/supplier/storehouse")
public class SupplierStorehouseInfoController extends BaseController
{
    @Autowired
    private ISupplierStorehouseInfoService supplierStorehouseInfoService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 查询供应商仓库信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SupplierStorehouseInfo supplierStorehouseInfo)
    {

        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 3) {
            supplierStorehouseInfo.setSupplierId(userCompany.getCompanyId());
        }

        if(getUserId() != 1L) {
            supplierStorehouseInfo.setCreateBy(String.valueOf(getUserId()));
        }

        startPage();
        List<SupplierStorehouseInfo> list = supplierStorehouseInfoService.selectSupplierStorehouseInfoList(supplierStorehouseInfo);
        return getDataTable(list);
    }

    /**
     * 查询供应商仓库信息列表
     */
    @GetMapping("/list/all")
    public TableDataInfo listAll(SupplierStorehouseInfo supplierStorehouseInfo)
    {
        startPage();
        List<SupplierStorehouseInfo> list = supplierStorehouseInfoService.selectSupplierStorehouseInfoList(supplierStorehouseInfo);
        return getDataTable(list);
    }

    /**
     * 导出供应商仓库信息列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:storehouse:export')")
    @Log(title = "供应商仓库信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierStorehouseInfo supplierStorehouseInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            supplierStorehouseInfo.setSupplierId(userCompany.getCompanyId());
        }
        if(getUserId() != 1L) {
            supplierStorehouseInfo.setCreateBy(String.valueOf(getUserId()));
        }
        List<SupplierStorehouseInfo> list = supplierStorehouseInfoService.selectSupplierStorehouseInfoExportList(supplierStorehouseInfo);
        ExcelUtil<SupplierStorehouseInfo> util = new ExcelUtil<SupplierStorehouseInfo>(SupplierStorehouseInfo.class);
        util.exportExcel(response, list, "供应商仓库信息数据");
    }

    /**
     * 获取供应商仓库信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:storehouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierStorehouseInfoService.selectSupplierStorehouseInfoById(id));
    }

    /**
     * 新增供应商仓库信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:storehouse:add')")
    @Log(title = "供应商仓库信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierStorehouseInfo supplierStorehouseInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1 && StringUtils.isEmpty(supplierStorehouseInfo.getSupplierId())) {
            supplierStorehouseInfo.setSupplierId(userCompany.getCompanyId());
        }
        supplierStorehouseInfo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(supplierStorehouseInfoService.insertSupplierStorehouseInfo(supplierStorehouseInfo));
    }

    /**
     * 修改供应商仓库信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:storehouse:edit')")
    @Log(title = "供应商仓库信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierStorehouseInfo supplierStorehouseInfo)
    {
        supplierStorehouseInfo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(supplierStorehouseInfoService.updateSupplierStorehouseInfo(supplierStorehouseInfo));
    }

    /**
     * 删除供应商仓库信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:storehouse:remove')")
    @Log(title = "供应商仓库信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierStorehouseInfoService.deleteSupplierStorehouseInfoByIds(ids));
    }
}
