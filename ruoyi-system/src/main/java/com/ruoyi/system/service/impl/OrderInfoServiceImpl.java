package com.ruoyi.system.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.OrderInfoVo;
import com.ruoyi.system.domain.vo.PushOrderReqVo;
import com.ruoyi.system.mapper.OrderInfoMapper;
import com.ruoyi.system.mapper.OrderItemMapper;
import com.ruoyi.system.service.IOrderInfoService;
import com.ruoyi.system.service.ISellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 订单信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements IOrderInfoService
{
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ISellerInfoService sellerInfoService;

    @Value("${order.push.url}")
    private String pushOrderUrl;

    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public OrderInfo selectOrderInfoById(Long id)
    {
        return orderInfoMapper.selectOrderInfoById(id);
    }

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息
     */
    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo)
    {

        List<OrderInfo> list = orderInfoMapper.selectOrderInfoList(orderInfo);

        BigDecimal proportion = getSellerProportion(orderInfo.getSellerId());
        if(ObjectUtil.isNotNull(proportion)) {
            list.forEach(order -> {
                order.setTotalAmount(order.getTotalAmount().multiply(proportion).setScale(2,4));
            });
        }

        return list;
    }

    @Override
    public List<OrderInfo> selectAfterOrderInfoList(OrderInfo orderInfo) {
        List<OrderInfo> list = orderInfoMapper.selectAfterOrderInfoList(orderInfo);
        BigDecimal proportion = getSellerProportion(orderInfo.getSellerId());
        if(ObjectUtil.isNotNull(proportion)) {
            list.forEach(order -> {
                order.setTotalAmount(order.getTotalAmount().multiply(proportion).setScale(2,4));
            });
        }
        return list;
    }

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public int insertOrderInfo(OrderInfo orderInfo)
    {
        orderInfo.setCreateTime(DateUtils.getNowDate());
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public int updateOrderInfo(OrderInfo orderInfo)
    {
        orderInfo.setUpdateTime(DateUtils.getNowDate());
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoByIds(Long[] ids)
    {
        return orderInfoMapper.deleteOrderInfoByIds(ids);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoById(Long id)
    {
        return orderInfoMapper.deleteOrderInfoById(id);
    }

    @Override
    public List<OrderInfoVo> selectOrderInfoDetailList(OrderInfo orderInfo) {
        List<OrderInfo> orderInfoList = orderInfoMapper.selectOrderInfoList(orderInfo);
        List<OrderInfoVo> list = new ArrayList<>();
        for(OrderInfo info : orderInfoList) {
            OrderInfoVo infoVo = new OrderInfoVo();
            BeanUtils.copyProperties(info, infoVo);
            infoVo.setOrderItems(orderItemMapper.queryOrderItemByOrderId(info.getId()));
            list.add(infoVo);
        }

        BigDecimal proportion = getSellerProportion(orderInfo.getSellerId());
        if(ObjectUtil.isNotNull(proportion)) {
            list.forEach(order -> {
                order.setTotalAmount(order.getTotalAmount().multiply(proportion).setScale(2,4));
                order.getOrderItems().forEach(item->{
                    item.setPrice(item.getPrice().multiply(proportion).setScale(2, 4));
                });
            });
        }

        return list;
    }

    @Override
    public OrderInfoVo selectOrderInfoDetailByOrderId(Long orderId, UserCompany userCompany) {
        OrderInfoVo orderInfoVo = orderInfoMapper.selectOrderInfoDetailByOrderId(orderId);
        if(userCompany.getCompanyType() == 2) {
            BigDecimal proportion = getSellerProportion(userCompany.getCompanyId());
            orderInfoVo.setTotalAmount(orderInfoVo.getTotalAmount().multiply(proportion).setScale(2,4));
            orderInfoVo.getOrderItems().forEach(item->{
                item.setPrice(item.getPrice().multiply(proportion).setScale(2, 4));
            });
        }
        return orderInfoVo;
    }

    @Override
    public int rollbackAbnormal(Long id) {
        return orderInfoMapper.rollbackAbnormal(id);
    }

    @Override
    public String uploadOrderInfo(MultipartFile orderFile, HttpServletResponse response)  {
        List<OrderInfoVo> orderList = readOrderExcel(orderFile);
        List<String> resultMsg = new ArrayList<>();
        if(Objects.isNull(orderList) || orderList.size() <= 0) {
            resultMsg.add("读取表格失败！");
        }

        int successNum = 0;
        for(int i=0; i<orderList.size(); i++) {
            OrderInfoVo orderInfoVo = orderList.get(i);
            OrderInfo orderInfo = new OrderInfo();
            BeanUtils.copyProperties(orderInfoVo, orderInfo);
            if(StringUtils.isBlank(orderInfo.getOutTradeNo())) {
                continue;
            }

            int result = orderInfoMapper.insertOrderInfo(orderInfo);
            if(result > 0) {
                successNum ++;
            }
            List<OrderItem> orderItemList = orderInfoVo.getOrderItems();
            if(ObjectUtil.isNotNull(orderItemList) && orderItemList.size() > 0) {
                for(int j=0; j<orderItemList.size(); j++) {
                    OrderItem orderItem = orderItemList.get(j);
                    if(StringUtils.isBlank(orderItem.getGoodsId())) {
                        continue;
                    }
                    orderItem.setOrderId(orderInfo.getId());
                    orderItemMapper.insertOrderItem(orderItem);
                }
            }
        }
        return "导入成功" + successNum + "条订单";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAftermarketFollowup(OrderAftermarketFollowup followup) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(followup.getOrderId());
        orderInfo.setAftermarketStatus(followup.getAftermarketStatus());
        orderInfo.setUpdateBy(String.valueOf(followup.getCreateBy()));
        orderInfo.setUpdateTime(followup.getCreateTime());
        orderInfoMapper.updateOrderInfo(orderInfo);

        followup.setDelFlag(0);
        orderInfoMapper.saveOrderAftermarketStatusFollowup(followup);

    }

    @Override
    public List<OrderAftermarketFollowup> queryOrderAftermarketStatusFollowup(OrderAftermarketFollowup followup) {
        return orderInfoMapper.queryOrderAftermarketStatusFollowup(followup);
    }

    @Override
    public AjaxResult pushOrder(PushOrderReqVo reqVo, String user) {
        if(ObjectUtil.isNull(reqVo) || reqVo.getOutTradeNos().size() <= 0) {
            return AjaxResult.error("请求数据有误");
        }
        OrderPushLog pushLog = new OrderPushLog();
        JSONObject reqJson = new JSONObject();
        reqJson.put("OutTradeNos", reqVo.getOutTradeNos());
        log.info("推单请求:{}", JSON.toJSONString(reqJson));
        String respStr = HttpUtils.sendPost(pushOrderUrl, JSON.toJSONString(reqJson));
        log.info("推单响应：{}", respStr);
        if(StringUtils.isEmpty(respStr)) {
            return AjaxResult.error("推送失败");
        }

        pushLog.setReqInfo(JSON.toJSONString(reqJson));
        pushLog.setRespInfo(respStr);
        pushLog.setCreateBy(user);
        pushLog.setCreateTime(new Date());
        orderInfoMapper.saveOrderPushLog(pushLog);
        JSONObject respJson = JSON.parseObject(respStr);
        if(ObjectUtil.isNotNull(respJson) && respJson.containsKey("success") && respJson.getBoolean("success")) {
            return AjaxResult.success("推送成功");
        } else {
            if(respJson.containsKey("message")) {
                String message = respJson.getString("message");
                return AjaxResult.error(message);
            } else {
                return AjaxResult.error("推送失败");
            }
        }
    }

    @Override
    public List<OrderItem> getOrderGoodsList(Long orderId) {
        return orderItemMapper.queryOrderItemByOrderId(orderId);
    }

    public List<OrderInfoVo> readOrderExcel(MultipartFile orderFile) {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        importParams.setHeadRows(2);

        List<OrderInfoVo> readAll = null;
        try {
            readAll = ExcelImportUtil.importExcel(orderFile.getInputStream(), OrderInfoVo.class, importParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  readAll;
    }


    /**
     * 获取销方的价格比例
     * @param sellerId
     * @return
     */
    public BigDecimal getSellerProportion(String sellerId) {
        if(StringUtils.isNotEmpty(sellerId)) {
            SellerInfo sellerInfo = sellerInfoService.selectSellerInfoById(sellerId);
            if(ObjectUtil.isNotNull(sellerInfo)
                    && ObjectUtil.isNotNull(sellerInfo.getIfAgent())
                    && sellerInfo.getIfAgent() == 0
                    && StringUtils.isNotEmpty(sellerInfo.getParentAgentId())) {
                SellerInfo agent = sellerInfoService.selectSellerInfoById(sellerInfo.getParentAgentId());
                if(ObjectUtil.isNotNull(agent) && ObjectUtil.isNotNull(agent.getProportion())) {
                    BigDecimal proportion = agent.getProportion().divide(new BigDecimal(100)).setScale(2, 4).add(new BigDecimal(1));
                    return proportion;
                }
            }
        }
        return null;
    }
}
