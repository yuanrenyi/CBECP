package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Objects;

import cn.hutool.db.sql.Order;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.OrderInfo;
import com.ruoyi.system.domain.SupplierWithdrawalOrderRef;
import com.ruoyi.system.mapper.OrderInfoMapper;
import com.ruoyi.system.mapper.SupplierWithdrawalOrderRefMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SupplierWithdrawalInfoMapper;
import com.ruoyi.system.domain.SupplierWithdrawalInfo;
import com.ruoyi.system.service.ISupplierWithdrawalInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 供应商提现记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
@Slf4j
public class SupplierWithdrawalInfoServiceImpl implements ISupplierWithdrawalInfoService 
{
    @Autowired
    private SupplierWithdrawalInfoMapper supplierWithdrawalInfoMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private SupplierWithdrawalOrderRefMapper supplierWithdrawalOrderRefMapper;

    /**
     * 查询供应商提现记录
     * 
     * @param id 供应商提现记录主键
     * @return 供应商提现记录
     */
    @Override
    public SupplierWithdrawalInfo selectSupplierWithdrawalInfoById(Long id)
    {
        SupplierWithdrawalInfo supplierWithdrawalInfo = supplierWithdrawalInfoMapper.selectSupplierWithdrawalInfoById(id);
        supplierWithdrawalInfo.setOrderInfoList(supplierWithdrawalOrderRefMapper.querySupplierWithdrawalOrderInfo(id));
        return supplierWithdrawalInfo;
    }

    /**
     * 查询供应商提现记录列表
     * 
     * @param supplierWithdrawalInfo 供应商提现记录
     * @return 供应商提现记录
     */
    @Override
    public List<SupplierWithdrawalInfo> selectSupplierWithdrawalInfoList(SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        return supplierWithdrawalInfoMapper.selectSupplierWithdrawalInfoList(supplierWithdrawalInfo);
    }

    /**
     * 新增供应商提现记录
     * 
     * @param supplierWithdrawalInfo 供应商提现记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertSupplierWithdrawalInfo(SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        if(Objects.isNull(supplierWithdrawalInfo.getOrderInfoIds())) {
            throw new ServiceException("请选择要提现的订单");
        }

        supplierWithdrawalInfoMapper.insertSupplierWithdrawalInfo(supplierWithdrawalInfo);

        supplierWithdrawalInfo.getOrderInfoIds().forEach(orderId -> {
            SupplierWithdrawalOrderRef supplierWithdrawalOrderRef = SupplierWithdrawalOrderRef.builder().orderInfoId(orderId).supplierWithdrawalId(supplierWithdrawalInfo.getId()).build();
            supplierWithdrawalOrderRefMapper.insertSupplierWithdrawalOrderRef(supplierWithdrawalOrderRef);

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(orderId);
            orderInfo.setSupplierWithdrawalStatus(1);
            orderInfoMapper.updateOrderInfo(orderInfo);

        });
        supplierWithdrawalInfo.setCreateTime(DateUtils.getNowDate());
        return 1;
    }

    /**
     * 修改供应商提现记录
     * 
     * @param supplierWithdrawalInfo 供应商提现记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int updateSupplierWithdrawalInfo(SupplierWithdrawalInfo supplierWithdrawalInfo)
    {
        //提现申请审核通过，修改订单的提现状态为已提现
        List<OrderInfo> orderInfoList = supplierWithdrawalOrderRefMapper.querySupplierWithdrawalOrderInfo(supplierWithdrawalInfo.getId());
        if(Objects.isNull(orderInfoList)) {
            log.info("未查询到提现记录{}对应的订单信息", supplierWithdrawalInfo.getId());
            throw new ServiceException("提现记录信息有误");
        }

        if(supplierWithdrawalInfo.getAuditStatus() == 1) {
            orderInfoList.forEach(orderInfo -> {
                OrderInfo update = new OrderInfo();
                update.setId(orderInfo.getId());
                update.setSupplierWithdrawalStatus(2);
                orderInfoMapper.updateOrderInfo(update);
            });

        } else if(supplierWithdrawalInfo.getAuditStatus() == 2){
            //提现申请未审核通过， 修改订单的提现状态为待提现，让供应商重新发起提现
            orderInfoList.forEach(orderInfo -> {
                OrderInfo update = new OrderInfo();
                update.setId(orderInfo.getId());
                update.setSupplierWithdrawalStatus(0);
                orderInfoMapper.updateOrderInfo(update);
            });

        }

        supplierWithdrawalInfo.setUpdateTime(DateUtils.getNowDate());
        return supplierWithdrawalInfoMapper.updateSupplierWithdrawalInfo(supplierWithdrawalInfo);
    }

    /**
     * 批量删除供应商提现记录
     * 
     * @param ids 需要删除的供应商提现记录主键
     * @return 结果
     */
    @Override
    public int deleteSupplierWithdrawalInfoByIds(Long[] ids)
    {
        return supplierWithdrawalInfoMapper.deleteSupplierWithdrawalInfoByIds(ids);
    }

    /**
     * 删除供应商提现记录信息
     * 
     * @param id 供应商提现记录主键
     * @return 结果
     */
    @Override
    public int deleteSupplierWithdrawalInfoById(Long id)
    {
        return supplierWithdrawalInfoMapper.deleteSupplierWithdrawalInfoById(id);
    }
}
