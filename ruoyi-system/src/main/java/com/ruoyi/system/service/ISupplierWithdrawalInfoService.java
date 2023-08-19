package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SupplierWithdrawalInfo;

/**
 * 供应商提现记录Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface ISupplierWithdrawalInfoService 
{
    /**
     * 查询供应商提现记录
     * 
     * @param id 供应商提现记录主键
     * @return 供应商提现记录
     */
    public SupplierWithdrawalInfo selectSupplierWithdrawalInfoById(Long id);

    /**
     * 查询供应商提现记录列表
     * 
     * @param supplierWithdrawalInfo 供应商提现记录
     * @return 供应商提现记录集合
     */
    public List<SupplierWithdrawalInfo> selectSupplierWithdrawalInfoList(SupplierWithdrawalInfo supplierWithdrawalInfo);

    /**
     * 新增供应商提现记录
     * 
     * @param supplierWithdrawalInfo 供应商提现记录
     * @return 结果
     */
    public int insertSupplierWithdrawalInfo(SupplierWithdrawalInfo supplierWithdrawalInfo);

    /**
     * 修改供应商提现记录
     * 
     * @param supplierWithdrawalInfo 供应商提现记录
     * @return 结果
     */
    public int updateSupplierWithdrawalInfo(SupplierWithdrawalInfo supplierWithdrawalInfo);

    /**
     * 批量删除供应商提现记录
     * 
     * @param ids 需要删除的供应商提现记录主键集合
     * @return 结果
     */
    public int deleteSupplierWithdrawalInfoByIds(Long[] ids);

    /**
     * 删除供应商提现记录信息
     * 
     * @param id 供应商提现记录主键
     * @return 结果
     */
    public int deleteSupplierWithdrawalInfoById(Long id);
}
