package com.ruoyi.system.service;

import java.io.IOException;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.OrderAftermarketFollowup;
import com.ruoyi.system.domain.OrderInfo;
import com.ruoyi.system.domain.OrderItem;
import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.domain.vo.OrderInfoVo;
import com.ruoyi.system.domain.vo.PushOrderReqVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 订单信息Service接口
 *
 * @author ruoyi
 * @date 2023-01-10
 */
public interface IOrderInfoService
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
     * 查询售后订单信息列表
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
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 查询订单列表详情
     * @param orderInfo
     * @return
     */
    public List<OrderInfoVo> selectOrderInfoDetailList(OrderInfo orderInfo);

    /**
     * 根据订单表ID查询订单详情
     * @param orderId
     * @return
     */
    public OrderInfoVo selectOrderInfoDetailByOrderId(Long orderId, UserCompany userCompany);

    /**
     * 撤销异常订单标记
     * @param id
     * @return
     */
    public int rollbackAbnormal(Long id);

    /**
     * 导入订单信息
     * @param orderFile
     */
    public String uploadOrderInfo(MultipartFile orderFile, HttpServletResponse response) throws IOException;

    /**
     * 保存订单状态更新记录
     * @param followup
     */
    public void saveAftermarketFollowup(OrderAftermarketFollowup followup);

    /**
     * 查询订单售后状态变更记录
     * @param followup
     * @return
     */
    public List<OrderAftermarketFollowup> queryOrderAftermarketStatusFollowup(OrderAftermarketFollowup followup);

    /**
     * 推单
     * @param reqVo
     * @param user
     * @return
     */
    public AjaxResult pushOrder(PushOrderReqVo reqVo, String user);

    /**
     * 根据订单ID获取订单商品数据
     * @param orderId
     * @return
     */
    public List<OrderItem> getOrderGoodsList(Long orderId);
}
