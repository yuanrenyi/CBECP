package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SellerFollowup;

/**
 * 销售方跟进记录Service接口
 *
 * @author ruoyi
 * @date 2023-01-17
 */
public interface ISellerFollowupService
{
    /**
     * 查询销售方跟进记录
     *
     * @param id 销售方跟进记录主键
     * @return 销售方跟进记录
     */
    public SellerFollowup selectSellerFollowupById(Long id);

    /**
     * 查询销售方跟进记录列表
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 销售方跟进记录集合
     */
    public List<SellerFollowup> selectSellerFollowupList(SellerFollowup sellerFollowup);

    /**
     * 查询销售方跟进记录列表
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 销售方跟进记录集合
     */
    public List<SellerFollowup> selectSellerFollowupListDTO(SellerFollowup sellerFollowup);

    /**
     * 新增销售方跟进记录
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 结果
     */
    public int insertSellerFollowup(SellerFollowup sellerFollowup);

    /**
     * 修改销售方跟进记录
     *
     * @param sellerFollowup 销售方跟进记录
     * @return 结果
     */
    public int updateSellerFollowup(SellerFollowup sellerFollowup);

    /**
     * 批量删除销售方跟进记录
     *
     * @param ids 需要删除的销售方跟进记录主键集合
     * @return 结果
     */
    public int deleteSellerFollowupByIds(Long[] ids);

    /**
     * 删除销售方跟进记录信息
     *
     * @param id 销售方跟进记录主键
     * @return 结果
     */
    public int deleteSellerFollowupById(Long id);

    /**
     * 查询当前用户当日要完成的跟进记录
     * @param userId
     * @return
     */
    public List<SellerFollowup> getTodayFollowupNotice(Long userId);

    /**
     * 查询所有跟进
     * @param type 跟进类型 1=已跟进  2=未跟进
     * @param userId
     * @return
     */
    public List<SellerFollowup> getFollowupAll(Integer type, Long userId);
}
