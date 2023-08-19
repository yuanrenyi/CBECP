package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GoodsStockInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 商品锁库存Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Mapper
@Repository("goodsStockInfoMapper")
public interface GoodsStockInfoMapper 
{
    /**
     * 查询商品锁库存
     * 
     * @param id 商品锁库存主键
     * @return 商品锁库存
     */
    public GoodsStockInfo selectGoodsStockInfoById(Long id);

    /**
     * 查询商品锁库存列表
     * 
     * @param goodsStockInfo 商品锁库存
     * @return 商品锁库存集合
     */
    public List<GoodsStockInfo> selectGoodsStockInfoList(GoodsStockInfo goodsStockInfo);

    /**
     * 新增商品锁库存
     * 
     * @param goodsStockInfo 商品锁库存
     * @return 结果
     */
    public int insertGoodsStockInfo(GoodsStockInfo goodsStockInfo);

    /**
     * 修改商品锁库存
     * 
     * @param goodsStockInfo 商品锁库存
     * @return 结果
     */
    public int updateGoodsStockInfo(GoodsStockInfo goodsStockInfo);

    /**
     * 删除商品锁库存
     * 
     * @param id 商品锁库存主键
     * @return 结果
     */
    public int deleteGoodsStockInfoById(Long id);

    /**
     * 批量删除商品锁库存
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsStockInfoByIds(Long[] ids);
}
