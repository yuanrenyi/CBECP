package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OrderAbnormalMapper;
import com.ruoyi.system.domain.OrderAbnormal;
import com.ruoyi.system.service.IOrderAbnormalService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 异常订单Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Service
public class OrderAbnormalServiceImpl implements IOrderAbnormalService
{
    @Autowired
    private OrderAbnormalMapper orderAbnormalMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 查询异常订单
     *
     * @param id 异常订单主键
     * @return 异常订单
     */
    @Override
    public OrderAbnormal selectOrderAbnormalById(Long id)
    {
        return orderAbnormalMapper.selectOrderAbnormalById(id);
    }

    /**
     * 查询异常订单列表
     *
     * @param orderAbnormal 异常订单
     * @return 异常订单
     */
    @Override
    public List<OrderAbnormal> selectOrderAbnormalList(OrderAbnormal orderAbnormal)
    {
        return orderAbnormalMapper.selectOrderAbnormalList(orderAbnormal);
    }

    /**
     * 新增异常订单
     *
     * @param orderAbnormal 异常订单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertOrderAbnormal(OrderAbnormal orderAbnormal)
    {
        orderAbnormal.setCreateTime(DateUtils.getNowDate());

        //更改订单信息中的异常状态
        orderInfoMapper.remarkAbnormalOrder(orderAbnormal.getOrderInfoId());

        //查询是否已经存在订单异常信息
        OrderAbnormal existAbnormal = orderAbnormalMapper.queryOrderAbnormalByOrderId(orderAbnormal.getOrderInfoId());
        if(ObjectUtil.isNotNull(existAbnormal)) {
            orderAbnormal.setId(existAbnormal.getId());
            return orderAbnormalMapper.updateOrderAbnormal(orderAbnormal);
        }
        return orderAbnormalMapper.insertOrderAbnormal(orderAbnormal);
    }

    /**
     * 修改异常订单
     *
     * @param orderAbnormal 异常订单
     * @return 结果
     */
    @Override
    public int updateOrderAbnormal(OrderAbnormal orderAbnormal)
    {
        return orderAbnormalMapper.updateOrderAbnormal(orderAbnormal);
    }

    /**
     * 批量删除异常订单
     *
     * @param ids 需要删除的异常订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderAbnormalByIds(Long[] ids)
    {
        return orderAbnormalMapper.deleteOrderAbnormalByIds(ids);
    }

    /**
     * 删除异常订单信息
     *
     * @param id 异常订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderAbnormalById(Long id)
    {
        return orderAbnormalMapper.deleteOrderAbnormalById(id);
    }
}
