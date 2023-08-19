package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.GoodsInfo;
import com.ruoyi.system.mapper.GoodsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GoodsStockInfoMapper;
import com.ruoyi.system.domain.GoodsStockInfo;
import com.ruoyi.system.service.IGoodsStockInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialException;

/**
 * 商品锁库存Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class GoodsStockInfoServiceImpl implements IGoodsStockInfoService 
{
    @Autowired
    private GoodsStockInfoMapper goodsStockInfoMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    /**
     * 查询商品锁库存
     * 
     * @param id 商品锁库存主键
     * @return 商品锁库存
     */
    @Override
    public GoodsStockInfo selectGoodsStockInfoById(Long id)
    {
        return goodsStockInfoMapper.selectGoodsStockInfoById(id);
    }

    /**
     * 查询商品锁库存列表
     * 
     * @param goodsStockInfo 商品锁库存
     * @return 商品锁库存
     */
    @Override
    public List<GoodsStockInfo> selectGoodsStockInfoList(GoodsStockInfo goodsStockInfo)
    {
        return goodsStockInfoMapper.selectGoodsStockInfoList(goodsStockInfo);
    }

    /**
     * 新增商品锁库存
     * 
     * @param goodsStockInfo 商品锁库存
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public synchronized int insertGoodsStockInfo(GoodsStockInfo goodsStockInfo)
    {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsStockInfo.getGoodsId());
        if(goodsInfo.getStockNum() == 0 ||  goodsInfo.getStockLockNum() + goodsStockInfo.getStockLockNum() > goodsInfo.getStockNum()) {
            throw new ServiceException("商品库存不足, 剩余{}, 请重新输入锁定库存数量。", goodsInfo.getStockNum() - goodsInfo.getStockLockNum());
        }
        goodsStockInfo.setCreateTime(DateUtils.getNowDate());
        goodsStockInfoMapper.insertGoodsStockInfo(goodsStockInfo);

        goodsInfo.setStockLockNum(goodsInfo.getStockLockNum() + goodsStockInfo.getStockLockNum());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);
        return 1;
    }

    /**
     * 修改商品锁库存
     * 
     * @param goodsStockInfo 商品锁库存
     * @return 结果
     */
    @Override
    public int updateGoodsStockInfo(GoodsStockInfo goodsStockInfo)
    {
        goodsStockInfo.setUpdateTime(DateUtils.getNowDate());
        goodsStockInfoMapper.updateGoodsStockInfo(goodsStockInfo);
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsStockInfo.getGoodsId());

        if(goodsStockInfo.getLockStatus() == 2){
            //审核通过，更改库存，还原锁定的库存数
            goodsInfo.setStockNum(goodsInfo.getStockNum() - goodsStockInfo.getStockApplyNum());
            goodsInfo.setStockLockNum(goodsInfo.getStockLockNum() - goodsStockInfo.getStockLockNum());

        } else {
            //取消锁库，把锁定的库存数还原
            goodsInfo.setStockLockNum(goodsInfo.getStockLockNum() - goodsStockInfo.getStockLockNum());
        }
        goodsInfoMapper.updateGoodsInfo(goodsInfo);
        return 1;
    }

    /**
     * 批量删除商品锁库存
     * 
     * @param ids 需要删除的商品锁库存主键
     * @return 结果
     */
    @Override
    public int deleteGoodsStockInfoByIds(Long[] ids)
    {
        return goodsStockInfoMapper.deleteGoodsStockInfoByIds(ids);
    }

    /**
     * 删除商品锁库存信息
     * 
     * @param id 商品锁库存主键
     * @return 结果
     */
    @Override
    public int deleteGoodsStockInfoById(Long id)
    {
        return goodsStockInfoMapper.deleteGoodsStockInfoById(id);
    }
}
