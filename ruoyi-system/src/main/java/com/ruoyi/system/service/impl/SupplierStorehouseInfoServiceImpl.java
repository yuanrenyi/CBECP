package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.CNInfoMapper;
import com.ruoyi.system.mapper.GoodsInfoMapper;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SupplierStorehouseInfoMapper;
import com.ruoyi.system.service.ISupplierStorehouseInfoService;

/**
 * 供应商仓库信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class SupplierStorehouseInfoServiceImpl implements ISupplierStorehouseInfoService 
{
    @Autowired
    private SupplierStorehouseInfoMapper supplierStorehouseInfoMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询供应商仓库信息
     * 
     * @param id 供应商仓库信息主键
     * @return 供应商仓库信息
     */
    @Override
    public SupplierStorehouseInfo selectSupplierStorehouseInfoById(String id)
    {
        return supplierStorehouseInfoMapper.selectSupplierStorehouseInfoById(id);
    }

    /**
     * 查询供应商仓库信息列表
     * 
     * @param supplierStorehouseInfo 供应商仓库信息
     * @return 供应商仓库信息
     */
    @Override
    public List<SupplierStorehouseInfo> selectSupplierStorehouseInfoList(SupplierStorehouseInfo supplierStorehouseInfo)
    {
        return supplierStorehouseInfoMapper.selectSupplierStorehouseInfoList(supplierStorehouseInfo);
    }

    @Override
    public List<SupplierStorehouseInfo> selectSupplierStorehouseInfoExportList(SupplierStorehouseInfo supplierStorehouseInfo) {
        List<SupplierStorehouseInfo> list = supplierStorehouseInfoMapper.selectSupplierStorehouseInfoList(supplierStorehouseInfo);

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("storehouse_type");
        List<SysDictData> storehouseTypeDict = sysDictDataService.selectDictDataList(sysDictData);
        Map<String, String> storehouseTypeMap = storehouseTypeDict.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

        sysDictData.setDictType("storehouse_postal_tax_type");
        List<SysDictData> postalTaxDict = sysDictDataService.selectDictDataList(sysDictData);
        Map<String, String> postalTaxMap = postalTaxDict.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

        sysDictData.setDictType("storehouse_if_split_order");
        List<SysDictData> ifSplitOrderDict = sysDictDataService.selectDictDataList(sysDictData);
        Map<String, String> ifSplitOrderMap = ifSplitOrderDict.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));


        list.forEach(s -> {
            if(StringUtils.isNotEmpty(s.getStorehouseType()) && storehouseTypeMap.containsKey(s.getStorehouseType())) {
                s.setStorehouseType(storehouseTypeMap.get(s.getStorehouseType()));
            }

            if(ObjectUtil.isNotNull(s.getPostalTaxType()) && postalTaxMap.containsKey(s.getPostalTaxType().toString())) {
                s.setPostalTaxTypeStr(postalTaxMap.get(s.getPostalTaxType().toString()));
            }

            if(ObjectUtil.isNotNull(s.getIfSplitOrder()) && ifSplitOrderMap.containsKey(s.getIfSplitOrder().toString())) {
                s.setIfSplitOrderStr(ifSplitOrderMap.get(s.getIfSplitOrder().toString()));
            }

        });

        return list;
    }

    /**
     * 新增供应商仓库信息
     * 
     * @param supplierStorehouseInfo 供应商仓库信息
     * @return 结果
     */
    @Override
    public int insertSupplierStorehouseInfo(SupplierStorehouseInfo supplierStorehouseInfo)
    {
        supplierStorehouseInfo.setId(IdUtils.fastSimpleUUID());
        supplierStorehouseInfo.setCreateTime(DateUtils.getNowDate());
        return supplierStorehouseInfoMapper.insertSupplierStorehouseInfo(supplierStorehouseInfo);
    }

    /**
     * 修改供应商仓库信息
     * 
     * @param supplierStorehouseInfo 供应商仓库信息
     * @return 结果
     */
    @Override
    public int updateSupplierStorehouseInfo(SupplierStorehouseInfo supplierStorehouseInfo)
    {
        supplierStorehouseInfo.setUpdateTime(DateUtils.getNowDate());

        /**
         * 修改此仓库下所有商品的运费信息与仓库的一致
         */
        boolean needUpdateGoodsInfo = false;
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setStorehouseId(supplierStorehouseInfo.getId());
        if(ObjectUtil.isNotNull(supplierStorehouseInfo.getFreightAmount())) {
            needUpdateGoodsInfo = true;
            goodsInfo.setFreightAmount(supplierStorehouseInfo.getFreightAmount());
        }
        if(ObjectUtil.isNotNull(supplierStorehouseInfo.getFreightRenewalAmount())) {
            needUpdateGoodsInfo = true;
            goodsInfo.setFreightRenewalAmount(supplierStorehouseInfo.getFreightRenewalAmount());
        }
        if(needUpdateGoodsInfo) {
            goodsInfoMapper.updateGoodsFreightByStorehouse(goodsInfo);
        }

        return supplierStorehouseInfoMapper.updateSupplierStorehouseInfo(supplierStorehouseInfo);
    }

    /**
     * 批量删除供应商仓库信息
     * 
     * @param ids 需要删除的供应商仓库信息主键
     * @return 结果
     */
    @Override
    public int deleteSupplierStorehouseInfoByIds(String[] ids)
    {
        return supplierStorehouseInfoMapper.deleteSupplierStorehouseInfoByIds(ids);
    }

    /**
     * 删除供应商仓库信息信息
     * 
     * @param id 供应商仓库信息主键
     * @return 结果
     */
    @Override
    public int deleteSupplierStorehouseInfoById(String id)
    {
        return supplierStorehouseInfoMapper.deleteSupplierStorehouseInfoById(id);
    }
}
