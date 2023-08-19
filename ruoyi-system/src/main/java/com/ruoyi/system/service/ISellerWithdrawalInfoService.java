package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SellerWithdrawalInfo;

/**
 * 销售方提现记录Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface ISellerWithdrawalInfoService 
{
    /**
     * 查询销售方提现记录
     * 
     * @param id 销售方提现记录主键
     * @return 销售方提现记录
     */
    public SellerWithdrawalInfo selectSellerWithdrawalInfoById(Long id);

    /**
     * 查询销售方提现记录列表
     * 
     * @param sellerWithdrawalInfo 销售方提现记录
     * @return 销售方提现记录集合
     */
    public List<SellerWithdrawalInfo> selectSellerWithdrawalInfoList(SellerWithdrawalInfo sellerWithdrawalInfo);

    /**
     * 新增销售方提现记录
     * 
     * @param sellerWithdrawalInfo 销售方提现记录
     * @return 结果
     */
    public int insertSellerWithdrawalInfo(SellerWithdrawalInfo sellerWithdrawalInfo);

    /**
     * 修改销售方提现记录
     * 
     * @param sellerWithdrawalInfo 销售方提现记录
     * @return 结果
     */
    public int updateSellerWithdrawalInfo(SellerWithdrawalInfo sellerWithdrawalInfo);

    /**
     * 批量删除销售方提现记录
     * 
     * @param ids 需要删除的销售方提现记录主键集合
     * @return 结果
     */
    public int deleteSellerWithdrawalInfoByIds(Long[] ids);

    /**
     * 删除销售方提现记录信息
     * 
     * @param id 销售方提现记录主键
     * @return 结果
     */
    public int deleteSellerWithdrawalInfoById(Long id);
}
