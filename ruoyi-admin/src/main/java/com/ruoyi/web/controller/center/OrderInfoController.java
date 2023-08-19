package com.ruoyi.web.controller.center;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.view.PoiBaseView;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.sql.Order;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.MyExcelUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.PushOrderReqVo;
import com.ruoyi.system.service.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.vo.OrderInfoVo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 订单信息Controller
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@RestController
@RequestMapping("/system/order")
public class OrderInfoController extends BaseController
{
    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IUserCompanyService userCompanyService;

    @Autowired
    private IOrderAbnormalService orderAbnormalService;

    @Autowired
    private IOrderAbnormalFollowupService orderAbnormalFollowupService;

    @Autowired
    private ISellerInfoService sellerInfoService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('order:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderInfo orderInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 3) {
            orderInfo.setSupplierId(userCompany.getCompanyId());
        }
        if(userCompany.getCompanyType() == 2) {
            orderInfo.setSellerId(userCompany.getCompanyId());

            SellerInfo agent = sellerInfoService.selectSellerInfoById(userCompany.getCompanyId());
            if(ObjectUtil.isNotNull(agent) && agent.getIfAgent() == 1) {
                List<SellerInfo> childSellerList = sellerInfoService.getAgentSellerList(userCompany.getCompanyId());
                List<String> sellerIdList = childSellerList.stream().map(SellerInfo::getId).collect(Collectors.toList());
                sellerIdList.add(agent.getId());
                orderInfo.setSellerIds(sellerIdList);
            }
        }
        startPage();
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('order:info:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderInfo orderInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 3) {
            orderInfo.setSupplierId(userCompany.getCompanyId());
        }
        if(userCompany.getCompanyType() == 2) {
            orderInfo.setSellerId(userCompany.getCompanyId());

            SellerInfo agent = sellerInfoService.selectSellerInfoById(userCompany.getCompanyId());
            if(agent.getIfAgent() == 1) {
                List<SellerInfo> childSellerList = sellerInfoService.getAgentSellerList(userCompany.getCompanyId());
                List<String> sellerIdList = childSellerList.stream().map(SellerInfo::getId).collect(Collectors.toList());
                sellerIdList.add(agent.getId());
                orderInfo.setSellerIds(sellerIdList);
            }

        }
        List<OrderInfoVo> list = orderInfoService.selectOrderInfoDetailList(orderInfo);
//        ExcelUtil<OrderInfoVo> util = new ExcelUtil<OrderInfoVo>(OrderInfoVo.class);
//        util.exportExcel(response, list, "订单信息数据");
        try {
            MyExcelUtils.exportExcel(list, "订单信息", "订单信息数据" , OrderInfoVo.class,"test" + DateUtils.dateTime(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        return success(orderInfoService.selectOrderInfoDetailByOrderId(id, userCompany));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderInfo orderInfo)
    {
        return toAjax(orderInfoService.insertOrderInfo(orderInfo));
    }

    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderInfo orderInfo)
    {
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('order:info:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderInfoService.deleteOrderInfoByIds(ids));
    }

    /**
     * 标记异常订单
     * @param orderAbnormal
     * @return
     */
    @PreAuthorize("@ss.hasPermi('order:abnormal:add')")
    @PostMapping("/abnormal/add")
    @Log(title = "异常订单信息", businessType = BusinessType.INSERT)
    public AjaxResult abnormalAdd(@RequestBody OrderAbnormal orderAbnormal) {
        return toAjax(orderAbnormalService.insertOrderAbnormal(orderAbnormal));
    }


    /**
     * 保存异常订单跟进记录
     * @param orderAbnormalFollowup
     * @return
     */
    @PreAuthorize("@ss.hasPermi('order:abnormal:followupadd')")
    @PostMapping("/abnormal/followup/add")
    @Log(title = "异常订单信息跟进", businessType = BusinessType.INSERT)
    public AjaxResult abnormalFollowupAdd(@RequestBody OrderAbnormalFollowup orderAbnormalFollowup) {
        orderAbnormalFollowup.setCreateBy(String.valueOf(getUserId()));
        return toAjax(orderAbnormalFollowupService.insertOrderAbnormalFollowup(orderAbnormalFollowup));
    }

    /**
     * 查询异常订单跟进记录
     * @param orderAbnormalFollowup
     * @return
     */
    @PreAuthorize("@ss.hasPermi('order:abnormal:followuplist')")
    @PostMapping("/abnormal/followup/list")
    public AjaxResult abnormalFollowupList(@RequestBody OrderAbnormalFollowup orderAbnormalFollowup) {
        if(Objects.isNull(orderAbnormalFollowup.getOrderId())) {
            return AjaxResult.error("订单ID有误");
        }
        if(getUserId() != 1L) {
            orderAbnormalFollowup.setCreateBy(String.valueOf(getUserId()));
        }
        return AjaxResult.success(orderAbnormalFollowupService.selectOrderAbnormalFollowupList(orderAbnormalFollowup));
    }

    /**
     * 撤销异常订单标记
     */
    @PreAuthorize("@ss.hasPermi('order:abnormal:rollback')")
    @Log(title = "撤销异常订单标记", businessType = BusinessType.UPDATE)
    @PostMapping("/abnormal/rollback")
    public AjaxResult rollbackAbnormal(@RequestBody OrderInfo orderInfo)
    {
        if(Objects.isNull(orderInfo.getId())) {
            return AjaxResult.error("请求信息有误");
        }
        return toAjax(orderInfoService.rollbackAbnormal(orderInfo.getId()));
    }

    @PreAuthorize("@ss.hasPermi('order:after:list')")
    @GetMapping("/aftermarket/list")
    public TableDataInfo afterList(OrderInfo orderInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 3) {
            orderInfo.setSupplierId(userCompany.getCompanyId());
        }
        if(userCompany.getCompanyType() == 2) {
            orderInfo.setSellerId(userCompany.getCompanyId());
        }
        startPage();
        List<OrderInfo> list = orderInfoService.selectAfterOrderInfoList(orderInfo);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('order:after:update')")
    @Log(title = "更新订单售后状态", businessType = BusinessType.UPDATE)
    @PostMapping("/aftermarket/update")
    public AjaxResult updateAfterOrder(OrderInfo orderInfo) {
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

    /**
     * 批量导入订单信息
     * @return
     */
    @PreAuthorize("@ss.hasPermi('order:info:upload')")
    @Log(title = "订单导入", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult orderUpload(@RequestParam("orderFile") MultipartFile orderFile, HttpServletResponse response) {
        logger.info(orderFile.getOriginalFilename());
        String msg = "";
        try {
            msg = orderInfoService.uploadOrderInfo(orderFile, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(msg);
    }

    @PreAuthorize("@ss.hasPermi('order:after:followup')")
    @Log(title = "更新订单售后状态", businessType = BusinessType.INSERT)
    @PostMapping("/aftermarket/followup/add")
    public AjaxResult orderAftermarketFollowupAdd(@RequestBody OrderAftermarketFollowup followup) {
        if(Objects.isNull(followup.getOrderId())) {
            return AjaxResult.error("订单ID有误");
        }
        followup.setCreateBy(getUserId());
        followup.setCreateTime(new Date());
        orderInfoService.saveAftermarketFollowup(followup);
        return AjaxResult.success();
    }

    /**
     * 查询订单售后状态更新记录
     * @param followup
     * @return
     */
    @PostMapping("/aftermarket/followup/list")
    public AjaxResult orderAftermarketFollowupList(@RequestBody OrderAftermarketFollowup followup) {
        if(Objects.isNull(followup.getOrderId())) {
            return AjaxResult.error("订单ID有误");
        }

        if(getUserId() != 1L) {
            followup.setCreateBy(getUserId());
        }
        List<OrderAftermarketFollowup> list = orderInfoService.queryOrderAftermarketStatusFollowup(followup);
        logger.info("list={}", JSON.toJSONString(list));
        return AjaxResult.success(list);
    }

    /**
     * 推送订单
     * @param reqVo
     * @return
     */
    @PostMapping("/push")
    @Log(title = "推送订单", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('order:info:push')")
    public AjaxResult pushOrder(@RequestBody PushOrderReqVo reqVo) {
        return orderInfoService.pushOrder(reqVo, getUsername());
    }

    /**
     * 根据订单ID获取订单商品数据
     * @param orderId
     * @return
     */
    @GetMapping("/goods/list/{orderId}")
    public AjaxResult orderGoodsList(@PathVariable Long orderId) {
        return AjaxResult.success(orderInfoService.getOrderGoodsList(orderId));
    }

}
