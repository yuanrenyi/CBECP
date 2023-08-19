package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.CatalogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BondedWarehouseInfoMapper;
import com.ruoyi.system.domain.BondedWarehouseInfo;
import com.ruoyi.system.service.IBondedWarehouseInfoService;

/**
 * 保税仓Service业务层处理
 */
@Service
public class BondedWarehouseInfoServiceImpl implements IBondedWarehouseInfoService
{
    @Autowired
    private BondedWarehouseInfoMapper bondedWarehouseInfoMapper;

    /**
     * 查询保税仓
     *
     * @param id 保税仓主键
     * @return 保税仓
     */
    @Override
    public BondedWarehouseInfo selectBondedWarehouseInfoById(Long id)
    {
        return bondedWarehouseInfoMapper.selectBondedWarehouseInfoById(id);
    }

    /**
     * 查询保税仓列表
     *
     * @param bondedWarehouseInfo 保税仓
     * @return 保税仓
     */
    @Override
    public List<BondedWarehouseInfo> selectBondedWarehouseInfoList(BondedWarehouseInfo bondedWarehouseInfo)
    {
        return bondedWarehouseInfoMapper.selectBondedWarehouseInfoList(bondedWarehouseInfo);
    }

    @Override
    public List<BondedWarehouseInfo> getBondedWarehouseInfoAll(BondedWarehouseInfo bondedWarehouseInfo) {
        List<BondedWarehouseInfo> allBwInfo = bondedWarehouseInfoMapper.selectBondedWarehouseInfoList(new BondedWarehouseInfo());

        if(ObjectUtil.isNull(allBwInfo) || allBwInfo.size() <= 0) {
            return new ArrayList<>();
        }

        List<BondedWarehouseInfo> result = allBwInfo.stream().filter(bw->bw.getBondedWarehouseLevel() == 1).collect(Collectors.toList());

        for(BondedWarehouseInfo bwInfo : result) {
            setSubBwInfoList(bwInfo, allBwInfo);
        }

        return result;
    }

    public void setSubBwInfoList(BondedWarehouseInfo currentBw, List<BondedWarehouseInfo> allBw) {
        List<BondedWarehouseInfo> subList = allBw.stream().filter(c ->
                ObjectUtil.equal(c.getParentId(), currentBw.getId())
        ).collect(Collectors.toList());
        if(ObjectUtil.isNotNull(subList) && subList.size() > 0) {
            for(BondedWarehouseInfo subBw : subList) {
                setSubBwInfoList(subBw, allBw);
            }
        }
        currentBw.setChildren(subList);
    }





    /**
     * 新增保税仓
     *
     * @param bondedWarehouseInfo 保税仓
     * @return 结果
     */
    @Override
    public int insertBondedWarehouseInfo(BondedWarehouseInfo bondedWarehouseInfo)
    {
        if(bondedWarehouseInfo.getParentId() == 0L) {
            bondedWarehouseInfo.setBondedWarehouseLevel(1);
        } else {
            BondedWarehouseInfo parentBw = bondedWarehouseInfoMapper.selectBondedWarehouseInfoById(bondedWarehouseInfo.getParentId());
            bondedWarehouseInfo.setBondedWarehouseLevel(parentBw.getBondedWarehouseLevel() + 1);
        }
        bondedWarehouseInfo.setCreateTime(DateUtils.getNowDate());
        return bondedWarehouseInfoMapper.insertBondedWarehouseInfo(bondedWarehouseInfo);
    }

    /**
     * 修改保税仓
     *
     * @param bondedWarehouseInfo 保税仓
     * @return 结果
     */
    @Override
    public int updateBondedWarehouseInfo(BondedWarehouseInfo bondedWarehouseInfo)
    {
        if(bondedWarehouseInfo.getParentId() == 0L) {
            bondedWarehouseInfo.setBondedWarehouseLevel(1);
        } else {
            BondedWarehouseInfo parentBw = bondedWarehouseInfoMapper.selectBondedWarehouseInfoById(bondedWarehouseInfo.getParentId());
            bondedWarehouseInfo.setBondedWarehouseLevel(parentBw.getBondedWarehouseLevel() + 1);
        }
        bondedWarehouseInfo.setUpdateTime(DateUtils.getNowDate());
        return bondedWarehouseInfoMapper.updateBondedWarehouseInfo(bondedWarehouseInfo);
    }

    /**
     * 批量删除保税仓
     *
     * @param ids 需要删除的保税仓主键
     * @return 结果
     */
    @Override
    public int deleteBondedWarehouseInfoByIds(Long[] ids)
    {
        return bondedWarehouseInfoMapper.deleteBondedWarehouseInfoByIds(ids);
    }

    /**
     * 删除保税仓信息
     *
     * @param id 保税仓主键
     * @return 结果
     */
    @Override
    public int deleteBondedWarehouseInfoById(Long id)
    {
        return bondedWarehouseInfoMapper.deleteBondedWarehouseInfoById(id);
    }
}
