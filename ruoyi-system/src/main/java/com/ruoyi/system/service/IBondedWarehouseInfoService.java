package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BondedWarehouseInfo;

/**
 * 保税仓Service接口
 */
public interface IBondedWarehouseInfoService
{
    /**
     * 查询保税仓
     *
     * @param id 保税仓主键
     * @return 保税仓
     */
    public BondedWarehouseInfo selectBondedWarehouseInfoById(Long id);

    /**
     * 查询保税仓列表
     *
     * @param bondedWarehouseInfo 保税仓
     * @return 保税仓集合
     */
    public List<BondedWarehouseInfo> selectBondedWarehouseInfoList(BondedWarehouseInfo bondedWarehouseInfo);

    /**
     * 查询保税仓列表（包含递归子集）
     * @param bondedWarehouseInfo
     * @return
     */
    public List<BondedWarehouseInfo> getBondedWarehouseInfoAll(BondedWarehouseInfo bondedWarehouseInfo);

    /**
     * 新增保税仓
     *
     * @param bondedWarehouseInfo 保税仓
     * @return 结果
     */
    public int insertBondedWarehouseInfo(BondedWarehouseInfo bondedWarehouseInfo);

    /**
     * 修改保税仓
     *
     * @param bondedWarehouseInfo 保税仓
     * @return 结果
     */
    public int updateBondedWarehouseInfo(BondedWarehouseInfo bondedWarehouseInfo);

    /**
     * 批量删除保税仓
     *
     * @param ids 需要删除的保税仓主键集合
     * @return 结果
     */
    public int deleteBondedWarehouseInfoByIds(Long[] ids);

    /**
     * 删除保税仓信息
     *
     * @param id 保税仓主键
     * @return 结果
     */
    public int deleteBondedWarehouseInfoById(Long id);
}
