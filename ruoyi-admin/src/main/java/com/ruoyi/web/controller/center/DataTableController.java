package com.ruoyi.web.controller.center;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.domain.vo.DataTableReqVo;
import com.ruoyi.system.domain.vo.GoodsPriceInfoReqVo;
import com.ruoyi.system.service.IDataTableService;
import com.ruoyi.system.service.ISellerInfoService;
import com.ruoyi.system.service.IUserCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据报表
 */
@RestController
@RequestMapping("/system/data/table")
public class DataTableController extends BaseController {

    @Autowired
    private IDataTableService dataTableService;

    @Autowired
    private IUserCompanyService userCompanyService;

    /**
     * 数据报表
     * @return
     */
    @GetMapping("/index")
    public AjaxResult dataTable() {
        return AjaxResult.success(dataTableService.dataTable(fillReqVo(new DataTableReqVo())));
    }

    /**
     * 商品动销
     * @return
     */
    @GetMapping("/goods/sale")
    public TableDataInfo goodsSale(DataTableReqVo reqVo) {
        reqVo = fillReqVo(reqVo);
        startPage();
        return getDataTable(dataTableService.getGoodsSaleVoList(reqVo));
    }

    /**
     * 商品7天内改价变动
     * @return
     */
    @GetMapping("/goods/price/7")
    public AjaxResult goodsPrice(GoodsPriceInfoReqVo reqVo) {
        return AjaxResult.success(dataTableService.goodsPriceInfo7Days(reqVo));
    }

    /**
     * 销售方销售总金额占比分布
     * @return
     */
    @GetMapping("/seller/sale/percent")
    public AjaxResult sellerSalePercent() {
        return AjaxResult.success(dataTableService.getSellerSalePercent());
    }

    /**
     * 订单量排行
     * @return
     */
    @GetMapping("/order/count/rank")
    public AjaxResult orderCountRank() {
        return AjaxResult.success(dataTableService.getOrderCountRank());
    }

    /**
     * 销售额排行
     * @return
     */
    @GetMapping("/amount/count/rank")
    public AjaxResult amountCountRank() {
        return AjaxResult.success(dataTableService.getAmountCountRank());
    }

    private DataTableReqVo fillReqVo(DataTableReqVo reqVo) {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 2) {
            reqVo.setSellerId(userCompany.getCompanyId());
        }
        if(userCompany.getCompanyType() == 3) {
            reqVo.setSupplierId(userCompany.getCompanyId());
        }
        return reqVo;
    }

}
