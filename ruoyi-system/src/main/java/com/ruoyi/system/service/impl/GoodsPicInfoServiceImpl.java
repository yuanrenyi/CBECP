package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GoodsPicInfoMapper;
import com.ruoyi.system.domain.GoodsPicInfo;
import com.ruoyi.system.service.IGoodsPicInfoService;

/**
 * 商品图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class GoodsPicInfoServiceImpl implements IGoodsPicInfoService 
{
    @Autowired
    private GoodsPicInfoMapper goodsPicInfoMapper;

    /**
     * 查询商品图片
     * 
     * @param id 商品图片主键
     * @return 商品图片
     */
    @Override
    public GoodsPicInfo selectGoodsPicInfoById(Long id)
    {
        return goodsPicInfoMapper.selectGoodsPicInfoById(id);
    }

    /**
     * 查询商品图片列表
     * 
     * @param goodsPicInfo 商品图片
     * @return 商品图片
     */
    @Override
    public List<GoodsPicInfo> selectGoodsPicInfoList(GoodsPicInfo goodsPicInfo)
    {
        return goodsPicInfoMapper.selectGoodsPicInfoList(goodsPicInfo);
    }

    /**
     * 新增商品图片
     * 
     * @param goodsPicInfo 商品图片
     * @return 结果
     */
    @Override
    public int insertGoodsPicInfo(GoodsPicInfo goodsPicInfo)
    {
        goodsPicInfo.setCreateTime(DateUtils.getNowDate());
        String url = goodsPicInfo.getPicUrl();
        goodsPicInfo.setSortNo(FileUtils.getNameNotSuffix(url));
        if(StringUtils.isNotEmpty(goodsPicInfo.getPlatBarcode())) {
            goodsPicInfo.setGoodsId(goodsPicInfo.getPlatBarcode());
        }
        return goodsPicInfoMapper.insertGoodsPicInfo(goodsPicInfo);
    }

    /**
     * 修改商品图片
     * 
     * @param goodsPicInfo 商品图片
     * @return 结果
     */
    @Override
    public int updateGoodsPicInfo(GoodsPicInfo goodsPicInfo)
    {
        goodsPicInfo.setUpdateTime(DateUtils.getNowDate());
        return goodsPicInfoMapper.updateGoodsPicInfo(goodsPicInfo);
    }

    /**
     * 批量删除商品图片
     * 
     * @param ids 需要删除的商品图片主键
     * @return 结果
     */
    @Override
    public int deleteGoodsPicInfoByIds(Long[] ids)
    {
        return goodsPicInfoMapper.deleteGoodsPicInfoByIds(ids);
    }

    /**
     * 删除商品图片信息
     * 
     * @param id 商品图片主键
     * @return 结果
     */
    @Override
    public int deleteGoodsPicInfoById(Long id)
    {
        return goodsPicInfoMapper.deleteGoodsPicInfoById(id);
    }

    /**
     * 获取商品主图
     * @param goodsBarcode
     * @return
     */
    @Override
    public GoodsPicInfo getGoodsMainPic(String goodsBarcode) {
        return goodsPicInfoMapper.getGoodsMainPic(goodsBarcode);
    }

    @Override
    public int deleteByBarcodeAndPicversion(String barcode, String picversion) {
        return goodsPicInfoMapper.deleteByBarcodeAndPicversion(barcode, picversion);
    }

    @Override
    public List<GoodsPicInfo> hisPicInfoList() {
        return goodsPicInfoMapper.hisPicInfoList();
    }

    @Override
    public List<GoodsPicInfo> hisPicInfoList2() {
        return goodsPicInfoMapper.hisPicInfoList2();
    }
}
