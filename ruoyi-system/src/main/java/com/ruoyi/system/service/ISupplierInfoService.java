package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SupplierInfo;

/**
 * 供应商信息Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface ISupplierInfoService 
{
    /**
     * 查询供应商信息
     * 
     * @param id 供应商信息主键
     * @return 供应商信息
     */
    public SupplierInfo selectSupplierInfoById(String id);

    /**
     * 查询供应商信息列表
     * 
     * @param supplierInfo 供应商信息
     * @return 供应商信息集合
     */
    public List<SupplierInfo> selectSupplierInfoList(SupplierInfo supplierInfo);

    /**
     * 新增供应商信息
     * 
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    public int insertSupplierInfo(SupplierInfo supplierInfo);

    /**
     * 修改供应商信息
     * 
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    public int updateSupplierInfo(SupplierInfo supplierInfo);

    /**
     * 批量删除供应商信息
     * 
     * @param ids 需要删除的供应商信息主键集合
     * @return 结果
     */
    public int deleteSupplierInfoByIds(String[] ids);

    /**
     * 删除供应商信息信息
     * 
     * @param id 供应商信息主键
     * @return 结果
     */
    public int deleteSupplierInfoById(String id);
}
