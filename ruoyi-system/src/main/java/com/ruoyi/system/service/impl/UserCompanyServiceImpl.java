package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UserCompanyMapper;
import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.service.IUserCompanyService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-01-04
 */
@Service
public class UserCompanyServiceImpl implements IUserCompanyService
{
    @Autowired
    private UserCompanyMapper userCompanyMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public UserCompany selectUserCompanyById(Long id)
    {
        return userCompanyMapper.selectUserCompanyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param userCompany 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<UserCompany> selectUserCompanyList(UserCompany userCompany)
    {
        return userCompanyMapper.selectUserCompanyList(userCompany);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param userCompany 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertUserCompany(UserCompany userCompany)
    {
        userCompany.setCreateTime(DateUtils.getNowDate());
        return userCompanyMapper.insertUserCompany(userCompany);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param userCompany 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateUserCompany(UserCompany userCompany)
    {
        userCompany.setUpdateTime(DateUtils.getNowDate());
        return userCompanyMapper.updateUserCompany(userCompany);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteUserCompanyByIds(Long[] ids)
    {
        return userCompanyMapper.deleteUserCompanyByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteUserCompanyById(Long id)
    {
        return userCompanyMapper.deleteUserCompanyById(id);
    }

    /**
     * 根据用户ID查询用户所属公司
     * @param userId
     * @return
     */
    @Override
    public UserCompany queryUserCompanyByUserId(Long userId) {
        return userCompanyMapper.queryUserCompanyByUserId(userId);
    }
}
