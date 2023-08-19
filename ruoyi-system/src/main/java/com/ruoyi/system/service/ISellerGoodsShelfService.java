package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SellerGoodsShelf;

/**
 * 销售方上架商品Service接口
 *
 * @author ruoyi
 * @date 2023-02-10
 */
public interface ISellerGoodsShelfService
{
    /**
     * 查询销售方上架商品
     *
     * @param id 销售方上架商品主键
     * @return 销售方上架商品
     */
    public SellerGoodsShelf selectSellerGoodsShelfById(Long id);

    /**
     * 查询销售方上架商品列表
     *
     * @param sellerGoodsShelf 销售方上架商品
     * @return 销售方上架商品集合
     */
    public List<SellerGoodsShelf> selectSellerGoodsShelfList(SellerGoodsShelf sellerGoodsShelf);

    /**
     * 新增销售方上架商品
     *
     * @param sellerGoodsShelf 销售方上架商品
     * @return 结果
     */
    public int insertSellerGoodsShelf(SellerGoodsShelf sellerGoodsShelf);

    /**
     * 修改销售方上架商品
     *
     * @param sellerGoodsShelf 销售方上架商品
     * @return 结果
     */
    public int updateSellerGoodsShelf(SellerGoodsShelf sellerGoodsShelf);

    /**
     * 批量删除销售方上架商品
     *
     * @param ids 需要删除的销售方上架商品主键集合
     * @return 结果
     */
    public int deleteSellerGoodsShelfByIds(Long[] ids);

    /**
     * 删除销售方上架商品信息
     *
     * @param id 销售方上架商品主键
     * @return 结果
     */
    public int deleteSellerGoodsShelfById(Long id);

    /**
     * 销售方下架商品
     * @param goodsId
     * @param sellerId
     * @return
     */
    public int deleteByGoodsAndSeller(String goodsId, String sellerId);
}
