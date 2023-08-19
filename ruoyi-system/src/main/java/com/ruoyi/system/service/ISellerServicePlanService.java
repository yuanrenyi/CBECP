package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SellerServicePlan;

/**
 * 销售方服务方案Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface ISellerServicePlanService 
{
    /**
     * 查询销售方服务方案
     * 
     * @param id 销售方服务方案主键
     * @return 销售方服务方案
     */
    public SellerServicePlan selectSellerServicePlanById(Long id);

    /**
     * 查询销售方服务方案列表
     * 
     * @param sellerServicePlan 销售方服务方案
     * @return 销售方服务方案集合
     */
    public List<SellerServicePlan> selectSellerServicePlanList(SellerServicePlan sellerServicePlan);

    /**
     * 新增销售方服务方案
     * 
     * @param sellerServicePlan 销售方服务方案
     * @return 结果
     */
    public int insertSellerServicePlan(SellerServicePlan sellerServicePlan);

    /**
     * 修改销售方服务方案
     * 
     * @param sellerServicePlan 销售方服务方案
     * @return 结果
     */
    public int updateSellerServicePlan(SellerServicePlan sellerServicePlan);

    /**
     * 批量删除销售方服务方案
     * 
     * @param ids 需要删除的销售方服务方案主键集合
     * @return 结果
     */
    public int deleteSellerServicePlanByIds(Long[] ids);

    /**
     * 删除销售方服务方案信息
     * 
     * @param id 销售方服务方案主键
     * @return 结果
     */
    public int deleteSellerServicePlanById(Long id);

    /**
     * 将销售方服务方案设置为已过期
     * @param compId
     * @return
     */
    public int updateSellerServicePlanOverdueByCompId(String compId);

    /**
     * 查询最新的销售方服务方案
     * @param compId
     * @return
     */
    public SellerServicePlan queryNewSellerServicePlan(String compId);
}
