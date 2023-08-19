package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository("DataTableMapper")
public interface DataTableMapper {
    /**
     * 商品动销查询
     * @param reqVo
     * @return
     */
    List<GoodsSaleVo> getGoodsSaleVoList(DataTableReqVo reqVo);

    /**
     * 查询总订单量
     * @param reqVo
     * @return
     */
    Long getOrderCount(@Param("req") DataTableReqVo reqVo);

    /**
     * 查询订单总金额
     * @param reqVo
     * @return
     */
    BigDecimal getOrderAmountCount(@Param("req") DataTableReqVo reqVo);

    /**
     * 最近7天的销售额汇总
     * @param reqVo
     * @return
     */
    List<SaleAmountVo> getSaleAmount7Days(@Param("req") DataTableReqVo reqVo);

    /**
     * 销售方销售额占比分布
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
