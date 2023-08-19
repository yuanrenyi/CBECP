package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 订单商品Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Mapper
@Repository
public interface OrderItemMapper
{
    /**
     * 查询订单商品
     *
     * @param id 订单商品主键
     * @return 订单商品
     */
    public OrderItem selectOrderItemById(Long id);

    /**
     * 查询订单商品列表
     *
     * @param orderItem 订单商品
     * @return 订单商品集合
     */
    public List<OrderItem> selectOrderItemList(OrderItem orderItem);

    /**
     * 新增订单商品
     *
     * @param orderItem 订单商品
     * @return 结果
     */
    public int insertOrderItem(OrderItem orderItem);

    /**
     * 修改订单商品
     *
     * @param orderItem 订单商品
     * @return 结果
     */
    public int updateOrderItem(OrderItem orderItem);

    /**
     * 删除订单商品
     *
     * @param id 订单商品主键
     * @return 结果
     */
    public int deleteOrderItemById(Long id);

    /**
     * 批量删除订单商品
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderItemByIds(Long[] ids);

    /**
     * 根据订单号查询订单中的商品信息
     * @param orderId
     * @return
     */
    public List<OrderItem> queryOrderItemByOrderId(Long orderId);
}
