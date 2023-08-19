package com.ruoyi.system.service;

import com.ruoyi.system.domain.GoodsPriceInfo;
import com.ruoyi.system.domain.vo.*;

import java.util.List;

public interface IDataTableService {
    /**
     * 商品动销查询
     * @param reqVo
     * @return
     */
    List<GoodsSaleVo> getGoodsSaleVoList(DataTableReqVo reqVo);

    /**
     * 商品价格7天变动记录
     * @param reqVo
     * @return
     */
    List<GoodsPriceInfo> goodsPriceInfo7Days(GoodsPriceInfoReqVo reqVo);

    /**
     * 数据报表
     * @param reqVo
     * @return
     */
    DataTableRespVo dataTable(DataTableReqVo reqVo);

    /**
     * 销售销售金额占比分布
     * @return
     */
    List<SellerSalePercent> getSellerSalePercent();

    /**
     * 订单量排行
     * @return
     */
    List<OrderCountRank> getOrderCountRank();

    /**
     * 销售额排行
     * @return
     */
    List<AmountCountRank> getAmountCountRank();

}
