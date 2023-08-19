package com.ruoyi.web.controller.center;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SellerFollowup;
import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.domain.vo.SellerInfoVo;
import com.ruoyi.system.service.ISellerFollowupService;
import com.ruoyi.system.service.IUserCompanyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SellerInfo;
import com.ruoyi.system.service.ISellerInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售方信息Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/seller")
public class SellerInfoController extends BaseController
{
    @Autowired
    private ISellerInfoService sellerInfoService;

    @Autowired
    private ISellerFollowupService sellerFollowupService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 查询销售方信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SellerInfo sellerInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 2) {
            sellerInfo.setParentAgentId(userCompany.getCompanyId());
        }
        startPage();
        List<SellerInfo> list = sellerInfoService.selectSellerInfoList(sellerInfo);
        return getDataTable(list);
    }

    /**
     * 查询销售方信息列表
     */
    @GetMapping("/agent/list")
    public TableDataInfo list(String compName)
    {
        startPage();
        List<SellerInfo> list = sellerInfoService.selectAgentInfoList(compName);
        return getDataTable(list);
    }

    /**
     * 导出销售方信息列表
     */
    @PreAuthorize("@ss.hasPermi('seller:info:export')")
    @Log(title = "销售方信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SellerInfo sellerInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 2) {
            sellerInfo.setParentAgentId(userCompany.getCompanyId());
        }
        List<SellerInfo> list = sellerInfoService.selectSellerInfoList(sellerInfo);
        ExcelUtil<SellerInfo> util = new ExcelUtil<SellerInfo>(SellerInfo.class);
        util.exportExcel(response, list, "销售方信息数据");
    }

    /**
     * 获取销售方信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('seller:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(sellerInfoService.selectSellerInfoById(id));
    }

    /**
     * 新增销售方信息
     */
    @PreAuthorize("@ss.hasPermi('seller:info:add')")
    @Log(title = "销售方信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SellerInfoVo sellerInfoVo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        sellerInfoVo.setCreateBy(String.valueOf(getUserId()));
        if(StringUtils.isNotEmpty(sellerInfoVo.getParentAgentId())) {
            SellerInfo agent = sellerInfoService.selectSellerInfoById(sellerInfoVo.getParentAgentId());
            if(ObjectUtil.isNotNull(agent)) {
                sellerInfoVo.setParentAgentName(agent.getCompName());
            }
        } else {
            sellerInfoVo.setParentAgentId(userCompany.getCompanyId());
            sellerInfoVo.setParentAgentName(userCompany.getCompanyName());
        }
        return toAjax(sellerInfoService.insertSellerInfo(sellerInfoVo));
    }

    /**
     * 修改销售方信息
     */
    @PreAuthorize("@ss.hasPermi('seller:info:edit')")
    @Log(title = "销售方信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SellerInfoVo sellerInfoVo)
    {
        sellerInfoVo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(sellerInfoService.updateSellerInfo(sellerInfoVo));
    }

    /**
     * 删除销售方信息
     */
    @PreAuthorize("@ss.hasPermi('seller:info:remove')")
    @Log(title = "销售方信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(sellerInfoService.deleteSellerInfoByIds(ids));
    }

    /**
     * 保存销售方跟进记录
     * @param sellerFollowup
     * @return
     */
    @PostMapping("/addFollowup")
    public AjaxResult addFollowup(@RequestBody SellerFollowup sellerFollowup) {
        sellerFollowup.setCreateBy(String.valueOf(getUserId()));
        return toAjax(sellerFollowupService.insertSellerFollowup(sellerFollowup));
    }

    /**
     * 查询销售方跟进记录列表
     * @param sellerFollowup
     * @return
     */
    @PostMapping("/followup/list")
    public AjaxResult follwupListBySellerId(@RequestBody SellerFollowup sellerFollowup) {

        if(!Objects.equals((long)1, getUserId())) {
            sellerFollowup.setCreateBy(String.valueOf(getUserId()));
        }

        if(StringUtils.isEmpty(sellerFollowup.getSellerId())) {
            return AjaxResult.error("销售方ID有误");
        }
        return AjaxResult.success(sellerFollowupService.selectSellerFollowupListDTO(sellerFollowup));
    }

    /**
     * 查询当前用户当日应完成的销售方跟进记录
     * @return
     */
    @GetMapping("/followup/notice")
    public AjaxResult followupNotice() {
        return AjaxResult.success(sellerFollowupService.getTodayFollowupNotice(getUserId()));
    }

    /**
     * 首页-查询所有跟进记录
     * @param type 跟进类型 1=已跟进 2=未跟进
     * @return
     */
    @GetMapping("/followup/all")
    public TableDataInfo followupAll(@RequestParam Integer type) {
        Long userId = getUserId();
        if(userId == (long)1) {
            userId = null;
        }
        List<SellerFollowup> followupList = sellerFollowupService.getFollowupAll(type, userId);
        return getDataTable(followupList);
    }

    @GetMapping("/showAddButton/flag")
    public AjaxResult currentInfo() {
        int showFlag = 0;
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 1) {
            showFlag = 1;
        }
        if(userCompany.getCompanyType() == 2) {
            SellerInfo sellerInfo = sellerInfoService.selectSellerInfoById(userCompany.getCompanyId());
           if(ObjectUtil.isNotNull(sellerInfo) && ObjectUtil.isNotNull(sellerInfo.getIfAgent()) && sellerInfo.getIfAgent() == 1) {
               showFlag = 1;
           }
        }
        return AjaxResult.success(showFlag);
    }
}
