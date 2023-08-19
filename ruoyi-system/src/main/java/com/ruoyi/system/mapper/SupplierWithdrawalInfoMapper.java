package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SupplierWithdrawalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 供应商提现记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Mapper
@Repository("SupplierWithdrawalInfoMapper")
public interface SupplierWithdrawalInfoMapper 
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
     * 删除供应商提现记录
     * 
     * @param id 供应商提现记录主键
     * @return 结果
     */
    public int deleteSupplierWithdrawalInfoById(Long id);

    /**
     * 批量删除供应商提现记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierWithdrawalInfoByIds(Long[] ids);
}
