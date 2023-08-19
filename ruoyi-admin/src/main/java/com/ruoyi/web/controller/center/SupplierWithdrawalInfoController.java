package com.ruoyi.web.controller.center;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.system.domain.SupplierWithdrawalInfo;
import com.ruoyi.system.service.ISupplierWithdrawalInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商提现记录Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/supplier/withdrawal")
public class SupplierWithdrawalInfoController extends BaseController
{
    @Autowired
    private ISupplierWithdrawalInfoService supplierWithdrawalInfoService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 查询供应商提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            supplierWithdrawalInfo.setSupplierId(userCompany.getCompanyId());
        }
        startPage();
        List<SupplierWithdrawalInfo> list = supplierWithdrawalInfoService.selectSupplierWithdrawalInfoList(supplierWithdrawalInfo);
        return getDataTable(list);
    }

    /**
     * 导出供应商提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:export')")
    @Log(title = "供应商提现记录导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            supplierWithdrawalInfo.setSupplierId(userCompany.getCompanyId());
        }
        List<SupplierWithdrawalInfo> list = supplierWithdrawalInfoService.selectSupplierWithdrawalInfoList(supplierWithdrawalInfo);
        ExcelUtil<SupplierWithdrawalInfo> util = new ExcelUtil<SupplierWithdrawalInfo>(SupplierWithdrawalInfo.class);
        util.exportExcel(response, list, "供应商提现记录数据");
    }

    /**
     * 获取供应商提现记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(supplierWithdrawalInfoService.selectSupplierWithdrawalInfoById(id));
    }

    /**
     * 新增供应商提现记录
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:add')")
    @Log(title = "供应商提现记录新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1 && StringUtils.isEmpty(supplierWithdrawalInfo.getSupplierId())) {
            supplierWithdrawalInfo.setSupplierId(userCompany.getCompanyId());
        }
        supplierWithdrawalInfo.setCreateBy(String.valueOf(getUserId()));
        supplierWithdrawalInfo.setAuditStatus(0);
        return toAjax(supplierWithdrawalInfoService.insertSupplierWithdrawalInfo(supplierWithdrawalInfo));
    }

    /**
     * 修改供应商提现记录
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:edit')")
    @Log(title = "供应商提现记录删除", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        supplierWithdrawalInfo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(supplierWithdrawalInfoService.updateSupplierWithdrawalInfo(supplierWithdrawalInfo));
    }

    /**
     * 删除供应商提现记录
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:remove')")
    @Log(title = "供应商提现记录批量删除", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(supplierWithdrawalInfoService.deleteSupplierWithdrawalInfoByIds(ids));
    }


    /**
     * 修改供应商提现记录
     */
    @PreAuthorize("@ss.hasPermi('supplier:withdrawal:audit')")
    @Log(title = "供应商提现记录审核", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    public AjaxResult audit(@RequestBody SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        if(Objects.isNull(supplierWithdrawalInfo.getId())) {
            return AjaxResult.error("提现记录ID有误");
        }
        if(StringUtils.isBlank(supplierWithdrawalInfo.getSupplierId())) {
            return AjaxResult.error("供应商ID有误");
        }
        if(Objects.isNull(supplierWithdrawalInfo.getAuditStatus())) {
            return AjaxResult.error("审核状态有误");
        }
        supplierWithdrawalInfo.setAuditBy(getUsername());
        supplierWithdrawalInfo.setAuditTime(new Date());
        return toAjax(supplierWithdrawalInfoService.updateSupplierWithdrawalInfo(supplierWithdrawalInfo));
    }
}
