package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UserCompany;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-01-04
 */
public interface IUserCompanyService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public UserCompany selectUserCompanyById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param userCompany 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<UserCompany> selectUserCompanyList(UserCompany userCompany);

    /**
     * 新增【请填写功能名称】
     *
     * @param userCompany 【请填写功能名称】
     * @return 结果
     */
    public int insertUserCompany(UserCompany userCompany);

    /**
     * 修改【请填写功能名称】
     *
     * @param userCompany 【请填写功能名称】
     * @return 结果
     */
    public int updateUserCompany(UserCompany userCompany);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteUserCompanyByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteUserCompanyById(Long id);

    /**
     * 根据用户ID查询用户所属公司
     * @param userId
     * @return
     */
    public UserCompany queryUserCompanyByUserId(Long userId);
}

