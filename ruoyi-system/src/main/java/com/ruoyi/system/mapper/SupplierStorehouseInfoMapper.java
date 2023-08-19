package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SupplierStorehouseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 供应商仓库信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Mapper
@Repository("SupplierStorehouseInfoMapper")
public interface SupplierStorehouseInfoMapper 
{
    /**
     * 查询供应商仓库信息
     * 
     * @param id 供应商仓库信息主键
     * @return 供应商仓库信息
     */
    public SupplierStorehouseInfo selectSupplierStorehouseInfoById(String id);

    /**
     * 查询供应商仓库信息列表
     * 
     * @param supplierStorehouseInfo 供应商仓库信息
     * @return 供应商仓库信息集合
     */
    public List<SupplierStorehouseInfo> selectSupplierStorehouseInfoList(SupplierStorehouseInfo supplierStorehouseInfo);

    /**
     * 新增供应商仓库信息
     * 
     * @param supplierStorehouseInfo 供应商仓库信息
     * @return 结果
     */
    public int insertSupplierStorehouseInfo(SupplierStorehouseInfo supplierStorehouseInfo);

    /**
     * 修改供应商仓库信息
     * 
     * @param supplierStorehouseInfo 供应商仓库信息
     * @return 结果
     */
    public int updateSupplierStorehouseInfo(SupplierStorehouseInfo supplierStorehouseInfo);

    /**
     * 删除供应商仓库信息
     * 
     * @param id 供应商仓库信息主键
     * @return 结果
     */
    public int deleteSupplierStorehouseInfoById(String id);

    /**
     * 批量删除供应商仓库信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierStorehouseInfoByIds(String[] ids);
}
