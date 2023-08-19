package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CatalogInfoMapper;
import com.ruoyi.system.domain.CatalogInfo;
import com.ruoyi.system.service.ICatalogInfoService;

/**
 * 商品分类信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-13
 */
@Service
public class CatalogInfoServiceImpl implements ICatalogInfoService
{
    @Autowired
    private CatalogInfoMapper catalogInfoMapper;

    /**
     * 查询商品分类信息
     *
     * @param id 商品分类信息主键
     * @return 商品分类信息
     */
    @Override
    public CatalogInfo selectCatalogInfoById(Long id)
    {
        return catalogInfoMapper.selectCatalogInfoById(id);
    }

    /**
     * 查询商品分类信息列表
     *
     * @param catalogInfo 商品分类信息
     * @return 商品分类信息
     */
    @Override
    public List<CatalogInfo> selectCatalogInfoList(CatalogInfo catalogInfo)
    {
        return catalogInfoMapper.selectCatalogInfoList(catalogInfo);
    }

    /**
     * 查询所有商品分类(包含递归子集的完整信息)
     */
    @Override
    public List<CatalogInfo> getCatalogListAll(CatalogInfo query) {

        List<CatalogInfo> allCatlogInfo = catalogInfoMapper.selectCatalogInfoList(new CatalogInfo());

        if(ObjectUtil.isNull(allCatlogInfo) || allCatlogInfo.size() <= 0) {
            return new ArrayList<>();
        }

        List<CatalogInfo> result = allCatlogInfo.stream().filter(catalog->catalog.getCatalogLevel() == 1).collect(Collectors.toList());

        for(CatalogInfo catalogInfo : result) {
            setSubCatalogInfoList(catalogInfo, allCatlogInfo);
        }

        return result;
    }


    public void setSubCatalogInfoList(CatalogInfo currentCatalog, List<CatalogInfo> allCatalogInfo) {
        List<CatalogInfo> subList = allCatalogInfo.stream().filter(c ->
            ObjectUtil.equal(c.getParentId(), currentCatalog.getId())
        ).collect(Collectors.toList());
        if(ObjectUtil.isNotNull(subList) && subList.size() > 0) {
            for(CatalogInfo subCatalog : subList) {
                setSubCatalogInfoList(subCatalog, allCatalogInfo);
            }
        }
        currentCatalog.setChildren(subList);
    }


    /**
     * 新增商品分类信息
     *
     * @param catalogInfo 商品分类信息
     * @return 结果
     */
    @Override
    public int insertCatalogInfo(CatalogInfo catalogInfo)
    {
        if(catalogInfo.getParentId() == 0L) {
            catalogInfo.setCatalogLevel(1);
        } else {
            CatalogInfo parentCatalog = catalogInfoMapper.selectCatalogInfoById(catalogInfo.getParentId());
            catalogInfo.setCatalogLevel(parentCatalog.getCatalogLevel() + 1);
        }
        catalogInfo.setCreateTime(DateUtils.getNowDate());
        return catalogInfoMapper.insertCatalogInfo(catalogInfo);
    }

    /**
     * 修改商品分类信息
     *
     * @param catalogInfo 商品分类信息
     * @return 结果
     */
    @Override
    public int updateCatalogInfo(CatalogInfo catalogInfo)
    {
        if(catalogInfo.getParentId() == 0L) {
            catalogInfo.setCatalogLevel(1);
        } else {
            CatalogInfo parentCatalog = catalogInfoMapper.selectCatalogInfoById(catalogInfo.getParentId());
            catalogInfo.setCatalogLevel(parentCatalog.getCatalogLevel() + 1);
        }
        catalogInfo.setUpdateTime(DateUtils.getNowDate());
        return catalogInfoMapper.updateCatalogInfo(catalogInfo);
    }

    /**
     * 批量删除商品分类信息
     *
     * @param ids 需要删除的商品分类信息主键
     * @return 结果
     */
    @Override
    public int deleteCatalogInfoByIds(Long[] ids)
    {
        return catalogInfoMapper.deleteCatalogInfoByIds(ids);
    }

    /**
     * 删除商品分类信息信息
     *
     * @param id 商品分类信息主键
     * @return 结果
     */
    @Override
    public int deleteCatalogInfoById(Long id)
    {
        return catalogInfoMapper.deleteCatalogInfoById(id);
    }
}
