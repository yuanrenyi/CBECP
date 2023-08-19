package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OrderAbnormal;

/**
 * 异常订单Service接口
 *
 * @author ruoyi
 * @date 2023-01-14
 */
public interface IOrderAbnormalService
{
    /**
     * 查询异常订单
     *
     * @param id 异常订单主键
     * @return 异常订单
     */
    public OrderAbnormal selectOrderAbnormalById(Long id);

    /**
     * 查询异常订单列表
     *
     * @param orderAbnormal 异常订单
     * @return 异常订单集合
     */
    public List<OrderAbnormal> selectOrderAbnormalList(OrderAbnormal orderAbnormal);

    /**
     * 新增异常订单
     *
     * @param orderAbnormal 异常订单
     * @return 结果
     */
    public int insertOrderAbnormal(OrderAbnormal orderAbnormal);

    /**
     * 修改异常订单
     *
     * @param orderAbnormal 异常订单
     * @return 结果
     */
    public int updateOrderAbnormal(OrderAbnormal orderAbnormal);

    /**
     * 批量删除异常订单
     *
     * @param ids 需要删除的异常订单主键集合
     * @return 结果
     */
    public int deleteOrderAbnormalByIds(Long[] ids);

    /**
     * 删除异常订单信息
     *
     * @param id 异常订单主键
     * @return 结果
     */
    public int deleteOrderAbnormalById(Long id);
}
