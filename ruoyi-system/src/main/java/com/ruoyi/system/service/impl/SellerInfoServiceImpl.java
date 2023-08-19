package com.ruoyi.system.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.SellerServicePlan;
import com.ruoyi.system.domain.vo.SellerInfoVo;
import com.ruoyi.system.service.ISellerServicePlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SellerInfoMapper;
import com.ruoyi.system.domain.SellerInfo;
import com.ruoyi.system.service.ISellerInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售方信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class SellerInfoServiceImpl implements ISellerInfoService 
{
    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Autowired
    private ISellerServicePlanService sellerServicePlanService;

    /**
     * 查询销售方信息
     * 
     * @param id 销售方信息主键
     * @return 销售方信息
     */
    @Override
    public SellerInfoVo selectSellerInfoById(String id)
    {
        SellerInfoVo sellerInfoVo = new SellerInfoVo();
        SellerInfo sellerInfo = sellerInfoMapper.selectSellerInfoById(id);
        if(ObjectUtil.isNull(sellerInfo)) {
            return null;
        }
        BeanUtils.copyProperties(sellerInfo, sellerInfoVo);
        SellerServicePlan sellerServicePlan = sellerServicePlanService.queryNewSellerServicePlan(id);
        sellerInfoVo.setServicePlan(sellerServicePlan);
        return sellerInfoVo;
    }

    /**
     * 查询销售方信息列表
     * 
     * @param sellerInfo 销售方信息
     * @return 销售方信息
     */
    @Override
    public List<SellerInfo> selectSellerInfoList(SellerInfo sellerInfo)
    {
        return sellerInfoMapper.selectSellerInfoList(sellerInfo);
    }

    @Override
    public List<SellerInfo> selectAgentInfoList(String compName) {
        SellerInfo s = new SellerInfo();
        s.setCompName(compName);
        return sellerInfoMapper.selectAgentInfoList(s);
    }

    /**
     * 新增销售方信息
     * 
     * @param sellerInfoVo 销售方信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int insertSellerInfo(SellerInfoVo sellerInfoVo)
    {
        SellerInfo sellerInfo = new SellerInfo();
        BeanUtils.copyProperties(sellerInfoVo, sellerInfo);
        sellerInfo.setId(IdUtils.fastSimpleUUID());
        sellerInfo.setCreateTime(DateUtils.getNowDate());
        sellerInfoMapper.insertSellerInfo(sellerInfo);

        if(ObjectUtil.isNotNull(sellerInfoVo.getServicePlan())) {
            SellerServicePlan sellerServicePlan = sellerInfoVo.getServicePlan();
            sellerServicePlan.setCompId(sellerInfo.getId());
            sellerServicePlan.setIfNews(1);
            sellerServicePlan.setSellerName(sellerInfo.getCompName());
            sellerServicePlan.setCreateBy(sellerInfo.getCreateBy());
            sellerServicePlan.setCreateTime(sellerInfo.getCreateTime());
            sellerServicePlanService.insertSellerServicePlan(sellerServicePlan);
        }
        return 1;
    }

    /**
     * 修改销售方信息
     * 
     * @param sellerInfoVo 销售方信息
     * @return 结果
     */
    @Override
    public int updateSellerInfo(SellerInfoVo sellerInfoVo)
    {
        SellerInfo sellerInfo = new SellerInfo();
        BeanUtils.copyProperties(sellerInfoVo, sellerInfo);
        sellerInfo.setUpdateTime(DateUtils.getNowDate());
        if(StringUtils.isNotEmpty(sellerInfoVo.getParentAgentId())) {
            SellerInfo agent = sellerInfoMapper.selectSellerInfoById(sellerInfoVo.getParentAgentId());
            if(ObjectUtil.isNotNull(agent)) {
                sellerInfo.setParentAgentName(agent.getCompName());
            }
        }
        sellerInfoMapper.updateSellerInfo(sellerInfo);

        if(ObjectUtil.isNotNull(sellerInfoVo.getServicePlan())) {
            SellerServicePlan sellerServicePlan = sellerInfoVo.getServicePlan();
            if(ObjectUtil.isNotNull(sellerServicePlan.getId())) {
                sellerServicePlan.setUpdateBy(sellerInfo.getUpdateBy());
                sellerServicePlan.setUpdateTime(sellerInfo.getUpdateTime());
                sellerServicePlanService.updateSellerServicePlan(sellerServicePlan);
            } else {
                sellerServicePlanService.updateSellerServicePlanOverdueByCompId(sellerInfo.getId());
                sellerServicePlan.setCompId(sellerInfo.getId());
                sellerServicePlan.setIfNews(1);
                sellerServicePlan.setSellerName(sellerInfo.getCompName());
                sellerServicePlan.setCreateBy(sellerInfo.getCreateBy());
                sellerServicePlan.setCreateTime(sellerInfo.getCreateTime());
                sellerServicePlanService.insertSellerServicePlan(sellerServicePlan);
            }
        }
        return 1;
    }

    /**
     * 批量删除销售方信息
     * 
     * @param ids 需要删除的销售方信息主键
     * @return 结果
     */
    @Override
    public int deleteSellerInfoByIds(String[] ids)
    {
        return sellerInfoMapper.deleteSellerInfoByIds(ids);
    }

    /**
     * 删除销售方信息信息
     * 
     * @param id 销售方信息主键
     * @return 结果
     */
    @Override
    public int deleteSellerInfoById(String id)
    {
        return sellerInfoMapper.deleteSellerInfoById(id);
    }

    @Override
    public List<SellerInfo> getAgentSellerList(String agentId) {
        SellerInfo query = new SellerInfo();
        query.setParentAgentId(agentId);
        return sellerInfoMapper.selectSellerInfoList(query);
    }
}
