package com.ruoyi.web.controller.center;

import java.util.List;
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
import com.ruoyi.system.domain.SellerWithdrawalInfo;
import com.ruoyi.system.service.ISellerWithdrawalInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售方提现记录Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/seller/withdrawal")
public class SellerWithdrawalInfoController extends BaseController
{
    @Autowired
    private ISellerWithdrawalInfoService sellerWithdrawalInfoService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 查询销售方提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('seller:withdrawal:list')")
    @GetMapping("/list")
    public TableDataInfo list(SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            sellerWithdrawalInfo.setSellerId(userCompany.getCompanyId());
        }
        startPage();
        List<SellerWithdrawalInfo> list = sellerWithdrawalInfoService.selectSellerWithdrawalInfoList(sellerWithdrawalInfo);
        return getDataTable(list);
    }

    /**
     * 导出销售方提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('seller:withdrawal:export')")
    @Log(title = "销售方提现记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            sellerWithdrawalInfo.setSellerId(userCompany.getCompanyId());
        }
        List<SellerWithdrawalInfo> list = sellerWithdrawalInfoService.selectSellerWithdrawalInfoList(sellerWithdrawalInfo);
        ExcelUtil<SellerWithdrawalInfo> util = new ExcelUtil<SellerWithdrawalInfo>(SellerWithdrawalInfo.class);
        util.exportExcel(response, list, "销售方提现记录数据");
    }

    /**
     * 获取销售方提现记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('seller:withdrawal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sellerWithdrawalInfoService.selectSellerWithdrawalInfoById(id));
    }

    /**
     * 新增销售方提现记录
     */
    @PreAuthorize("@ss.hasPermi('seller:withdrawal:add')")
    @Log(title = "销售方提现记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1 && StringUtils.isEmpty(sellerWithdrawalInfo.getSellerId())) {
            sellerWithdrawalInfo.setSellerId(userCompany.getCompanyId());
        }
        sellerWithdrawalInfo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(sellerWithdrawalInfoService.insertSellerWithdrawalInfo(sellerWithdrawalInfo));
    }

    /**
     * 修改销售方提现记录
     */
    @PreAuthorize("@ss.hasPermi('seller:withdrawal:edit')")
    @Log(title = "销售方提现记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        sellerWithdrawalInfo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(sellerWithdrawalInfoService.updateSellerWithdrawalInfo(sellerWithdrawalInfo));
    }

    /**
     * 删除销售方提现记录
     */
    @PreAuthorize("@ss.hasPermi('seller:withdrawal:remove')")
    @Log(title = "销售方提现记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sellerWithdrawalInfoService.deleteSellerWithdrawalInfoByIds(ids));
    }
}
