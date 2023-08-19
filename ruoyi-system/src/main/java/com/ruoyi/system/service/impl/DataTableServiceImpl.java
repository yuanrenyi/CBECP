package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.GoodsInfo;
import com.ruoyi.system.domain.GoodsPriceInfo;
import com.ruoyi.system.domain.SellerInfo;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.mapper.DataTableMapper;
import com.ruoyi.system.mapper.GoodsInfoMapper;
import com.ruoyi.system.mapper.GoodsPriceInfoMapper;
import com.ruoyi.system.service.IDataTableService;
import com.ruoyi.system.service.ISellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataTableServiceImpl implements IDataTableService {
    @Autowired
    private DataTableMapper dataTableMapper;

    @Autowired
    private GoodsPriceInfoMapper goodsPriceInfoMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private ISellerInfoService sellerInfoService;

    @Override
    public List<GoodsSaleVo> getGoodsSaleVoList(DataTableReqVo reqVo) {

        List<GoodsSaleVo> list = dataTableMapper.getGoodsSaleVoList(reqVo);

        BigDecimal proportion = getSellerProportion(reqVo.getSellerId());

        if(ObjectUtil.isNotNull(proportion)) {
            list.forEach(goodsSaleVo -> {
                goodsSaleVo.setPrice(goodsSaleVo.getPrice().multiply(proportion).setScale(2,4));
            });
        }
        return list;
    }

    @Override
    public List<GoodsPriceInfo> goodsPriceInfo7Days(GoodsPriceInfoReqVo reqVo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        Date end = calendar.getTime();

        calendar.add(Calendar.DATE, -7);
        Date start = calendar.getTime();

        reqVo.setBeginDate(simpleDateFormat.format(start));
        List<GoodsPriceInfo> result =  goodsPriceInfoMapper.queryGoodsPriceInfo7Days(reqVo);

        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(reqVo.getGoodsId());

        //当价格7天内无变动，则加入1条记录，7天前和今天是同一个价格
        if(Objects.isNull(result) || result.size() <= 0) {
            GoodsPriceInfo record1 = new GoodsPriceInfo();
            record1.setAfterPrice(goodsInfo.getCostPrice());
            record1.setOptTime(start);
            result.add(record1);
        }

        //默认会有今天的价格信息
        GoodsPriceInfo record2 = new GoodsPriceInfo();
        record2.setAfterPrice(goodsInfo.getCostPrice());
        record2.setOptTime(end);
        result.add(record2);

        return result;
    }

    @Override
    public DataTableRespVo dataTable(DataTableReqVo reqVo) {
        DataTableRespVo respVo = new DataTableRespVo();
        respVo.setOrderCount(dataTableMapper.getOrderCount(reqVo));
        respVo.setOrderAmountCount(dataTableMapper.getOrderAmountCount(reqVo));
        respVo.setSaleData7Days(dataTableMapper.getSaleAmount7Days(reqVo));
        return respVo;
    }

    @Override
    public List<SellerSalePercent> getSellerSalePercent() {
        List<SellerSalePercent> salePercentList = dataTableMapper.getSellerSalePercent();
        List<SellerSalePercent> respList = new ArrayList<>();
        /*BigDecimal totalAmount = new BigDecimal(0);
        if(salePercentList != null && salePercentList.size() > 0) {
            for(SellerSalePercent percent : salePercentList) {
                totalAmount = totalAmount.add(percent.getSaleAmount());
            }
        }*/
        if(salePercentList.size() <= 10) {
            return salePercentList;
        }
        for(int i=0; i<9; i++) {
            respList.add(salePercentList.get(i));
        }
        BigDecimal otherAmount = new BigDecimal(0);
        for(int i=9; i<salePercentList.size(); i++) {
            otherAmount = otherAmount.add(salePercentList.get(i).getSaleAmount());
        }
        SellerSalePercent other = new SellerSalePercent();
        other.setSellerName("其他");
        other.setSaleAmount(otherAmount);
        respList.add(other);
        return respList;
    }

    @Override
    public List<OrderCountRank> getOrderCountRank() {
        return dataTableMapper.getOrderCountRank();
    }

    @Override
    public List<AmountCountRank> getAmountCountRank() {
        return dataTableMapper.getAmountCountRank();
    }

    /**
     * 获取销方的价格比例
     * @param sellerId
     * @return
     */
    public BigDecimal getSellerProportion(String sellerId) {
        if(StringUtils.isNotEmpty(sellerId)) {
            SellerInfo sellerInfo = sellerInfoService.selectSellerInfoById(sellerId);
            if(ObjectUtil.isNotNull(sellerInfo)
                    && ObjectUtil.isNotNull(sellerInfo.getIfAgent())
                    && sellerInfo.getIfAgent() == 0
                    && StringUtils.isNotEmpty(sellerInfo.getParentAgentId())) {
                SellerInfo agent = sellerInfoService.selectSellerInfoById(sellerInfo.getParentAgentId());
                if(ObjectUtil.isNotNull(agent) && ObjectUtil.isNotNull(agent.getProportion())) {
                    BigDecimal proportion = agent.getProportion().divide(new BigDecimal(100)).setScale(2, 4).add(new BigDecimal(1));
                    return proportion;
                }
            }
        }
        return null;
    }
}
