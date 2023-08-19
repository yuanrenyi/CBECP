package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CatalogInfo;

public interface ICatalogInfoService
{
    /**
     * 查询商品分类信息
     *
     * @param id 商品分类信息主键
     * @return 商品分类信息
     */
    public CatalogInfo selectCatalogInfoById(Long id);

    /**
     * 查询商品分类信息列表
     *
     * @param catalogInfo 商品分类信息
     * @return 商品分类信息集合
     */
    public List<CatalogInfo> selectCatalogInfoList(CatalogInfo catalogInfo);

    /**
     * 查询所有商品分类(包含递归子集的完整信息)
     */
    public List<CatalogInfo> getCatalogListAll(CatalogInfo catalogInfo);

    /**
     * 新增商品分类信息
     *
     * @param catalogInfo 商品分类信息
     * @return 结果
     */
    public int insertCatalogInfo(CatalogInfo catalogInfo);

    /**
     * 修改商品分类信息
     *
     * @param catalogInfo 商品分类信息
     * @return 结果
     */
    public int updateCatalogInfo(CatalogInfo catalogInfo);

    /**
     * 批量删除商品分类信息
     *
     * @param ids 需要删除的商品分类信息主键集合
     * @return 结果
     */
    public int deleteCatalogInfoByIds(Long[] ids);

    /**
     * 删除商品分类信息信息
     *
     * @param id 商品分类信息主键
     * @return 结果
     */
    public int deleteCatalogInfoById(Long id);
}
