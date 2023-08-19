package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OrderAbnormal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 异常订单Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Mapper
@Repository("OrderAbnormalMapper")
public interface OrderAbnormalMapper
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
     * 删除异常订单
     *
     * @param id 异常订单主键
     * @return 结果
     */
    public int deleteOrderAbnormalById(Long id);

    /**
     * 批量删除异常订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderAbnormalByIds(Long[] ids);

    /**
     * 根据订单号查询异常订单
     * @param orderInfoId
     * @return
     */
    public OrderAbnormal queryOrderAbnormalByOrderId(Long orderInfoId);
}
