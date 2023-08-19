package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.OrderInfo;
import com.ruoyi.system.domain.SupplierWithdrawalOrderRef;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 供应商提现记录与订单记录关联Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-14
 */
@Mapper
@Repository("SupplierWithdrawalOrderRefMapper")
public interface SupplierWithdrawalOrderRefMapper
{
    /**
     * 查询供应商提现记录与订单记录关联
     *
     * @param id 供应商提现记录与订单记录关联主键
     * @return 供应商提现记录与订单记录关联
     */
    public SupplierWithdrawalOrderRef selectSupplierWithdrawalOrderRefById(Long id);

    /**
     * 查询供应商提现记录与订单记录关联列表
     *
     * @param supplierWithdrawalOrderRef 供应商提现记录与订单记录关联
     * @return 供应商提现记录与订单记录关联集合
     */
    public List<SupplierWithdrawalOrderRef> selectSupplierWithdrawalOrderRefList(SupplierWithdrawalOrderRef supplierWithdrawalOrderRef);

    /**
     * 新增供应商提现记录与订单记录关联
     *
     * @param supplierWithdrawalOrderRef 供应商提现记录与订单记录关联
     * @return 结果
     */
    public int insertSupplierWithdrawalOrderRef(SupplierWithdrawalOrderRef supplierWithdrawalOrderRef);

    /**
     * 修改供应商提现记录与订单记录关联
     *
     * @param supplierWithdrawalOrderRef 供应商提现记录与订单记录关联
     * @return 结果
     */
    public int updateSupplierWithdrawalOrderRef(SupplierWithdrawalOrderRef supplierWithdrawalOrderRef);

    /**
     * 删除供应商提现记录与订单记录关联
     *
     * @param id 供应商提现记录与订单记录关联主键
     * @return 结果
     */
    public int deleteSupplierWithdrawalOrderRefById(Long id);

    /**
     * 批量删除供应商提现记录与订单记录关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierWithdrawalOrderRefByIds(Long[] ids);

    /**
     * 查询供应商提现记录对应的订单信息
     * @param supplierWithdrawalId
     * @return
     */
    public List<OrderInfo> querySupplierWithdrawalOrderInfo(Long supplierWithdrawalId);
}
