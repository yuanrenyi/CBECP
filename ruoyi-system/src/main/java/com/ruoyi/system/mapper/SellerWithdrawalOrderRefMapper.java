package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.OrderInfo;
import com.ruoyi.system.domain.SellerWithdrawalOrderRef;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 销售方提现记录与订单记录关联Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Mapper
@Repository("SellerWithdrawalOrderRefMapper")
public interface SellerWithdrawalOrderRefMapper
{
    /**
     * 查询销售方提现记录与订单记录关联
     *
     * @param id 销售方提现记录与订单记录关联主键
     * @return 销售方提现记录与订单记录关联
     */
    public SellerWithdrawalOrderRef selectSellerWithdrawalOrderRefById(Long id);

    /**
     * 查询销售方提现记录与订单记录关联列表
     *
     * @param sellerWithdrawalOrderRef 销售方提现记录与订单记录关联
     * @return 销售方提现记录与订单记录关联集合
     */
    public List<SellerWithdrawalOrderRef> selectSellerWithdrawalOrderRefList(SellerWithdrawalOrderRef sellerWithdrawalOrderRef);

    /**
     * 新增销售方提现记录与订单记录关联
     *
     * @param sellerWithdrawalOrderRef 销售方提现记录与订单记录关联
     * @return 结果
     */
    public int insertSellerWithdrawalOrderRef(SellerWithdrawalOrderRef sellerWithdrawalOrderRef);

    /**
     * 修改销售方提现记录与订单记录关联
     *
     * @param sellerWithdrawalOrderRef 销售方提现记录与订单记录关联
     * @return 结果
     */
    public int updateSellerWithdrawalOrderRef(SellerWithdrawalOrderRef sellerWithdrawalOrderRef);

    /**
     * 删除销售方提现记录与订单记录关联
     *
     * @param id 销售方提现记录与订单记录关联主键
     * @return 结果
     */
    public int deleteSellerWithdrawalOrderRefById(Long id);

    /**
     * 批量删除销售方提现记录与订单记录关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSellerWithdrawalOrderRefByIds(Long[] ids);


    /**
     * 根据销售方提现记录ID查询对应的订单信息
     * @param sellerWithdrawalId
     * @return
     */
    public List<OrderInfo> querySellerWithdrawalOrderInfo(Long sellerWithdrawalId);
}
