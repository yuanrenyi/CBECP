package com.ruoyi.system.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.ProjectConstants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.ImageTypeEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileDownloadUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.sellergoods.SellerGoodsDetail;
import com.ruoyi.system.domain.sellergoods.SellerGoodsInfo;
import com.ruoyi.system.domain.sellergoods.SellerGoodsStorehouseInfo;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商品信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
@Slf4j
public class GoodsInfoServiceImpl implements IGoodsInfoService 
{
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsPicInfoMapper goodsPicInfoMapper;

    @Autowired
    private ISupplierStorehouseInfoService supplierStorehouseInfoService;

    @Autowired
    private SellerGoodsShelfMapper sellerGoodsShelfMapper;

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Autowired
    private GoodsPriceInfoMapper goodsPriceInfoMapper;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private ICatalogInfoService catalogInfoService;

    @Autowired
    private ISellerInfoService sellerInfoService;

    /**
     * 查询商品信息
     * 
     * @param id 商品信息主键
     * @return 商品信息
     */
    @Override
    public GoodsInfoVo selectGoodsInfoById(String id)
    {
        GoodsInfoVo goodsInfoVo = new GoodsInfoVo();
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if(!Objects.isNull(goodsInfo)) {
            BeanUtils.copyProperties(goodsInfo, goodsInfoVo);
            GoodsPicInfo queryParams = new GoodsPicInfo();
            queryParams.setGoodsId(goodsInfo.getPlatBarcode());
            if(StringUtils.isNotEmpty(goodsInfo.getPicVersion())) {
                queryParams.setPicVersion(goodsInfo.getPicVersion());
            } else {
                queryParams.setPicVersion("0");
            }

            List<GoodsPicInfo> picInfos = goodsPicInfoMapper.selectGoodsPicInfoList(queryParams);
            //解决图片地址中的特殊字符无法被解析成正确url问题
            if(ObjectUtil.isNotNull(picInfos) && picInfos.size() > 0) {
                picInfos.forEach(pic -> {
                    if(StringUtils.isNotEmpty(pic.getPicUrl())) {
                        pic.setPicUrl(UriEncoder.encode(pic.getPicUrl()));
                    }
                });
            }
            if(!Objects.isNull(picInfos)) {
                goodsInfoVo.setPicInfo(picInfos);
            }

            if(StringUtils.isNotEmpty(goodsInfoVo.getGoodsClassification())) {
                goodsInfoVo.setGoodsClassificationArr(StringUtils.stringToLongList(goodsInfoVo.getGoodsClassification()));
            }
        }

        List<OnplatSellerInfoVo> sellerList = sellerGoodsShelfMapper.selectOnplatSellerInfo(goodsInfo.getId());

        if(ObjectUtil.isNotNull(sellerList) && sellerList.size() > 0) {
            List<String> sellerName = sellerList.stream().map(OnplatSellerInfoVo::getSellerName).collect(Collectors.toList());
            goodsInfoVo.setOnplatSeller(sellerName);
        }
        
        return goodsInfoVo;
    }

    @Override
    public GoodsInfoVo selectGoodsInfoByIdAndSellerId(String id, String sellerId) {
        GoodsInfoVo goodsInfoVo = new GoodsInfoVo();
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if(!Objects.isNull(goodsInfo)) {
            BeanUtils.copyProperties(goodsInfo, goodsInfoVo);
            GoodsPicInfo queryParams = new GoodsPicInfo();
            queryParams.setGoodsId(goodsInfo.getPlatBarcode());
            if(StringUtils.isNotEmpty(goodsInfo.getPicVersion())) {
                queryParams.setPicVersion(goodsInfo.getPicVersion());
            } else {
                queryParams.setPicVersion("0");
            }

            List<GoodsPicInfo> picInfos = goodsPicInfoMapper.selectGoodsPicInfoList(queryParams);
            //解决图片地址中的特殊字符无法被解析成正确url问题
            if(ObjectUtil.isNotNull(picInfos) && picInfos.size() > 0) {
                picInfos.forEach(pic -> {
                    if(StringUtils.isNotEmpty(pic.getPicUrl())) {
                        pic.setPicUrl(UriEncoder.encode(pic.getPicUrl()));
                    }
                });
            }
            if(!Objects.isNull(picInfos)) {
                goodsInfoVo.setPicInfo(picInfos);
            }

            if(StringUtils.isNotEmpty(goodsInfoVo.getGoodsClassification())) {
                goodsInfoVo.setGoodsClassificationArr(StringUtils.stringToLongList(goodsInfoVo.getGoodsClassification()));
            }
        }

        List<OnplatSellerInfoVo> sellerList = sellerGoodsShelfMapper.selectOnplatSellerInfo(goodsInfo.getId());

        if(ObjectUtil.isNotNull(sellerList) && sellerList.size() > 0) {
            List<String> sellerName = sellerList.stream().map(OnplatSellerInfoVo::getSellerName).collect(Collectors.toList());
            goodsInfoVo.setOnplatSeller(sellerName);
        }

        BigDecimal proportion = getSellerProportion(sellerId);
        if(ObjectUtil.isNotNull(proportion)) {
            goodsInfoVo.setCostPrice(goodsInfoVo.getCostPrice().multiply(proportion).setScale(2, 4));
        }
        return goodsInfoVo;
    }

    /**
     * 查询商品信息列表
     * 
     * @param goodsInfo 商品信息
     * @return 商品信息
     */
    @Override
    public List<GoodsInfo> selectGoodsInfoList(GoodsInfo goodsInfo)
    {
        List<GoodsInfo> result = goodsInfoMapper.selectGoodsInfoList(goodsInfo);
        result.forEach(goods->{
            if(StringUtils.isNotEmpty(goods.getGoodsClassification())) {
                goods.setGoodsClassificationArr(StringUtils.stringToLongList(goods.getGoodsClassification()));
            }
        });

        BigDecimal proportion = getSellerProportion(goodsInfo.getSellerId());
        if(ObjectUtil.isNotNull(proportion)) {
            result.forEach(goods -> {
                goods.setCostPrice(goods.getCostPrice().multiply(proportion).setScale(2, 4));
            });
        }
        return result;
    }

    @Override
    public List<GoodsInfo> selectGoodsInfoByIds(GoodsInfoExportReqVo reqVo) {
        List<GoodsInfo> result = goodsInfoMapper.selectGoodsInfoByIds(reqVo.getIds());
        result.forEach(goods->{
            if(StringUtils.isNotEmpty(goods.getGoodsClassification())) {
                goods.setGoodsClassificationArr(StringUtils.stringToLongList(goods.getGoodsClassification()));
            }
        });

        BigDecimal proportion = getSellerProportion(reqVo.getSellerId());
        if(ObjectUtil.isNotNull(proportion)) {
            result.forEach(goods -> {
                goods.setCostPrice(goods.getCostPrice().multiply(proportion).setScale(2, 4));
            });
        }

        return result;
    }

    /**
     * 新增商品信息
     * 
     * @param goodsInfoVo 商品信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertGoodsInfo(GoodsInfoVo goodsInfoVo)
    {
        //保存商品基本信息
        goodsInfoVo.setId(IdUtils.fastSimpleUUID());
        goodsInfoVo.setCreateTime(DateUtils.getNowDate());
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsInfoVo, goodsInfo);
        if(ObjectUtil.isNotNull(goodsInfo.getGoodsClassificationArr())) {
            goodsInfo.setGoodsClassification(StringUtils.join(goodsInfo.getGoodsClassificationArr().toArray(), ","));
        }
        if(StringUtils.isBlank(goodsInfo.getGoodsOutCode())) {
            goodsInfo.setGoodsOutCode(IdUtils.fastSimpleUUID().toUpperCase());
        }

        if(ObjectUtil.isNotNull(goodsInfo.getNakedPrice())) {
            BigDecimal bd = new BigDecimal("1.025");
            BigDecimal costPrice = goodsInfo.getNakedPrice().multiply(bd).setScale(2, 4);
            goodsInfo.setCostPrice(costPrice);
        }
        if(StringUtils.isEmpty(goodsInfo.getPlatBarcode())) {
            goodsInfo.setPlatBarcode(goodsInfo.getGoodsBarcode());
        }
        goodsInfo.setFinalFreightAmount(calcFinalFreightAmount(goodsInfo));
        int i = goodsInfoMapper.insertGoodsInfo(goodsInfo);
        if(StringUtils.isNotEmpty(goodsInfo.getHsCode())) {
            goodsInfoMapper.updateGoodsTaxRateById(goodsInfo.getId());
        }
        return i;
    }

    /**
     * 修改商品信息
     * 
     * @param goodsInfoVo 商品信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public int updateGoodsInfo(GoodsInfoVo goodsInfoVo, LoginUser loginUser)
    {


        //更新商品基本信息
        goodsInfoVo.setUpdateTime(DateUtils.getNowDate());
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsInfoVo, goodsInfo);

        GoodsInfo srcGoodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsInfo.getId());

        if(ObjectUtil.isNotNull(goodsInfo.getGoodsClassificationArr())) {
            goodsInfo.setGoodsClassification(StringUtils.join(goodsInfo.getGoodsClassificationArr().toArray(), ","));
        }

        if(StringUtils.isBlank(goodsInfo.getGoodsOutCode())) {
            goodsInfo.setGoodsOutCode(IdUtils.fastSimpleUUID().toUpperCase());
        }

        // 成本价 = 裸价 * 1.025
        if(ObjectUtil.isNotNull(goodsInfo.getNakedPrice())) {
            BigDecimal bd = new BigDecimal("1.025");
            BigDecimal costPrice = goodsInfo.getNakedPrice().multiply(bd).setScale(2, 4);
            goodsInfo.setCostPrice(costPrice);
        }

        if(StringUtils.isEmpty(goodsInfo.getPlatBarcode())) {
            goodsInfo.setPlatBarcode(goodsInfo.getGoodsBarcode());
        }
        goodsInfo.setFinalFreightAmount(calcFinalFreightAmount(goodsInfo));
        int i = goodsInfoMapper.updateGoodsInfo(goodsInfo);

        if(StringUtils.isNotEmpty(goodsInfo.getHsCode())) {
            goodsInfoMapper.updateGoodsTaxRateById(goodsInfo.getId());
        }

        // 记录商品价格改动
        if(ObjectUtil.isNotNull(srcGoodsInfo.getCostPrice()) && ObjectUtil.isNotNull(goodsInfo.getCostPrice()) && srcGoodsInfo.getCostPrice().compareTo(goodsInfo.getCostPrice()) != 0) {
            SysOperLog operLog = new SysOperLog();
            operLog.setTitle("商品改价-" + goodsInfo.getGoodsCode());
            operLog.setOperatorType(2);
            operLog.setMethod("单个商品修改价格");
            operLog.setRequestMethod("POST");
            operLog.setOperatorType(1);
            operLog.setOperName(loginUser.getUser().getNickName());
            operLog.setDeptName(loginUser.getUser().getDept().getDeptName());
            operLog.setOperUrl("/system/goods/update");
            operLog.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            operLog.setOperParam("原价格：" + srcGoodsInfo.getCostPrice() + ",修改后价格：" + goodsInfo.getCostPrice());
            operLog.setStatus(0);
            operLog.setOperTime(new Date());
            operLogMapper.insertOperlog(operLog);

            GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
            goodsPriceInfo.setGoodsId(goodsInfo.getId());
            goodsPriceInfo.setGoodsName(goodsInfo.getGoodsName());
            goodsPriceInfo.setBeforePrice(srcGoodsInfo.getCostPrice());
            goodsPriceInfo.setAfterPrice(goodsInfo.getCostPrice());
            goodsPriceInfo.setOptUserId(loginUser.getUserId());
            goodsPriceInfo.setOptUserName(loginUser.getUser().getNickName());
            goodsPriceInfo.setOptTime(new Date());
            goodsPriceInfoMapper.saveGoodsPriceInfo(goodsPriceInfo);
        }
        return i;
    }

    /**
     * 修改商品信息
     *
     * @param goodsInfoVo 商品信息
     * @return 结果
     */
    @Override
    public int auditGoodsInfo(GoodsInfoVo goodsInfoVo, LoginUser loginUser)
    {
        //更新商品基本信息
        goodsInfoVo.setUpdateTime(DateUtils.getNowDate());
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsInfoVo, goodsInfo);

        int i = goodsInfoMapper.updateGoodsInfo(goodsInfo);

        SysOperLog operLog = new SysOperLog();
        operLog.setTitle("商品审核-" + goodsInfo.getGoodsCode());
        operLog.setOperatorType(2);
        operLog.setMethod("商品审核");
        operLog.setRequestMethod("POST");
        operLog.setOperatorType(1);
        operLog.setOperName(loginUser.getUser().getNickName());
        operLog.setDeptName(loginUser.getUser().getDept().getDeptName());
        operLog.setOperUrl("/system/goods/update");
        operLog.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        operLog.setOperParam("商品审核结果：" + goodsInfo.getAuditStatus());
        operLog.setStatus(0);
        operLog.setOperTime(new Date());
        operLogMapper.insertOperlog(operLog);
        return i;
    }


    /**
     * 批量删除商品信息
     * 
     * @param ids 需要删除的商品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsInfoByIds(String[] ids)
    {
        return goodsInfoMapper.deleteGoodsInfoByIds(ids);
    }

    /**
     * 删除商品信息信息
     * 
     * @param id 商品信息主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGoodsInfoById(String id)
    {
        //删除商品信息
        return goodsInfoMapper.deleteGoodsInfoById(id);
    }

    @Override
    public int platOnshelf(GoodsInfo goodsInfo) {
        GoodsInfo existGoodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsInfo.getId());
        if(Objects.isNull(existGoodsInfo)) {
            throw new ServiceException("商品信息有误");
        }
        return goodsInfoMapper.platOnshelf(goodsInfo);
    }

    /**
     * 查询销售方查看的商品列表
     * @param goodsInfo
     * @return
     */
    @Override
    public List<SellerGoodsInfo> sellerGoodsList(GoodsInfo goodsInfo) {
        List<SellerGoodsInfo> sellerGoodsInfoList = goodsInfoMapper.sellerGoodsList(goodsInfo);
        sellerGoodsInfoList.stream().forEach(sellerGoodsInfo -> {
            sellerGoodsInfo.setStorehouseInfoList(goodsInfoMapper.sellerGoodsStorehouseInfoList(sellerGoodsInfo.getGoodsBarcode()));
            GoodsPicInfo picInfo = goodsPicInfoMapper.getGoodsMainPic(sellerGoodsInfo.getPlatBarcode());
            if(ObjectUtil.isNotNull(picInfo)) {
                sellerGoodsInfo.setPicUrl(UriEncoder.encode(picInfo.getPicUrl()));
            }
        });

        BigDecimal proportion = getSellerProportion(goodsInfo.getSellerId());
        if(ObjectUtil.isNotNull(proportion)) {
            sellerGoodsInfoList.forEach(sellerGoodsInfo -> {
                sellerGoodsInfo.getStorehouseInfoList().forEach(e->{
                    e.setCostPrice(e.getCostPrice().multiply(proportion).setScale(2, 4));
                });
            });
        }
        return sellerGoodsInfoList;
    }

    /**
     * 查询销售方查看的商品详情
     * @param goodsBarcode
     * @param sellerId
     * @return
     */
    @Override
    public SellerGoodsDetail sellerGoodsDetail(String goodsBarcode, String sellerId) {
        SellerGoodsDetail sellerGoodsDetail = goodsInfoMapper.sellerGoodsDetail(goodsBarcode);
        List<SellerGoodsStorehouseInfo> storehouseInfoList = goodsInfoMapper.sellerGoodsStorehouseInfoDetail(goodsBarcode, sellerId);
        storehouseInfoList.forEach(sinfo -> {
            if(StringUtils.isNotEmpty(sinfo.getGoodsClassification())) {
                sinfo.setGoodsClassificationArr(StringUtils.stringToLongList(sinfo.getGoodsClassification()));
            }
        });
        sellerGoodsDetail.setStorehouseInfoList(storehouseInfoList);

        sellerGoodsDetail.setPicList(goodsPicInfoMapper.selectGoodsPicInfoList(GoodsPicInfo.builder().goodsId(goodsBarcode).build()));

        // 解决图片地址中的特殊字符无法被解析成正确url问题
        if(ObjectUtil.isNotNull(sellerGoodsDetail.getPicList()) && sellerGoodsDetail.getPicList().size() > 0) {
            sellerGoodsDetail.getPicList().forEach(pic -> {
                if(StringUtils.isNotEmpty(pic.getPicUrl())) {
                    pic.setPicUrl(UriEncoder.encode(pic.getPicUrl()));
                }
            });
        }

        if(StringUtils.isNotEmpty(sellerId)) {
            BigDecimal proportion = getSellerProportion(sellerId);
            if(ObjectUtil.isNotNull(proportion)) {
                sellerGoodsDetail.getStorehouseInfoList().forEach(e->{
                    e.setCostPrice(e.getCostPrice().multiply(proportion).setScale(2,4));
                });
            }
        }
        return sellerGoodsDetail;
    }

    /**
     * 下载商品图片
     * @param barcode
     */
    @Override
    public void downloadGoodsPic(String barcode, HttpServletRequest request, HttpServletResponse response) {
        List<GoodsPicInfo> picInfoList = goodsPicInfoMapper.selectGoodsPicInfoList(GoodsPicInfo.builder().goodsId(barcode).build());
        List<String> picPathList = picInfoList.stream().map(GoodsPicInfo::getPicUrl).collect(Collectors.toList());
        List<String> downloadPathList = new ArrayList<>();
        picPathList.forEach(url ->{
            if(!StringUtils.isEmpty(url)) {
                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_IP, ProjectConstants.PROJECT_UPLOADPATH);
                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_REALM, ProjectConstants.PROJECT_UPLOADPATH);
                if(url.contains(",")) {
                    downloadPathList.addAll(Arrays.asList(url.split(",")));
                } else {
                    downloadPathList.add(url);
                }
            }
        });
        FileDownloadUtils.batchDownloadGoodsFile(barcode, downloadPathList, request, response);
    }

    /**
     * 下载商品图片(新)
     * @param barcode
     */
    @Override
    public byte[] downloadGoodsPicByte(String barcode, String picVersion, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        genGoodsPicZip(barcode, picVersion, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 导入商品信息
     * @param goodsFile
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String uploadGoodsInfo(MultipartFile goodsFile, HttpServletResponse response, LoginUser loginUser) throws IOException {
        Long userId = loginUser.getUserId();
        List<GoodsInfoUploadVo> goodsList = readGoodsExcel(goodsFile);
        List<String> resultMsg = new ArrayList<>();
        if(Objects.isNull(goodsList) || goodsList.size() <= 0) {
            resultMsg.add("读取表格失败！");
        }
        int successNum = 0;

        GoodsInfo existQueryParam = new GoodsInfo();
        for(GoodsInfoUploadVo goodsInfoUploadVo : goodsList) {
            if(StringUtils.isBlank(goodsInfoUploadVo.getGoodsBarcode()) || StringUtils.isBlank(goodsInfoUploadVo.getGoodsCode())) {
                continue;
            }
            GoodsInfo goodsInfo = new GoodsInfo();
            BeanUtils.copyProperties(goodsInfoUploadVo, goodsInfo);
            goodsInfo.setDelFlag(1);
            goodsInfo.setPicVersion("0");
            goodsInfo.setCreateBy(String.valueOf(userId));
            goodsInfo.setCreateTime(new Date());
            goodsInfo.setAuditStatus(1);
            goodsInfo.setAuditBy("系统导入");
            if(Objects.isNull(goodsInfo.getPlatOnShelf())) {
                goodsInfo.setPlatOnShelf(0);
            }
            goodsInfo.setRecommend(0);

            existQueryParam.setGoodsCode(goodsInfo.getGoodsCode());
            List<GoodsInfo> exist = goodsInfoMapper.selectGoodsInfoList(existQueryParam);

            if(Objects.isNull(exist) || exist.size() <= 0) {
                goodsInfo.setId(IdUtils.fastSimpleUUID());
                goodsInfoMapper.insertGoodsInfo(goodsInfo);
            } else {
                GoodsInfo existGoodsInfo = exist.get(0);
                goodsInfo.setId(exist.get(0).getId());
                goodsInfo.setStorehouseId(existGoodsInfo.getStorehouseId());
                goodsInfo.setSupplierId(existGoodsInfo.getSupplierId());
                goodsInfoMapper.updateGoodsInfo(goodsInfo);

                if(ObjectUtil.isNotNull(existGoodsInfo.getCostPrice()) && ObjectUtil.isNotNull(goodsInfo.getCostPrice())
                    && existGoodsInfo.getCostPrice().compareTo(goodsInfo.getCostPrice()) != 0) {
                    SysOperLog operLog = new SysOperLog();
                    operLog.setTitle("商品改价-" + goodsInfo.getGoodsCode());
                    operLog.setOperatorType(2);
                    operLog.setMethod("批量导入修改价格");
                    operLog.setRequestMethod("POST");
                    operLog.setOperatorType(1);
                    operLog.setOperName(loginUser.getUser().getNickName());
                    operLog.setDeptName(loginUser.getUser().getDept().getDeptName());
                    operLog.setOperUrl("/system/goods/import");
                    operLog.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
                    operLog.setOperParam("原价格：" + existGoodsInfo.getCostPrice() + ",修改后价格：" + goodsInfo.getCostPrice());
                    operLog.setStatus(0);
                    operLog.setOperTime(new Date());
                    operLogMapper.insertOperlog(operLog);
                }
            }
            successNum ++;
        }
        return "导入成功" + successNum + "条商品";
    }

    /**
     * 导入商品信息
     * @param goodsFile
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String uploadGoodsInfoV2(MultipartFile goodsFile, HttpServletResponse response, Long userId, String storehouseId, LoginUser loginUser) throws IOException {
        List<GoodsInfoUploadVo> goodsList = readGoodsExcel(goodsFile);
        List<String> resultMsg = new ArrayList<>();
        if(Objects.isNull(goodsList) || goodsList.size() <= 0) {
            resultMsg.add("读取表格失败！");
        }
        int successNum = 0;

        String supplierId = "";
        SupplierStorehouseInfo supplierStorehouseInfo = supplierStorehouseInfoService.selectSupplierStorehouseInfoById(storehouseId);
        if(ObjectUtil.isNotNull(supplierStorehouseInfo)) {
            supplierId = supplierStorehouseInfo.getSupplierId();
        }

        BigDecimal addWeight = new BigDecimal(150);

        for(GoodsInfoUploadVo goodsInfoUploadVo : goodsList) {
            if(StringUtils.isBlank(goodsInfoUploadVo.getGoodsBarcode()) || StringUtils.isBlank(goodsInfoUploadVo.getGoodsCode())) {
                continue;
            }

            GoodsInfo goodsInfo = new GoodsInfo();
            BeanUtils.copyProperties(goodsInfoUploadVo, goodsInfo);
            goodsInfo.setDelFlag(1);
            goodsInfo.setPicVersion("0");
            goodsInfo.setCreateBy(String.valueOf(userId));
            goodsInfo.setCreateTime(new Date());
            goodsInfo.setAuditStatus(1);
            goodsInfo.setAuditBy("系统导入");
            goodsInfo.setStorehouseId(storehouseId);
            goodsInfo.setSupplierId(supplierId);
            if(Objects.isNull(goodsInfo.getPlatOnShelf())) {
                goodsInfo.setPlatOnShelf(0);
            }
            goodsInfo.setRecommend(0);

            if(ObjectUtil.isNotNull(goodsInfo.getGoodsWeight())) {
                goodsInfo.setGrossWeight(goodsInfo.getGoodsWeight().add(addWeight));
            }

            List<GoodsInfo> exist = goodsInfoMapper.selectGoodsInfoByGoodsCodeAndStorehouseId(goodsInfo.getGoodsCode(), goodsInfo.getStorehouseId());
            if(Objects.isNull(exist) || exist.size() <= 0) {
                goodsInfo.setId(IdUtils.fastSimpleUUID());
                if(StringUtils.isBlank(goodsInfo.getGoodsOutCode())) {
                    goodsInfo.setGoodsOutCode(IdUtils.fastSimpleUUID().toUpperCase());
                }
                goodsInfo.setPlatBarcode(goodsInfo.getGoodsBarcode());
                goodsInfo.setFinalFreightAmount(calcFinalFreightAmount(goodsInfo));
                goodsInfoMapper.insertGoodsInfo(goodsInfo);
                if(StringUtils.isNotEmpty(goodsInfo.getHsCode())) {
                    goodsInfoMapper.updateGoodsTaxRateById(goodsInfo.getId());
                }
            } else {
                GoodsInfo existGoodsInfo = exist.get(0);
                goodsInfo.setId(exist.get(0).getId());
                if(StringUtils.isBlank(existGoodsInfo.getGoodsOutCode())) {
                    goodsInfo.setGoodsOutCode(IdUtils.fastSimpleUUID().toUpperCase());
                }
                if(StringUtils.isNotEmpty(existGoodsInfo.getPlatBarcode())) {
                    goodsInfo.setPlatBarcode(existGoodsInfo.getPlatBarcode());
                } else {
                    goodsInfo.setPlatBarcode(goodsInfo.getGoodsBarcode());
                }
                goodsInfo.setFinalFreightAmount(calcFinalFreightAmount(goodsInfo));
                goodsInfoMapper.updateGoodsInfo(goodsInfo);
                if(StringUtils.isNotEmpty(goodsInfo.getHsCode())) {
                    goodsInfoMapper.updateGoodsTaxRateById(goodsInfo.getId());
                }
                // 商品价格变动，1、记录日志表  2、记录商品改价表
                if(ObjectUtil.isNotNull(existGoodsInfo.getCostPrice()) && ObjectUtil.isNotNull(goodsInfo.getCostPrice())
                        && existGoodsInfo.getCostPrice().compareTo(goodsInfo.getCostPrice()) != 0) {
                    SysOperLog operLog = new SysOperLog();
                    operLog.setTitle("商品改价-" + goodsInfo.getGoodsCode());
                    operLog.setOperatorType(2);
                    operLog.setMethod("批量导入修改价格");
                    operLog.setRequestMethod("POST");
                    operLog.setOperatorType(1);
                    operLog.setOperName(loginUser.getUser().getNickName());
                    operLog.setDeptName(loginUser.getUser().getDept().getDeptName());
                    operLog.setOperUrl("/system/goods/import");
                    operLog.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
                    operLog.setOperParam("原价格：" + existGoodsInfo.getCostPrice() + ",修改后价格：" + goodsInfo.getCostPrice());
                    operLog.setStatus(0);
                    operLog.setOperTime(new Date());
                    operLogMapper.insertOperlog(operLog);

                    GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
                    goodsPriceInfo.setGoodsId(goodsInfo.getId());
                    goodsPriceInfo.setGoodsName(goodsInfo.getGoodsName());
                    goodsPriceInfo.setBeforePrice(existGoodsInfo.getCostPrice());
                    goodsPriceInfo.setAfterPrice(goodsInfo.getCostPrice());
                    goodsPriceInfo.setOptUserId(userId);
                    goodsPriceInfo.setOptUserName(loginUser.getUser().getNickName());
                    goodsPriceInfo.setOptTime(new Date());
                    goodsPriceInfoMapper.saveGoodsPriceInfo(goodsPriceInfo);
                }
            }
            successNum ++;
        }
        return "导入成功" + successNum + "条商品";
    }

    /**
     *
     */
    private void genGoodsPicZip(String barcode, String picVersion, ZipOutputStream zip)
    {
        /*List<GoodsPicInfo> picInfoList = goodsPicInfoMapper.selectGoodsPicInfoList(GoodsPicInfo.builder().goodsId(barcode).picVersion(picVersion).build());
        List<String> picPathList = picInfoList.stream().map(GoodsPicInfo::getPicUrl).collect(Collectors.toList());
        List<String> downloadPathList = new ArrayList<>();
        picPathList.forEach(url ->{
            if(!StringUtils.isEmpty(url)) {
                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_IP, ProjectConstants.PROJECT_UPLOADPATH);
                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_REALM, ProjectConstants.PROJECT_UPLOADPATH);
                if(url.contains(",")) {
                    downloadPathList.addAll(Arrays.asList(url.split(",")));
                } else {
                    downloadPathList.add(url);
                }
            }
        });
        log.info("图片地址List={}", JSON.toJSONString(downloadPathList));
        for (String goodsUrl : downloadPathList)
        {

            try
            {

                // 添加到zip
                zip.putNextEntry(new ZipEntry(new Date().getTime() + "_" + goodsUrl.substring(goodsUrl.lastIndexOf("/") + 1)));
                IOUtils.write(getFileBytes(goodsUrl), zip);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("下载图片失败genGoodsPicZip", e);
            }
        }*/
        List<GoodsPicInfo> picInfoList = goodsPicInfoMapper.selectGoodsPicInfoList(GoodsPicInfo.builder().goodsId(barcode).picVersion(picVersion).build());

//        //将映射地址（域名下的地址） 转换为实际的物理地址
//        picInfoList.stream().forEach(picInfo -> {
//            String url = picInfo.getPicUrl();
//            if(!StringUtils.isEmpty(url)) {
//                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_IP, ProjectConstants.PROJECT_UPLOADPATH);
//                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_REALM, ProjectConstants.PROJECT_UPLOADPATH);
//                url = url.replaceAll(ProjectConstants.PROJECT_DOMAIN_DEV, ProjectConstants.PROJECT_UPLOADPATH);
//                picInfo.setPicUrl(url);
//            }
//        });

        for(GoodsPicInfo picInfo : picInfoList) {
            try
            {
                String picUrl = picInfo.getPicUrl();
                String barcodeFolder = picInfo.getGoodsId() + "-" + picInfo.getPicVersion();
                String picTypeFolder = ImageTypeEnums.getImageTypeCode(picInfo.getPicType());
                // 添加到zip
                zip.putNextEntry(new ZipEntry(barcodeFolder + "/" + picTypeFolder + "/" + new Date().getTime() + "_" + picUrl.substring(picUrl.lastIndexOf("/") + 1)));
                IOUtils.write(getCosFileByteArray(picUrl), zip);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("下载图片失败genGoodsPicZip", e);
            }
        }
    }


    public byte[] getFileBytes(String filePath) {
        File file = new File(filePath);
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] fileBytes = new byte[bis.available()];
            bis.read(fileBytes);
            bis.close();
            return fileBytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("读取文件字节为空！！！");
        return null;
    }


    public List<GoodsInfoUploadVo> readGoodsExcel(MultipartFile goodsFile) {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        importParams.setStartRows(0);

        List<GoodsInfoUploadVo> readAll = null;
        try {
            readAll = ExcelImportUtil.importExcel(goodsFile.getInputStream(), GoodsInfoUploadVo.class, importParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("读取到商品信息：\n{}", JSON.toJSONString(readAll));
        for(GoodsInfoUploadVo uploadVo : readAll) {
            if(StringUtils.isNotEmpty(uploadVo.getHsCode()) && !StringUtils.ifNumberStr(uploadVo.getHsCode())) {
                throw new ServiceException("HSCode填写有误，请修改后重新导入");
            }
        }
        return readAll;
    }

    public List<GoodsInfoUploadUpdateVo> readGoodsUpdateExcel(MultipartFile goodsFile) {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        importParams.setStartRows(0);

        List<GoodsInfoUploadUpdateVo> readAll = null;
        try {
            readAll = ExcelImportUtil.importExcel(goodsFile.getInputStream(), GoodsInfoUploadUpdateVo.class, importParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("读取到更新商品信息：\n{}", JSON.toJSONString(readAll));
        return readAll;
    }

    public List<GoodsInfoStockUpdateVo> readGoodsStockExcel(MultipartFile goodsFile) {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        importParams.setStartRows(0);

        List<GoodsInfoStockUpdateVo> readAll = null;
        try {
            readAll = ExcelImportUtil.importExcel(goodsFile.getInputStream(), GoodsInfoStockUpdateVo.class, importParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("读取到更新商品库存信息：\n{}", JSON.toJSONString(readAll));
        return readAll;
    }


    public String getPicSortNoByUrl(String url) {
        if(url.contains("_") && url.contains(".")) {
            url = url.substring(url.lastIndexOf("_") + 1, url.lastIndexOf("."));
        }
        if(StringUtils.ifNumberStr(url)) {
            return url;
        }
        return "0";
    }


    @Override
    public void exportGoodsInfo(HttpServletResponse response, List<GoodsInfo> list, Integer userCompanyType) {
        List<GoodsInfoExportRespVo> expList = new ArrayList<>();

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("goods_shelves_status");
        List<SysDictData> shelfDict = sysDictDataService.selectDictDataList(sysDictData);
        Map<String, String> shelfMap = shelfDict.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

        List<CatalogInfo> catalogInfoList = catalogInfoService.selectCatalogInfoList(new CatalogInfo());
        Map<Long, String> catalogMap = catalogInfoList.stream().collect(Collectors.toMap(CatalogInfo::getId,CatalogInfo::getCatalogName));

        BigDecimal bd1000 = new BigDecimal(1000);
        SimpleDateFormat saveFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat expFormat = new SimpleDateFormat("yyyy/MM/dd");
        for(int i=0; i<list.size(); i++) {
            GoodsInfo goods = list.get(i);
            GoodsInfoExportRespVo exp = new GoodsInfoExportRespVo();
            BeanUtils.copyProperties(goods, exp);
            // 序号
            exp.setSeqNo(i+1);
            // 分类
            List<String> goodsCatalog = new ArrayList<>();
            if(StringUtils.isNotEmpty(exp.getGoodsClassification())) {
                String[] goodsCatalogArray = exp.getGoodsClassification().split(",");
                for(String catalogId : goodsCatalogArray) {
                    if(StringUtils.isNotEmpty(catalogId.trim()) && catalogMap.containsKey(Long.valueOf(catalogId))) {
                        goodsCatalog.add(catalogMap.get(Long.valueOf(catalogId)));
                    }
                }
            }

            if(ObjectUtil.isNotNull(goodsCatalog) && goodsCatalog.size() > 0) {
                exp.setGoodsClassification(StringUtils.join(goodsCatalog.toArray(), ","));
            }

            // 上架状态
            if(ObjectUtil.isNotNull(exp.getPlatOnShelf()) && shelfMap.containsKey(exp.getPlatOnShelf().toString())) {
                exp.setPlatOnShelfStr(shelfMap.get(exp.getPlatOnShelf().toString()));
            }

            // 毛重 > 1000时，最终运费 = 首重运费 + 续重运费
            if (ObjectUtil.isNotNull(goods.getGrossWeight())
                    && ObjectUtil.isNotNull(goods.getFreightAmount())
                    && ObjectUtil.isNotNull(goods.getFreightRenewalAmount())
            ) {
                if(goods.getGrossWeight().compareTo(bd1000) > 0) {
                    exp.setFinalFreightAmount(goods.getFreightAmount().add(goods.getFreightRenewalAmount()).setScale(2, 4));
                }

            }

            // 效期格式
            if(StringUtils.isNotEmpty(exp.getExpirDate())) {
                try {
                    exp.setExpirDate(expFormat.format(saveFormat.parse(exp.getExpirDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            expList.add(exp);
        }
        if(userCompanyType == 1) {
            ExcelUtil<GoodsInfoExportRespVo> util = new ExcelUtil<GoodsInfoExportRespVo>(GoodsInfoExportRespVo.class);
            util.exportExcel(response, expList, "商品信息数据");
        } else if(userCompanyType == 2) {
            List<GoodsInfoExportForSellerRespVo> sellerExpList = new ArrayList<>();
            for(GoodsInfoExportRespVo expVo : expList) {
                GoodsInfoExportForSellerRespVo sellerVo = new GoodsInfoExportForSellerRespVo();
                BeanUtils.copyProperties(expVo, sellerVo);
                sellerExpList.add(sellerVo);
            }

            ExcelUtil<GoodsInfoExportForSellerRespVo> util = new ExcelUtil<GoodsInfoExportForSellerRespVo>(GoodsInfoExportForSellerRespVo.class);
            util.exportExcel(response, sellerExpList, "商品信息数据");
        } else if(userCompanyType == 3) {
            List<GoodsInfoExportForSupplierRespVo> spExpList = new ArrayList<>();
            for(GoodsInfoExportRespVo expVo : expList) {
                GoodsInfoExportForSupplierRespVo spVo = new GoodsInfoExportForSupplierRespVo();
                BeanUtils.copyProperties(expVo, spVo);
                spExpList.add(spVo);
            }

            ExcelUtil<GoodsInfoExportForSupplierRespVo> util = new ExcelUtil<GoodsInfoExportForSupplierRespVo>(GoodsInfoExportForSupplierRespVo.class);
            util.exportExcel(response, spExpList, "商品信息数据");
        } else {
            throw new ServiceException("系统账号权限异常");
        }

    }

    public byte[] getCosFileByteArray(String cosUrl) throws IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        // 创建URL
        URL url = new URL(UriEncoder.encode(cosUrl));
        final byte[] by = new byte[1024];
        // 创建链接
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        InputStream is = conn.getInputStream();
        // 将内容读取内存中
        int len;
        while ((len = is.read(by)) != -1) {
            data.write(by, 0, len);
        }
        return data.toByteArray();
    }


    /**
     * 导入修改商品信息
     * @param goodsFile
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String uploadGoodsInfoUpdate(MultipartFile goodsFile, HttpServletResponse response, LoginUser loginUser) throws IOException {
        List<GoodsInfoUploadUpdateVo> goodsList = readGoodsUpdateExcel(goodsFile);

        if(Objects.isNull(goodsList) || goodsList.size() <= 0) {
            return "表格数据不能为空！";
        }

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("goods_shelves_status");
        List<SysDictData> shelfDict = sysDictDataService.selectDictDataList(sysDictData);
        Map<String, String> shelfMap = shelfDict.stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));

        SimpleDateFormat importFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat saveFormat = new SimpleDateFormat("yyyy-MM-dd");
        GoodsInfo existQueryParam = new GoodsInfo();
        for(GoodsInfoUploadUpdateVo updateVo : goodsList) {
            if(StringUtils.isBlank(updateVo.getGoodsOutCode())) {
                continue;
            }

            if(StringUtils.isNotEmpty(updateVo.getPlatOnShelfStr()) && shelfMap.containsKey(updateVo.getPlatOnShelfStr())) {
                updateVo.setPlatOnShelf(Integer.valueOf(shelfMap.get(updateVo.getPlatOnShelfStr())));
            }

            existQueryParam.setGoodsOutCode(updateVo.getGoodsOutCode());
            existQueryParam.setDelFlag(1);
            List<GoodsInfo> exist = goodsInfoMapper.selectGoodsInfoList(existQueryParam);
            GoodsInfo existGoodsInfo = exist.get(0);
            if(StringUtils.isNotEmpty(updateVo.getExpirDate()) && updateVo.getExpirDate().contains("/")) {
                try {
                    updateVo.setExpirDate(saveFormat.format(importFormat.parse(updateVo.getExpirDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            goodsInfoMapper.updateGoodsInfoByOutCode(updateVo);

            // 商品价格变动，1、记录日志表  2、记录商品改价表
            if(ObjectUtil.isNotNull(existGoodsInfo.getCostPrice()) && ObjectUtil.isNotNull(updateVo.getCostPrice())
                    && existGoodsInfo.getCostPrice().compareTo(updateVo.getCostPrice()) != 0) {
                SysOperLog operLog = new SysOperLog();
                operLog.setTitle("商品改价-" + existGoodsInfo.getGoodsCode());
                operLog.setOperatorType(2);
                operLog.setMethod("批量导入修改价格");
                operLog.setRequestMethod("POST");
                operLog.setOperatorType(1);
                operLog.setOperName(loginUser.getUser().getNickName());
                operLog.setDeptName(loginUser.getUser().getDept().getDeptName());
                operLog.setOperUrl("/system/goods/import/update");
                operLog.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
                operLog.setOperParam("原价格：" + existGoodsInfo.getCostPrice() + ",修改后价格：" + updateVo.getCostPrice());
                operLog.setStatus(0);
                operLog.setOperTime(new Date());
                operLogMapper.insertOperlog(operLog);

                GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
                goodsPriceInfo.setGoodsId(existGoodsInfo.getId());
                goodsPriceInfo.setGoodsName(existGoodsInfo.getGoodsName());
                goodsPriceInfo.setBeforePrice(existGoodsInfo.getCostPrice());
                goodsPriceInfo.setAfterPrice(updateVo.getCostPrice());
                goodsPriceInfo.setOptUserId(loginUser.getUserId());
                goodsPriceInfo.setOptUserName(loginUser.getUser().getNickName());
                goodsPriceInfo.setOptTime(new Date());
                goodsPriceInfoMapper.saveGoodsPriceInfo(goodsPriceInfo);
            }
        }
        return "导入成功";
    }

    /**
     * 导入修改商品信息
     * @param goodsFile
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String uploadGoodsStockUpdate(MultipartFile goodsFile, HttpServletResponse response, LoginUser loginUser) throws IOException {
        List<GoodsInfoStockUpdateVo> goodsList = readGoodsStockExcel(goodsFile);

        if(Objects.isNull(goodsList) || goodsList.size() <= 0) {
            return "表格数据不能为空！";
        }

        for(GoodsInfoStockUpdateVo stockUpdateVo : goodsList) {
            goodsInfoMapper.updateGoodsStockByGoodsCode(stockUpdateVo);
        }
        return "导入成功";
    }



    /**
     * 获取销方的价格比例
     * @param sellerId
     * @return
     */
    public BigDecimal getSellerProportion(String sellerId) {
        if(StringUtils.isNotEmpty(sellerId)) {
            SellerInfo sellerInfo = sellerInfoService.selectSellerInfoById(sellerId);
            if(ObjectUtil.isNotNull(sellerInfo)
                    && ObjectUtil.isNotNull(sellerInfo.getIfAgent())
                    && sellerInfo.getIfAgent() == 0
                    && StringUtils.isNotEmpty(sellerInfo.getParentAgentId())) {
                SellerInfo agent = sellerInfoService.selectSellerInfoById(sellerInfo.getParentAgentId());
                if(ObjectUtil.isNotNull(agent) && ObjectUtil.isNotNull(agent.getProportion())) {
                    BigDecimal proportion = agent.getProportion().divide(new BigDecimal(100)).setScale(2, 4).add(new BigDecimal(1));
                    return proportion;
                }
            }
        }
        return null;
    }


    /**
     * 计算最终运费
     * @param goodsInfo
     * @return
     */
    public BigDecimal calcFinalFreightAmount(GoodsInfo goodsInfo) {
        // 首重运费或物品重量为空，无法计算，直接返回空
        if(ObjectUtil.isNull(goodsInfo.getFreightAmount()) || ObjectUtil.isNull(goodsInfo.getGoodsWeight())) {
            return null;
        }

        // 续重运费为空，返回首重运费
        if(ObjectUtil.isNull(goodsInfo.getFreightRenewalAmount())) {
            return goodsInfo.getFreightAmount();
        }

        BigDecimal bd1000 = new BigDecimal(1000);
        // 1、物品重量<1KG, 最终运费 = 首重运费
        if(goodsInfo.getGoodsWeight().compareTo(bd1000) < 0) {
            return goodsInfo.getFreightAmount();
        }
        // 2、物品重量>1KG, 最终运费 = 首重运费 + 续重运费 * (重量 / 1000)
        int renewCount = goodsInfo.getGoodsWeight().divide(bd1000).intValue();
        BigDecimal renewAmount = goodsInfo.getFreightRenewalAmount().multiply(new BigDecimal(renewCount)).setScale(2, 4);
        BigDecimal finalAmount = goodsInfo.getFreightAmount().add(renewAmount);
        return finalAmount;
    }

}
