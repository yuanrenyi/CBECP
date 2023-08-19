package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CatalogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CatalogInfoMapper
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
     * 删除商品分类信息
     *
     * @param id 商品分类信息主键
     * @return 结果
     */
    public int deleteCatalogInfoById(Long id);

    /**
     * 批量删除商品分类信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCatalogInfoByIds(Long[] ids);
}
