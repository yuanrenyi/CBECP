package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.OrderInfo;
import com.ruoyi.system.domain.SellerWithdrawalOrderRef;
import com.ruoyi.system.mapper.OrderInfoMapper;
import com.ruoyi.system.mapper.SellerWithdrawalOrderRefMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SellerWithdrawalInfoMapper;
import com.ruoyi.system.domain.SellerWithdrawalInfo;
import com.ruoyi.system.service.ISellerWithdrawalInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售方提现记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class SellerWithdrawalInfoServiceImpl implements ISellerWithdrawalInfoService 
{
    @Autowired
    private SellerWithdrawalInfoMapper sellerWithdrawalInfoMapper;

    @Autowired
    private SellerWithdrawalOrderRefMapper sellerWithdrawalOrderRefMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 查询销售方提现记录
     * 
     * @param id 销售方提现记录主键
     * @return 销售方提现记录
     */
    @Override
    public SellerWithdrawalInfo selectSellerWithdrawalInfoById(Long id)
    {
        SellerWithdrawalInfo sellerWithdrawalInfo = sellerWithdrawalInfoMapper.selectSellerWithdrawalInfoById(id);
        List<OrderInfo> orderInfoList = sellerWithdrawalOrderRefMapper.querySellerWithdrawalOrderInfo(id);
        sellerWithdrawalInfo.setOrderInfoList(orderInfoList);
        return sellerWithdrawalInfo;
    }

    /**
     * 查询销售方提现记录列表
     * 
     * @param sellerWithdrawalInfo 销售方提现记录
     * @return 销售方提现记录
     */
    @Override
    public List<SellerWithdrawalInfo> selectSellerWithdrawalInfoList(SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        return sellerWithdrawalInfoMapper.selectSellerWithdrawalInfoList(sellerWithdrawalInfo);
    }

    /**
     * 新增销售方提现记录
     * 
     * @param sellerWithdrawalInfo 销售方提现记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertSellerWithdrawalInfo(SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        if(Objects.isNull(sellerWithdrawalInfo.getOrderInfoIds())) {
            throw new ServiceException("请选择要提现的订单");
        }

        sellerWithdrawalInfo.setCreateTime(DateUtils.getNowDate());
        sellerWithdrawalInfoMapper.insertSellerWithdrawalInfo(sellerWithdrawalInfo);

        sellerWithdrawalInfo.getOrderInfoIds().forEach(orderId -> {
            SellerWithdrawalOrderRef sellerWithdrawalOrderRef = SellerWithdrawalOrderRef.builder().orderInfoId(orderId).sellerWithdrawalId(sellerWithdrawalInfo.getId()).build();
            sellerWithdrawalOrderRefMapper.insertSellerWithdrawalOrderRef(sellerWithdrawalOrderRef);

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(orderId);
            orderInfo.setSellerWithdrawalStatus(2);
            orderInfoMapper.updateOrderInfo(orderInfo);
        });

        return 1;
    }

    /**
     * 修改销售方提现记录
     * 
     * @param sellerWithdrawalInfo 销售方提现记录
     * @return 结果
     */
    @Override
    public int updateSellerWithdrawalInfo(SellerWithdrawalInfo sellerWithdrawalInfo)
    {
        sellerWithdrawalInfo.setUpdateTime(DateUtils.getNowDate());
        return sellerWithdrawalInfoMapper.updateSellerWithdrawalInfo(sellerWithdrawalInfo);
    }

    /**
     * 批量删除销售方提现记录
     * 
     * @param ids 需要删除的销售方提现记录主键
     * @return 结果
     */
    @Override
    public int deleteSellerWithdrawalInfoByIds(Long[] ids)
    {
        return sellerWithdrawalInfoMapper.deleteSellerWithdrawalInfoByIds(ids);
    }

    /**
     * 删除销售方提现记录信息
     * 
     * @param id 销售方提现记录主键
     * @return 结果
     */
    @Override
    public int deleteSellerWithdrawalInfoById(Long id)
    {
        return sellerWithdrawalInfoMapper.deleteSellerWithdrawalInfoById(id);
    }
}
