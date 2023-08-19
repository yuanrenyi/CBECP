package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.GoodsPicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品图片Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Mapper
@Repository("goodsPicInfoMapper")
public interface GoodsPicInfoMapper 
{
    /**
     * 查询商品图片
     * 
     * @param id 商品图片主键
     * @return 商品图片
     */
    public GoodsPicInfo selectGoodsPicInfoById(Long id);

    /**
     * 查询商品图片列表
     * 
     * @param goodsPicInfo 商品图片
     * @return 商品图片集合
     */
    public List<GoodsPicInfo> selectGoodsPicInfoList(GoodsPicInfo goodsPicInfo);

    /**
     * 新增商品图片
     * 
     * @param goodsPicInfo 商品图片
     * @return 结果
     */
    public int insertGoodsPicInfo(GoodsPicInfo goodsPicInfo);

    /**
     * 修改商品图片
     * 
     * @param goodsPicInfo 商品图片
     * @return 结果
     */
    public int updateGoodsPicInfo(GoodsPicInfo goodsPicInfo);

    /**
     * 删除商品图片
     * 
     * @param id 商品图片主键
     * @return 结果
     */
    public int deleteGoodsPicInfoById(Long id);

    /**
     * 根据商品ID删除商品图片
     * @param goodsId
     * @return
     */
    public int deleteGoodsPicInfoByGoodsId(String goodsId);


    public int deleteGoodsPicInfo(@Param("goodsId") String goodsId, @Param("picVersion") String picVersion);

    /**
     * 批量删除商品图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsPicInfoByIds(Long[] ids);

    /**
     * 获取商品主图
     * @param goodsBarcode
     * @return
     */
    public GoodsPicInfo getGoodsMainPic(String goodsBarcode);

    /**
     * 根据条码和路径删除图片信息
     * @param barcode
     * @param picversion
     * @return
     */
    public int deleteByBarcodeAndPicversion(@Param("barcode") String barcode, @Param("picversion") String picversion);

    List<GoodsPicInfo> hisPicInfoList();


    List<GoodsPicInfo> hisPicInfoList2();
}
