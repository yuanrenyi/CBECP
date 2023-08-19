package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OrderAbnormalFollowup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 异常订单跟进Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-18
 */
@Mapper
@Repository("OrderAbnormalFollowupMapper")
public interface OrderAbnormalFollowupMapper
{
    /**
     * 查询异常订单跟进
     *
     * @param id 异常订单跟进主键
     * @return 异常订单跟进
     */
    public OrderAbnormalFollowup selectOrderAbnormalFollowupById(Long id);

    /**
     * 查询异常订单跟进列表
     *
     * @param orderAbnormalFollowup 异常订单跟进
     * @return 异常订单跟进集合
     */
    public List<OrderAbnormalFollowup> selectOrderAbnormalFollowupList(OrderAbnormalFollowup orderAbnormalFollowup);

    /**
     * 新增异常订单跟进
     *
     * @param orderAbnormalFollowup 异常订单跟进
     * @return 结果
     */
    public int insertOrderAbnormalFollowup(OrderAbnormalFollowup orderAbnormalFollowup);

    /**
     * 修改异常订单跟进
     *
     * @param orderAbnormalFollowup 异常订单跟进
     * @return 结果
     */
    public int updateOrderAbnormalFollowup(OrderAbnormalFollowup orderAbnormalFollowup);

    /**
     * 删除异常订单跟进
     *
     * @param id 异常订单跟进主键
     * @return 结果
     */
    public int deleteOrderAbnormalFollowupById(Long id);

    /**
     * 批量删除异常订单跟进
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderAbnormalFollowupByIds(Long[] ids);
}
