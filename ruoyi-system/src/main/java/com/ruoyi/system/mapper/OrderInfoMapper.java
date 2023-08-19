package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.OrderAftermarketFollowup;
import com.ruoyi.system.domain.OrderInfo;
import com.ruoyi.system.domain.OrderPushLog;
import com.ruoyi.system.domain.vo.OrderInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 订单信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Mapper
@Repository("OrderInfoMapper")
public interface OrderInfoMapper
{
    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息集合
     */
    public List<OrderInfo> selectAfterOrderInfoList(OrderInfo orderInfo);

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 删除订单信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);

    public List<OrderInfoVo> selectOrderInfoDetailList(OrderInfo orderInfo);

    public OrderInfoVo selectOrderInfoDetailByOrderId(Long orderId);

    /**
     * 标记异常订单
     * @param id
     * @return
     */
    public int remarkAbnormalOrder(Long id);

    /**
     * 撤销异常订单标记
     * @param id
     * @return
     */
    public int rollbackAbnormal(Long id);

    /**
     * 保存订单售后状态变更记录
     * @param followup
     */
    public void saveOrderAftermarketStatusFollowup(OrderAftermarketFollowup followup);

    /**
     * 查询订单售后状态变更记录
     * @param followup
     * @return
     */
    List<OrderAftermarketFollowup> queryOrderAftermarketStatusFollowup(OrderAftermarketFollowup followup);

    /**
     * 保存订单推送记录
     * @param log
     * @return
     */
    public int saveOrderPushLog(OrderPushLog log);
}
