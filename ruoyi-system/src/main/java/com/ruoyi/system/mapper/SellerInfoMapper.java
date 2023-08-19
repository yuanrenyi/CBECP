package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SellerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 销售方信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Mapper
@Repository("sellerInfoMapper")
public interface SellerInfoMapper 
{
    /**
     * 查询销售方信息
     * 
     * @param id 销售方信息主键
     * @return 销售方信息
     */
    public SellerInfo selectSellerInfoById(String id);

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
    public List<SellerInfo> selectAgentInfoList(SellerInfo sellerInfo);

    /**
     * 新增销售方信息
     * 
     * @param sellerInfo 销售方信息
     * @return 结果
     */
    public int insertSellerInfo(SellerInfo sellerInfo);

    /**
     * 修改销售方信息
     * 
     * @param sellerInfo 销售方信息
     * @return 结果
     */
    public int updateSellerInfo(SellerInfo sellerInfo);

    /**
     * 删除销售方信息
     * 
     * @param id 销售方信息主键
     * @return 结果
     */
    public int deleteSellerInfoById(String id);

    /**
     * 批量删除销售方信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSellerInfoByIds(String[] ids);
}
