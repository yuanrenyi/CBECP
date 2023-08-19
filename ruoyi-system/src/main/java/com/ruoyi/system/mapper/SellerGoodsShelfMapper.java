package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SellerGoodsShelf;
import com.ruoyi.system.domain.vo.OnplatSellerInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 销售方上架商品Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-10
 */
@Mapper
@Repository("SellerGoodsShelfMapper")
public interface SellerGoodsShelfMapper
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
     * 删除销售方上架商品
     *
     * @param id 销售方上架商品主键
     * @return 结果
     */
    public int deleteSellerGoodsShelfById(Long id);

    /**
     * 批量删除销售方上架商品
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSellerGoodsShelfByIds(Long[] ids);

    /**
     * 销售方下架商品
     * @param goodsId
     * @param sellerId
     * @return
     */
    public int deleteByGoodsAndSeller(@Param("goodsId") String goodsId, @Param("sellerId") String sellerId);

    /**
     * 根据商品id查询已上架该商品的销售方
     * @param goodsId
     * @return
     */
    public List<OnplatSellerInfoVo> selectOnplatSellerInfo(@Param("goodsId") String goodsId);
}
