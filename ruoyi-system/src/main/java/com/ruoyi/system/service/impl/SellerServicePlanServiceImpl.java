package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SellerServicePlanMapper;
import com.ruoyi.system.domain.SellerServicePlan;
import com.ruoyi.system.service.ISellerServicePlanService;

/**
 * 销售方服务方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class SellerServicePlanServiceImpl implements ISellerServicePlanService 
{
    @Autowired
    private SellerServicePlanMapper sellerServicePlanMapper;

    /**
     * 查询销售方服务方案
     * 
     * @param id 销售方服务方案主键
     * @return 销售方服务方案
     */
    @Override
    public SellerServicePlan selectSellerServicePlanById(Long id)
    {
        return sellerServicePlanMapper.selectSellerServicePlanById(id);
    }

    /**
     * 查询销售方服务方案列表
     * 
     * @param sellerServicePlan 销售方服务方案
     * @return 销售方服务方案
     */
    @Override
    public List<SellerServicePlan> selectSellerServicePlanList(SellerServicePlan sellerServicePlan)
    {
        return sellerServicePlanMapper.selectSellerServicePlanList(sellerServicePlan);
    }

    /**
     * 新增销售方服务方案
     * 
     * @param sellerServicePlan 销售方服务方案
     * @return 结果
     */
    @Override
    public int insertSellerServicePlan(SellerServicePlan sellerServicePlan)
    {
        sellerServicePlanMapper.updateSellerServicePlanOverdueByCompId(sellerServicePlan.getCompId());
        sellerServicePlan.setIfNews(1);
        sellerServicePlan.setCreateTime(DateUtils.getNowDate());
        return sellerServicePlanMapper.insertSellerServicePlan(sellerServicePlan);
    }

    /**
     * 修改销售方服务方案
     * 
     * @param sellerServicePlan 销售方服务方案
     * @return 结果
     */
    @Override
    public int updateSellerServicePlan(SellerServicePlan sellerServicePlan)
    {
        sellerServicePlan.setUpdateTime(DateUtils.getNowDate());
        return sellerServicePlanMapper.updateSellerServicePlan(sellerServicePlan);
    }

    /**
     * 批量删除销售方服务方案
     * 
     * @param ids 需要删除的销售方服务方案主键
     * @return 结果
     */
    @Override
    public int deleteSellerServicePlanByIds(Long[] ids)
    {
        return sellerServicePlanMapper.deleteSellerServicePlanByIds(ids);
    }

    /**
     * 删除销售方服务方案信息
     * 
     * @param id 销售方服务方案主键
     * @return 结果
     */
    @Override
    public int deleteSellerServicePlanById(Long id)
    {
        return sellerServicePlanMapper.deleteSellerServicePlanById(id);
    }

    @Override
    public int updateSellerServicePlanOverdueByCompId(String compId) {
        return sellerServicePlanMapper.updateSellerServicePlanOverdueByCompId(compId);
    }

    @Override
    public SellerServicePlan queryNewSellerServicePlan(String compId) {
        return sellerServicePlanMapper.queryNewSellerServicePlan(compId);
    }
}
