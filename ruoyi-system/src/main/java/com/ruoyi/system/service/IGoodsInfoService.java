package com.ruoyi.system.service;

import java.io.IOException;
import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.GoodsInfo;
import com.ruoyi.system.domain.sellergoods.SellerGoodsDetail;
import com.ruoyi.system.domain.sellergoods.SellerGoodsInfo;
import com.ruoyi.system.domain.vo.GoodsInfoExportReqVo;
import com.ruoyi.system.domain.vo.GoodsInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商品信息Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface IGoodsInfoService 
{
    /**
     * 查询商品信息
     * 
     * @param id 商品信息主键
     * @return 商品信息
     */
    public GoodsInfoVo selectGoodsInfoById(String id);

    /**
     * 查询商品信息
     * @param id
     * @param sellerId
     * @return
     */
    public GoodsInfoVo selectGoodsInfoByIdAndSellerId(String id, String sellerId);

    /**
     * 查询商品信息列表
     * 
     * @param goodsInfo 商品信息
     * @return 商品信息集合
     */
    public List<GoodsInfo> selectGoodsInfoList(GoodsInfo goodsInfo);

    /**
     * 根据商品ids查询商品列表
     * @param reqVo
     * @return
     */
    public List<GoodsInfo> selectGoodsInfoByIds(GoodsInfoExportReqVo reqVo);

    /**
     * 新增商品信息
     * 
     * @param goodsInfoVo 商品信息
     * @return 结果
     */
    public int insertGoodsInfo(GoodsInfoVo goodsInfoVo);

    /**
     * 修改商品信息
     * 
     * @param goodsInfoVo 商品信息
     * @return 结果
     */
    public int updateGoodsInfo(GoodsInfoVo goodsInfoVo, LoginUser loginUser);

    /**
     * 商品审核
     * @param goodsInfoVo
     * @return
     */
    public int auditGoodsInfo(GoodsInfoVo goodsInfoVo, LoginUser loginUser);

    /**
     * 批量删除商品信息
     * 
     * @param ids 需要删除的商品信息主键集合
     * @return 结果
     */
    public int deleteGoodsInfoByIds(String[] ids);

    /**
     * 删除商品信息信息
     * 
     * @param id 商品信息主键
     * @return 结果
     */
    public int deleteGoodsInfoById(String id);

    /**
     * 平台方上架/下架商品
     * @param goodsInfo
     * @return
     */
    public int platOnshelf(GoodsInfo goodsInfo);

    /**
     * 查询销售方查看的商品列表
     * @param goodsInfo
     * @return
     */
    public List<SellerGoodsInfo> sellerGoodsList(GoodsInfo goodsInfo);

    /**
     * 查询销售方查看的商品详情
     * @param goodsBarcode
     * @param sellerId
     * @return
     */
    public SellerGoodsDetail sellerGoodsDetail(String goodsBarcode, String sellerId);

    /**
     * 下载商品图片
     * @param barcode
     */
    public void downloadGoodsPic(String barcode, HttpServletRequest request, HttpServletResponse response);

    /**
     * 下载商品图片(新)
     * @param barcode
     */
    public byte[] downloadGoodsPicByte(String barcode, String picVersion, HttpServletResponse response);

    /**
     * 导入商品信息
     * @param goodsFile
     */
    public String uploadGoodsInfo(MultipartFile goodsFile, HttpServletResponse response, LoginUser loginUser) throws IOException;

    /**
     * 导入新增商品信息
     * @param goodsFile
     */
    public String uploadGoodsInfoV2(MultipartFile goodsFile, HttpServletResponse response, Long userId, String storehouseId, LoginUser loginUser) throws IOException;

    /**
     * 导出商品信息
     * @param response
     * @param list
     */
    public void exportGoodsInfo(HttpServletResponse response, List<GoodsInfo> list, Integer userCompanyType);

    /**
     * 导入修改商品信息
     * @param goodsFile
     */
    public String uploadGoodsInfoUpdate(MultipartFile goodsFile, HttpServletResponse response, LoginUser loginUser) throws IOException;

    /**
     * 导入修改商品库存
     * @param goodsFile
     * @param response
     * @param loginUser
     * @return
     * @throws IOException
     */
    public String uploadGoodsStockUpdate(MultipartFile goodsFile, HttpServletResponse response, LoginUser loginUser) throws IOException;
}
