package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SupplierInfoMapper;
import com.ruoyi.system.domain.SupplierInfo;
import com.ruoyi.system.service.ISupplierInfoService;

/**
 * 供应商信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class SupplierInfoServiceImpl implements ISupplierInfoService 
{
    @Autowired
    private SupplierInfoMapper supplierInfoMapper;

    /**
     * 查询供应商信息
     * 
     * @param id 供应商信息主键
     * @return 供应商信息
     */
    @Override
    public SupplierInfo selectSupplierInfoById(String id)
    {
        return supplierInfoMapper.selectSupplierInfoById(id);
    }

    /**
     * 查询供应商信息列表
     * 
     * @param supplierInfo 供应商信息
     * @return 供应商信息
     */
    @Override
    public List<SupplierInfo> selectSupplierInfoList(SupplierInfo supplierInfo)
    {
        return supplierInfoMapper.selectSupplierInfoList(supplierInfo);
    }

    /**
     * 新增供应商信息
     * 
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    @Override
    public int insertSupplierInfo(SupplierInfo supplierInfo)
    {
        supplierInfo.setId(IdUtils.fastSimpleUUID());
        supplierInfo.setCreateTime(DateUtils.getNowDate());
        return supplierInfoMapper.insertSupplierInfo(supplierInfo);
    }

    /**
     * 修改供应商信息
     * 
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    @Override
    public int updateSupplierInfo(SupplierInfo supplierInfo)
    {
        supplierInfo.setUpdateTime(DateUtils.getNowDate());
        return supplierInfoMapper.updateSupplierInfo(supplierInfo);
    }

    /**
     * 批量删除供应商信息
     * 
     * @param ids 需要删除的供应商信息主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoByIds(String[] ids)
    {
        return supplierInfoMapper.deleteSupplierInfoByIds(ids);
    }

    /**
     * 删除供应商信息信息
     * 
     * @param id 供应商信息主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoById(String id)
    {
        return supplierInfoMapper.deleteSupplierInfoById(id);
    }
}
