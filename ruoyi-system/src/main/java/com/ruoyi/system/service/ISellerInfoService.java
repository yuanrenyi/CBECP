package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SellerInfo;
import com.ruoyi.system.domain.vo.SellerInfoVo;

/**
 * 销售方信息Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface ISellerInfoService 
{
    /**
     * 查询销售方信息
     * 
     * @param id 销售方信息主键
     * @return 销售方信息
     */
    public SellerInfoVo selectSellerInfoById(String id);

    /**
     * 查询销售方信息列表
     * 
     * @param sellerInfo 销售方信息
     * @return 销售方信息集合
     */
    public List<SellerInfo> selectSellerInfoList(SellerInfo sellerInfo);

    /**
     * 查询代理商列表
     * @return
     */
    public List<SellerInfo> selectAgentInfoList(String compName);

    /**
     * 新增销售方信息
     * 
     * @param sellerInfoVo 销售方信息
     * @return 结果
     */
    public int insertSellerInfo(SellerInfoVo sellerInfoVo);

    /**
     * 修改销售方信息
     * 
     * @param sellerInfoVo 销售方信息
     * @return 结果
     */
    public int updateSellerInfo(SellerInfoVo sellerInfoVo);

    /**
     * 批量删除销售方信息
     * 
     * @param ids 需要删除的销售方信息主键集合
     * @return 结果
     */
    public int deleteSellerInfoByIds(String[] ids);

    /**
     * 删除销售方信息信息
     * 
     * @param id 销售方信息主键
     * @return 结果
     */
    public int deleteSellerInfoById(String id);

    /**
     * 获取代理商所有的下级销方
     * @param agentId
     * @return
     */
    public List<SellerInfo> getAgentSellerList(String agentId);
}
