package com.ruoyi.system.mapper;


import com.ruoyi.system.domain.GoodsPriceInfo;
import com.ruoyi.system.domain.vo.GoodsPriceInfoReqVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("GoodsPriceInfoMapper")
public interface GoodsPriceInfoMapper {

    /**
     * 保存商品价格变动信息
     * @param goodsPriceInfo
     */
    void saveGoodsPriceInfo(GoodsPriceInfo goodsPriceInfo);

    /**
     * 查询7天内的商品价格变动
     * @param reqVo
     * @return
     */
    List<GoodsPriceInfo> queryGoodsPriceInfo7Days(@Param("vo") GoodsPriceInfoReqVo reqVo);
}
