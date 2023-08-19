package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GoodsInfo;
import com.ruoyi.system.domain.sellergoods.SellerGoodsDetail;
import com.ruoyi.system.domain.sellergoods.SellerGoodsInfo;
import com.ruoyi.system.domain.sellergoods.SellerGoodsStorehouseInfo;
import com.ruoyi.system.domain.vo.GoodsInfoStockUpdateVo;
import com.ruoyi.system.domain.vo.GoodsInfoUploadUpdateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Mapper
@Repository("goodsInfoMapper")
public interface GoodsInfoMapper 
{
    /**
     * 查询商品信息
     * 
     * @param id 商品信息主键
     * @return 商品信息
     */
    public GoodsInfo selectGoodsInfoById(String id);

    /**
     * 查询商品信息列表
     * 
     * @param goodsInfo 商品信息
     * @return 商品信息集合
     */
    public List<GoodsInfo> selectGoodsInfoList(GoodsInfo goodsInfo);

    /**
     * 根据商品编码和仓库ID查询商品信息
     * @param goodsCode
     * @param storehouseId
     * @return
     */
    public List<GoodsInfo> selectGoodsInfoByGoodsCodeAndStorehouseId(@Param("goodsCode") String goodsCode, @Param("storehouseId") String storehouseId);

    /**
     * 根据商品ids查询商品列表
     * @param ids
     * @return
     */
    public List<GoodsInfo> selectGoodsInfoByIds(@Param("ids") List<String> ids);

    /**
     * 新增商品信息
     * 
     * @param goodsInfo 商品信息
     * @return 结果
     */
    public int insertGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 更新商品的税率信息
     * @param id
     * @return
     */
    public int updateGoodsTaxRateById(@Param("id") String id);

    /**
     * 修改商品信息
     * 
     * @param goodsInfo 商品信息
     * @return 结果
     */
    public int updateGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 删除商品信息
     * 
     * @param id 商品信息主键
     * @return 结果
     */
    public int deleteGoodsInfoById(String id);

    /**
     * 批量删除商品信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsInfoByIds(String[] ids);

    /**
     * 平台方上架/下架商品
     * @param goodsInfo
     * @return
     */
    public int platOnshelf(GoodsInfo goodsInfo);

    /**
     * 销售分查看的商品列表
     * @param goodsInfo
     * @return
     */
    public List<SellerGoodsInfo> sellerGoodsList(GoodsInfo goodsInfo);

    /**
     * 销售方查看的商品列表中的仓库信息
     * @param goodsBarcode
     * @return
     */
    public List<SellerGoodsStorehouseInfo> sellerGoodsStorehouseInfoList(@Param("goodsBarcode") String goodsBarcode);

    /**
     * 销售方查看的商品详情主体信息
     * @param goodsBarcode
     * @return
     */
    public SellerGoodsDetail sellerGoodsDetail(@Param("goodsBarcode") String goodsBarcode);

    /**
     * 销售方查看的商品详情中的仓库信息
     * @param goodsBarcode
     * @return
     */
    public List<SellerGoodsStorehouseInfo> sellerGoodsStorehouseInfoDetail(@Param("goodsBarcode") String goodsBarcode, @Param("sellerId") String sellerId);


    /**
     * 修改仓库运费信息时同步修改其仓库的所有商品运费
     * @param goodsInfo
     */
    public int updateGoodsFreightByStorehouse(GoodsInfo goodsInfo);

    /**
     * 根据外部编码更新商品信息
     * @param updateVo
     */
    public void updateGoodsInfoByOutCode(GoodsInfoUploadUpdateVo updateVo);

    /**
     * 根据商品编码更新库存
     * @param stockUpdateVo
     */
    public void updateGoodsStockByGoodsCode(GoodsInfoStockUpdateVo stockUpdateVo);
}
