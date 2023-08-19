package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SellerGoodsShelfMapper;
import com.ruoyi.system.domain.SellerGoodsShelf;
import com.ruoyi.system.service.ISellerGoodsShelfService;

/**
 * 销售方上架商品Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-10
 */
@Service
public class SellerGoodsShelfServiceImpl implements ISellerGoodsShelfService
{
    @Autowired
    private SellerGoodsShelfMapper sellerGoodsShelfMapper;

    /**
     * 查询销售方上架商品
     *
     * @param id 销售方上架商品主键
     * @return 销售方上架商品
     */
    @Override
    public SellerGoodsShelf selectSellerGoodsShelfById(Long id)
    {
        return sellerGoodsShelfMapper.selectSellerGoodsShelfById(id);
    }

    /**
     * 查询销售方上架商品列表
     *
     * @param sellerGoodsShelf 销售方上架商品
     * @return 销售方上架商品
     */
    @Override
    public List<SellerGoodsShelf> selectSellerGoodsShelfList(SellerGoodsShelf sellerGoodsShelf)
    {
        return sellerGoodsShelfMapper.selectSellerGoodsShelfList(sellerGoodsShelf);
    }

    /**
     * 新增销售方上架商品
     *
     * @param sellerGoodsShelf 销售方上架商品
     * @return 结果
     */
    @Override
    public int insertSellerGoodsShelf(SellerGoodsShelf sellerGoodsShelf)
    {
        sellerGoodsShelf.setCreateTime(DateUtils.getNowDate());
        return sellerGoodsShelfMapper.insertSellerGoodsShelf(sellerGoodsShelf);
    }

    /**
     * 修改销售方上架商品
     *
     * @param sellerGoodsShelf 销售方上架商品
     * @return 结果
     */
    @Override
    public int updateSellerGoodsShelf(SellerGoodsShelf sellerGoodsShelf)
    {
        return sellerGoodsShelfMapper.updateSellerGoodsShelf(sellerGoodsShelf);
    }

    /**
     * 批量删除销售方上架商品
     *
     * @param ids 需要删除的销售方上架商品主键
     * @return 结果
     */
    @Override
    public int deleteSellerGoodsShelfByIds(Long[] ids)
    {
        return sellerGoodsShelfMapper.deleteSellerGoodsShelfByIds(ids);
    }

    /**
     * 删除销售方上架商品信息
     *
     * @param id 销售方上架商品主键
     * @return 结果
     */
    @Override
    public int deleteSellerGoodsShelfById(Long id)
    {
        return sellerGoodsShelfMapper.deleteSellerGoodsShelfById(id);
    }

    /**
     * 销售方下架商品
     * @param goodsId
     * @param sellerId
     * @return
     */
    @Override
    public int deleteByGoodsAndSeller(String goodsId, String sellerId) {
        return sellerGoodsShelfMapper.deleteByGoodsAndSeller(goodsId, sellerId);
    }
}
