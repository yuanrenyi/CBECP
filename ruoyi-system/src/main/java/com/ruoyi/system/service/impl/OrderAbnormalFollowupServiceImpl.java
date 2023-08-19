package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OrderAbnormalFollowupMapper;
import com.ruoyi.system.domain.OrderAbnormalFollowup;
import com.ruoyi.system.service.IOrderAbnormalFollowupService;

/**
 * 异常订单跟进Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-18
 */
@Service
public class OrderAbnormalFollowupServiceImpl implements IOrderAbnormalFollowupService
{
    @Autowired
    private OrderAbnormalFollowupMapper orderAbnormalFollowupMapper;

    /**
     * 查询异常订单跟进
     *
     * @param id 异常订单跟进主键
     * @return 异常订单跟进
     */
    @Override
    public OrderAbnormalFollowup selectOrderAbnormalFollowupById(Long id)
    {
        return orderAbnormalFollowupMapper.selectOrderAbnormalFollowupById(id);
    }

    /**
     * 查询异常订单跟进列表
     *
     * @param orderAbnormalFollowup 异常订单跟进
     * @return 异常订单跟进
     */
    @Override
    public List<OrderAbnormalFollowup> selectOrderAbnormalFollowupList(OrderAbnormalFollowup orderAbnormalFollowup)
    {
        return orderAbnormalFollowupMapper.selectOrderAbnormalFollowupList(orderAbnormalFollowup);
    }

    /**
     * 新增异常订单跟进
     *
     * @param orderAbnormalFollowup 异常订单跟进
     * @return 结果
     */
    @Override
    public int insertOrderAbnormalFollowup(OrderAbnormalFollowup orderAbnormalFollowup)
    {
        orderAbnormalFollowup.setCreateTime(DateUtils.getNowDate());
        return orderAbnormalFollowupMapper.insertOrderAbnormalFollowup(orderAbnormalFollowup);
    }

    /**
     * 修改异常订单跟进
     *
     * @param orderAbnormalFollowup 异常订单跟进
     * @return 结果
     */
    @Override
    public int updateOrderAbnormalFollowup(OrderAbnormalFollowup orderAbnormalFollowup)
    {
        return orderAbnormalFollowupMapper.updateOrderAbnormalFollowup(orderAbnormalFollowup);
    }

    /**
     * 批量删除异常订单跟进
     *
     * @param ids 需要删除的异常订单跟进主键
     * @return 结果
     */
    @Override
    public int deleteOrderAbnormalFollowupByIds(Long[] ids)
    {
        return orderAbnormalFollowupMapper.deleteOrderAbnormalFollowupByIds(ids);
    }

    /**
     * 删除异常订单跟进信息
     *
     * @param id 异常订单跟进主键
     * @return 结果
     */
    @Override
    public int deleteOrderAbnormalFollowupById(Long id)
    {
        return orderAbnormalFollowupMapper.deleteOrderAbnormalFollowupById(id);
    }
}
