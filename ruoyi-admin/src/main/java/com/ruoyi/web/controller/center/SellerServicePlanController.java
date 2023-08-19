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
import com.ruoyi.system.domain.SellerServicePlan;
import com.ruoyi.system.service.ISellerServicePlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售方服务方案Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/service/plan")
public class SellerServicePlanController extends BaseController
{
    @Autowired
    private ISellerServicePlanService sellerServicePlanService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 查询销售方服务方案列表
     */
    @PreAuthorize("@ss.hasPermi('service:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SellerServicePlan sellerServicePlan)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            sellerServicePlan.setCompId(userCompany.getCompanyId());
        }
        startPage();
        List<SellerServicePlan> list = sellerServicePlanService.selectSellerServicePlanList(sellerServicePlan);
        return getDataTable(list);
    }

    /**
     * 导出销售方服务方案列表
     */
    @PreAuthorize("@ss.hasPermi('system:plan:export')")
    @Log(title = "销售方服务方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SellerServicePlan sellerServicePlan)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1) {
            sellerServicePlan.setCompId(userCompany.getCompanyId());
        }
        List<SellerServicePlan> list = sellerServicePlanService.selectSellerServicePlanList(sellerServicePlan);
        ExcelUtil<SellerServicePlan> util = new ExcelUtil<SellerServicePlan>(SellerServicePlan.class);
        util.exportExcel(response, list, "销售方服务方案数据");
    }

    /**
     * 获取销售方服务方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('service:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sellerServicePlanService.selectSellerServicePlanById(id));
    }

    /**
     * 新增销售方服务方案
     */
    @PreAuthorize("@ss.hasPermi('service:plan:add')")
    @Log(title = "销售方服务方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SellerServicePlan sellerServicePlan)
    {
        sellerServicePlan.setCreateBy(String.valueOf(getUserId()));
        return toAjax(sellerServicePlanService.insertSellerServicePlan(sellerServicePlan));
    }

    /**
     * 修改销售方服务方案
     */
    @PreAuthorize("@ss.hasPermi('service:plan:edit')")
    @Log(title = "销售方服务方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SellerServicePlan sellerServicePlan)
    {
        return toAjax(sellerServicePlanService.updateSellerServicePlan(sellerServicePlan));
    }

    /**
     * 删除销售方服务方案
     */
    @PreAuthorize("@ss.hasPermi('service:plan:remove')")
    @Log(title = "销售方服务方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sellerServicePlanService.deleteSellerServicePlanByIds(ids));
    }
}
