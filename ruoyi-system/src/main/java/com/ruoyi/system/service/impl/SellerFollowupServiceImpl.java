package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SellerInfo;
import com.ruoyi.system.mapper.SellerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SellerFollowupMapper;
import com.ruoyi.system.domain.SellerFollowup;
import com.ruoyi.system.service.ISellerFollowupService;

/**
 * 销售方跟进记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-17
 */
@Service
public class SellerFollowupServiceImpl implements ISellerFollowupService
{
    @Autowired
    private SellerFollowupMapper sellerFollowupMapper;

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    /**
     * 查询销售方跟进记录
     *
     * @param id 销售方跟进记录主键
     * @return 销售方跟进记录
     */
    @Override
    public SellerFollowup selectSellerFollowupById(Long id)
    {
        return sellerFollowupMapper.selectSellerFollowupById(id);
    }

    /**
     * 查询销售方跟进记录列表
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 销售方跟进记录
     */
    @Override
    public List<SellerFollowup> selectSellerFollowupList(SellerFollowup sellerFollowup)
    {
        return sellerFollowupMapper.selectSellerFollowupList(sellerFollowup);
    }

    /**
     * 查询销售方跟进记录列表
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 销售方跟进记录
     */
    @Override
    public List<SellerFollowup> selectSellerFollowupListDTO(SellerFollowup sellerFollowup)
    {
        return sellerFollowupMapper.selectSellerFollowupListDTO(sellerFollowup);
    }

    /**
     * 新增销售方跟进记录
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 结果
     */
    @Override
    public int insertSellerFollowup(SellerFollowup sellerFollowup)
    {
        SellerInfo sellerInfo = sellerInfoMapper.selectSellerInfoById(sellerFollowup.getSellerId());
        sellerFollowup.setSellerName(sellerInfo.getCompName());
        sellerFollowup.setCreateTime(DateUtils.getNowDate());
        return sellerFollowupMapper.insertSellerFollowup(sellerFollowup);
    }

    /**
     * 修改销售方跟进记录
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 结果
     */
    @Override
    public int updateSellerFollowup(SellerFollowup sellerFollowup)
    {
        return sellerFollowupMapper.updateSellerFollowup(sellerFollowup);
    }

    /**
     * 批量删除销售方跟进记录
     *
     * @param ids 需要删除的销售方跟进记录主键
     * @return 结果
     */
    @Override
    public int deleteSellerFollowupByIds(Long[] ids)
    {
        return sellerFollowupMapper.deleteSellerFollowupByIds(ids);
    }

    /**
     * 删除销售方跟进记录信息
     *
     * @param id 销售方跟进记录主键
     * @return 结果
     */
    @Override
    public int deleteSellerFollowupById(Long id)
    {
        return sellerFollowupMapper.deleteSellerFollowupById(id);
    }

    @Override
    public List<SellerFollowup> getTodayFollowupNotice(Long userId) {
        return sellerFollowupMapper.getTodayFollowupNotice(userId);
    }

    /**
     * 查询所有跟进
     * @param type 跟进类型 1=已跟进  2=未跟进
     * @param userId
     * @return
     */
    @Override
    public List<SellerFollowup> getFollowupAll(Integer type, Long userId) {
        return sellerFollowupMapper.getFollowupAll(type, userId);
    }
}
