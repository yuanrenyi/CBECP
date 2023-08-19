package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.GoodsPicInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 商品图片Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface IGoodsPicInfoService 
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
     * 批量删除商品图片
     * 
     * @param ids 需要删除的商品图片主键集合
     * @return 结果
     */
    public int deleteGoodsPicInfoByIds(Long[] ids);

    /**
     * 删除商品图片信息
     * 
     * @param id 商品图片主键
     * @return 结果
     */
    public int deleteGoodsPicInfoById(Long id);

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
    public int deleteByBarcodeAndPicversion(String barcode,String picversion);

    List<GoodsPicInfo> hisPicInfoList();

    List<GoodsPicInfo> hisPicInfoList2();
}
