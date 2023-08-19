package com.ruoyi.web.controller.center;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.BondedWarehouseInfo;
import com.ruoyi.system.service.IBondedWarehouseInfoService;

/**
 * 保税仓Controller
 */
@RestController
@RequestMapping("/bw/info")
public class BondedWarehouseInfoController extends BaseController
{
    @Autowired
    private IBondedWarehouseInfoService bondedWarehouseInfoService;

    /**
     * 查询保税仓列表
     */
    @GetMapping("/list")
    public AjaxResult list(BondedWarehouseInfo bondedWarehouseInfo)
    {
        List<BondedWarehouseInfo> list = bondedWarehouseInfoService.selectBondedWarehouseInfoList(bondedWarehouseInfo);
        return AjaxResult.success(list);
    }

    /**
     * 查询保税仓列表（包含递归子集）
     */
    @GetMapping("/list/all")
    public AjaxResult listAll(BondedWarehouseInfo bondedWarehouseInfo)
    {
        List<BondedWarehouseInfo> list = bondedWarehouseInfoService.getBondedWarehouseInfoAll(bondedWarehouseInfo);
        return AjaxResult.success(list);
    }

    /**
     * 获取保税仓详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bondedWarehouseInfoService.selectBondedWarehouseInfoById(id));
    }

    /**
     * 新增保税仓
     */
    @PreAuthorize("@ss.hasPermi('bw:info:add')")
    @Log(title = "保税仓", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BondedWarehouseInfo bondedWarehouseInfo)
    {
        bondedWarehouseInfo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(bondedWarehouseInfoService.insertBondedWarehouseInfo(bondedWarehouseInfo));
    }

    /**
     * 修改保税仓
     */
    @PreAuthorize("@ss.hasPermi('bw:info:edit')")
    @Log(title = "保税仓", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BondedWarehouseInfo bondedWarehouseInfo)
    {
        bondedWarehouseInfo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(bondedWarehouseInfoService.updateBondedWarehouseInfo(bondedWarehouseInfo));
    }

    /**
     * 删除保税仓
     */
    @PreAuthorize("@ss.hasPermi('bw:info:remove')")
    @Log(title = "保税仓", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bondedWarehouseInfoService.deleteBondedWarehouseInfoByIds(ids));
    }
}
