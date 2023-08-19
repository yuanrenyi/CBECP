package com.ruoyi.web.controller.center;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.ImageTypeEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.domain.GoodsInfo;
import com.ruoyi.system.domain.GoodsPicInfo;
import com.ruoyi.system.domain.SellerGoodsShelf;
import com.ruoyi.system.domain.UserCompany;
import com.ruoyi.system.domain.sellergoods.SellerGoodsInfo;
import com.ruoyi.system.domain.vo.GoodsInfoExportReqVo;
import com.ruoyi.system.domain.vo.GoodsInfoVo;
import com.ruoyi.system.service.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 商品信息Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/goods")
public class GoodsInfoController extends BaseController
{
    @Autowired
    private IGoodsInfoService goodsInfoService;

    @Autowired
    private IUserCompanyService userCompanyService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IGoodsPicInfoService goodsPicInfoService;

    @Autowired
    private ISellerGoodsShelfService sellerGoodsShelfService;

    @Autowired
    private ICosFileService cosFileService;


    /**
     * 查询商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsInfo goodsInfo)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        //登录用户是供应商时，只能查看商品所属供应商为自己的商品信息
        if(userCompany.getCompanyType() == 3) {
            goodsInfo.setSupplierId(userCompany.getCompanyId());
        }

        if(userCompany.getCompanyType() == 2) {
            goodsInfo.setSellerId(userCompany.getCompanyId());
        }
        startPage();
        List<GoodsInfo> list = goodsInfoService.selectGoodsInfoList(goodsInfo);
        return getDataTable(list);
    }

    /**
     * 导出商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:info:export')")
    @Log(title = "商品信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsInfoExportReqVo reqVo)
    {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(reqVo, goodsInfo);
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 3) {
            goodsInfo.setSupplierId(userCompany.getCompanyId());
        }

        if(userCompany.getCompanyType() == 2) {
            goodsInfo.setSellerId(userCompany.getCompanyId());
            reqVo.setSellerId(userCompany.getCompanyId());
        }

        List<GoodsInfo> list = new ArrayList<>();
        if(ObjectUtil.isNotNull(reqVo.getIds()) && reqVo.getIds().size() > 0) {
            list = goodsInfoService.selectGoodsInfoByIds(reqVo);
        } else {
            list = goodsInfoService.selectGoodsInfoList(goodsInfo);
        }

        goodsInfoService.exportGoodsInfo(response, list, userCompany.getCompanyType());
    }

    /**
     * 获取商品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 2) {
            return success(goodsInfoService.selectGoodsInfoByIdAndSellerId(id, userCompany.getCompanyId()));
        }
        return success(goodsInfoService.selectGoodsInfoById(id));
    }

    /**
     * 批量导入商品信息
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:info:upload')")
    @Log(title = "商品信息导入", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult orderUpload(@RequestParam("goodsFile") MultipartFile goodsFile, HttpServletResponse response) {
        logger.info("导入商品信息：{}",goodsFile.getOriginalFilename());
        String msg = "";
        try {
            msg = goodsInfoService.uploadGoodsInfo(goodsFile, response, getLoginUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(msg);
    }

    /**
     * 批量导入商品信息
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:info:import')")
    @Log(title = "商品信息导入", businessType = BusinessType.INSERT)
    @PostMapping("/upload/v2")
    public AjaxResult orderUpload(@RequestParam("goodsFile") MultipartFile goodsFile, @RequestParam("storehouseId") String storehouseId, HttpServletResponse response) {
        logger.info("导入商品信息：{}",goodsFile.getOriginalFilename());
        String msg = "";
        try {
            msg = goodsInfoService.uploadGoodsInfoV2(goodsFile, response, getUserId(), storehouseId, getLoginUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(msg);
    }

    /**
     * 新增商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:info:add')")
    @Log(title = "商品信息新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsInfoVo goodsInfovo)
    {
        if(StringUtils.isEmpty(goodsInfovo.getGoodsBarcode())) {
            return AjaxResult.error("请输入条形码");
        }
        if(StringUtils.isNotEmpty(goodsInfovo.getHsCode()) && !StringUtils.ifNumberStr(goodsInfovo.getHsCode())) {
            return AjaxResult.error("HS编码填写有误");
        }
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() != 1 && StringUtils.isEmpty(goodsInfovo.getSupplierId())) {
            goodsInfovo.setSupplierId(userCompany.getCompanyId());
            goodsInfovo.setAuditStatus(0);
        } else {
            goodsInfovo.setAuditStatus(1);
            goodsInfovo.setAuditBy(getUsername());
            goodsInfovo.setAuditTime(new Date());
        }
        goodsInfovo.setOnShelfStatus(0);
        goodsInfovo.setCreateBy(String.valueOf(getUserId()));
        return toAjax(goodsInfoService.insertGoodsInfo(goodsInfovo));
    }

    /**
     * 修改商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:info:edit')")
    @Log(title = "商品信息修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsInfoVo goodsInfoVo)
    {
        if(StringUtils.isNotEmpty(goodsInfoVo.getHsCode()) && !StringUtils.ifNumberStr(goodsInfoVo.getHsCode())) {
            return AjaxResult.error("HS编码填写有误");
        }
        goodsInfoVo.setUpdateBy(String.valueOf(getUserId()));
        return toAjax(goodsInfoService.updateGoodsInfo(goodsInfoVo, getLoginUser()));
    }

    /**
     * 删除商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:info:remove')")
    @Log(title = "商品信息批量删除", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(goodsInfoService.deleteGoodsInfoByIds(ids));
    }


    /**
     * 批量导入商品图片
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goodspic:batch:upload')")
    @Log(title = "商品信息图片导入", businessType = BusinessType.INSERT)
    @PostMapping("/goodspic/batch/upload")
    public AjaxResult batchUploadPic(@RequestParam("zipFile") MultipartFile zipFile) {
        //获取文件全名
        String fileName = zipFile.getOriginalFilename();
        //解压目标文件夹对象（压缩文件解压到此文件夹中）
        File extractFolder = new File(RuoYiConfig.getUploadPath() + "/extract/");
        //压缩包存储目标文件对象
        File destFile = new File(RuoYiConfig.getUploadPath() + fileName);
        //文件上传路径对象
        File fileDirectory = new File(RuoYiConfig.getUploadPath());
        //当上传路径不存在时，生成上传路径
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        if(extractFolder.exists()) {
            FileUtils.delAllFile(extractFolder);
        } else {
            extractFolder.mkdirs();
        }
        //调用上面【处理压缩包文件方法】得到返回结果
        R zipResult = FileUtils.dealZip(zipFile, destFile, extractFolder);
        //失败就返回错误信息
        if (500 == zipResult.getCode()) {
            return AjaxResult.error(zipResult.getMsg());
        }

        String barcode = "";
        String version = "0";
        String type = "";

        File[] zipFolders = (File[]) zipResult.getData();
        if(Objects.isNull(zipFolders) || zipFolders.length <= 0) {
            throw new ServiceException("压缩包有误");
        }
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();

        StringBuffer error = new StringBuffer();

        for(File totalFolder : zipFolders) {
            logger.info("totalFolderName={}", totalFolder.getName());
            File[] folders = totalFolder.listFiles();
            for(File barcodeFolder : folders) {
                if(!barcodeFolder.isDirectory() || barcodeFolder.listFiles().length <= 0) {
                    continue;
                }
                String barcodeAndVersion = barcodeFolder.getName();
                if(barcodeAndVersion.contains("-")) {
                    barcode = barcodeAndVersion.split("-")[0];
                    version = barcodeAndVersion.split("-")[1];
                } else {
                    barcode = barcodeAndVersion;
                    version = "0";
                }

                goodsPicInfoService.deleteByBarcodeAndPicversion(barcode, version);

                logger.info("条码层文件夹：{}", barcodeAndVersion);
                File[] imageTypeFolders =  barcodeFolder.listFiles();
                for(File typeFolder : imageTypeFolders) {
                    if(!typeFolder.isDirectory() || typeFolder.listFiles().length <= 0) {
                        continue;
                    }
                    String imageType = typeFolder.getName();
                    imageType = ImageTypeEnums.getImageTypeValue(imageType);
                    logger.info("主图/详情图层文件夹：{}", imageType);
                    File[] images = typeFolder.listFiles();
                    for(File image : images) {
                        if(Objects.isNull(image)) {
                            continue;
                        }

                        if(image.isFile()) {
                            logger.info("文件名称：{}", image.getName());
                        }

                        String srcFileName = image.getName();
                        if(!srcFileName.contains(".")) {
                            error.append(barcode + "/" + imageType + "/" + image.getName() + "\n");
                            continue;
                        }

                        String imageName = FileUtils.getNameNotSuffix(srcFileName);
                        if(!StringUtils.ifNumberStr(imageName)) {
                            error.append(barcode + "/" + imageType + "/" + image.getName() + "\n");
                            continue;
                        }

/*                        String fileDBPath = "";
                        try {
                            String fileNamePrefix = barcode + "_" + version + "_" + imageType;
                            fileDBPath = FileUploadUtils.uploadCBECPImage(filePath, FileUtils.getMultipartFile(image), fileNamePrefix);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(StringUtils.isBlank(fileDBPath)) {
                            logger.info("IMAGE_UPLOAD_ERROR");
                            continue;
                        }
                        String url = serverConfig.getUrl() + fileDBPath;*/

                        String url = "";
                        try {
                            url = cosFileService.uploadFileWithFolder(barcode + "-" + version + "/" + imageType, FileUtils.getMultipartFile(image));
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new ServiceException("图片上传到腾讯云COS失败！");
                        }
                        //保存图片信息
                        GoodsPicInfo goodsPicInfo = new GoodsPicInfo();
                        goodsPicInfo.setGoodsId(barcode);
                        goodsPicInfo.setPicType(imageType);
                        goodsPicInfo.setPicVersion(version);
                        goodsPicInfo.setPicUrl(url);
                        goodsPicInfo.setSortNo(imageName);
                        goodsPicInfo.setCreateBy(String.valueOf(getUserId()));
                        goodsPicInfo.setCreateTime(new Date());
                        goodsPicInfoService.insertGoodsPicInfo(goodsPicInfo);
                    }

                }
            }
        }
        extractFolder.delete();

        String errorMsg = error.toString();
        if(StringUtils.isNotEmpty(errorMsg)) {
            return AjaxResult.error("以下图片名称有误，上传失败：\n" + errorMsg);
        }
        return AjaxResult.success();
    }


    /**
     * 审核商品信息
     */
    @PreAuthorize("@ss.hasPermi('goods:info:audit')")
    @Log(title = "商品信息审核", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    public AjaxResult audit(@RequestBody GoodsInfoVo goodsInfoVo)
    {
        if(StringUtils.isBlank(goodsInfoVo.getId())) {
            return AjaxResult.error("商品ID有误");
        }
        if(Objects.isNull(goodsInfoVo.getAuditStatus())) {
            return AjaxResult.error("请传入审核结果");
        }
        goodsInfoVo.setAuditBy(getUsername());
        goodsInfoVo.setAuditTime(new Date());
        return toAjax(goodsInfoService.auditGoodsInfo(goodsInfoVo, getLoginUser()));
    }

    /**
     * 商品信息平台方上架/下架
     */
    @PreAuthorize("@ss.hasPermi('goods:info:onshelf')")
    @Log(title = "商品信息平台方上架/下架", businessType = BusinessType.UPDATE)
    @PostMapping("/onshelf/plat")
    public AjaxResult platOnshelf(@RequestBody GoodsInfo goodsInfo)
    {
        if(StringUtils.isBlank(goodsInfo.getId())) {
            return AjaxResult.error("商品ID有误");
        }
        if(Objects.isNull(goodsInfo.getPlatOnShelf())) {
            return AjaxResult.error("上架/下架状态有误");
        }
        return toAjax(goodsInfoService.platOnshelf(goodsInfo));
    }

    /**
     * 销售方查看的商品列表
     * @param goodsInfo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:seller:list')")
    @GetMapping("/seller/list")
    public TableDataInfo sellerList(GoodsInfo goodsInfo) {
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        if(userCompany.getCompanyType() == 2) {
            goodsInfo.setSellerId(userCompany.getCompanyId());
        }
        startPage();
        List<SellerGoodsInfo> list = goodsInfoService.sellerGoodsList(goodsInfo);
        return getDataTable(list);
    }

    /**
     * 销售方查看商品详情
     * @param goodsInfo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:seller:detail')")
    @PostMapping("/seller/detail")
    public AjaxResult sellerDetail(@RequestBody GoodsInfo goodsInfo) {
        if(StringUtils.isEmpty(goodsInfo.getGoodsBarcode())) {
            return AjaxResult.error("请求有误");
        }
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        return AjaxResult.success(goodsInfoService.sellerGoodsDetail(goodsInfo.getGoodsBarcode(), userCompany.getCompanyId()));
    }

    /**
     * 销售方上架商品
     * @param sellerGoodsShelf
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:seller:putshelf')")
    @PostMapping("/seller/putshelf")
    @Log(title = "销售方上架商品", businessType = BusinessType.INSERT)
    public AjaxResult sellerPutshelf(@RequestBody SellerGoodsShelf sellerGoodsShelf) {
        if(StringUtils.isEmpty(sellerGoodsShelf.getGoodsId())) {
            return AjaxResult.error("商品ID有误");
        }
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        sellerGoodsShelf.setSellerId(userCompany.getCompanyId());
        sellerGoodsShelf.setSellerName(userCompany.getCompanyName());
        sellerGoodsShelf.setCreateBy(getUsername());
        sellerGoodsShelfService.insertSellerGoodsShelf(sellerGoodsShelf);
        return success();
    }

    /**
     * 销售方上架商品
     * @param sellerGoodsShelf
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:seller:putshelf')")
    @PostMapping("/seller/offshelf")
    @Log(title = "销售方下架架商品", businessType = BusinessType.DELETE)
    public AjaxResult sellerOffshelf(@RequestBody SellerGoodsShelf sellerGoodsShelf) {
        if(StringUtils.isEmpty(sellerGoodsShelf.getGoodsId())) {
            return AjaxResult.error("商品ID有误");
        }
        UserCompany userCompany = userCompanyService.queryUserCompanyByUserId(getUserId());
        int i = sellerGoodsShelfService.deleteByGoodsAndSeller(sellerGoodsShelf.getGoodsId(), userCompany.getCompanyId());
        return toAjax(i);
    }

    @PreAuthorize("@ss.hasPermi('goodspic:batch:download')")
    @PostMapping("/goodspic/batch/download")
    @Log(title = "商品信息图片下载", businessType = BusinessType.INSERT)
    public void batchDownloadPic(@RequestParam("goodsId") String goodsId, HttpServletRequest request, HttpServletResponse response) {
        //goodsInfoService.downloadGoodsPic(goodsBarcode, request, response);
        GoodsInfoVo goodsInfo = goodsInfoService.selectGoodsInfoById(goodsId);
        String goodsBarcode = goodsInfo.getPlatBarcode();
        if(StringUtils.isEmpty(goodsBarcode)) {
            goodsBarcode = goodsInfo.getGoodsBarcode();
        }
        String picVersion = goodsInfo.getPicVersion();
        if(StringUtils.isBlank(picVersion)) {
            picVersion = "0";
        }
        byte[] bytes = goodsInfoService.downloadGoodsPicByte(goodsBarcode, picVersion, response);
        try {
            genGoodsPicZip(response, bytes, goodsBarcode);
        } catch (IOException e) {
            logger.info("下载图片失败:genGoodsPicZip");
            e.printStackTrace();
        }
    }


    /**
     * 生成zip文件
     */
    private void genGoodsPicZip(HttpServletResponse response, byte[] data, String barcode) throws IOException
    {
        String date = DateUtils.getDate();
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + barcode + "-" + date + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }


    /**
     * 批量导入商品信息
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:info:batchupdate')")
    @Log(title = "商品信息批量导入修改", businessType = BusinessType.UPDATE)
    @PostMapping("/upload/update")
    public AjaxResult goodsUploadUpdate(@RequestParam("goodsUpdateFile") MultipartFile goodsFile, HttpServletResponse response) {
        logger.info("导入修改商品信息：{}",goodsFile.getOriginalFilename());
        String msg = "";
        try {
            msg = goodsInfoService.uploadGoodsInfoUpdate(goodsFile, response,  getLoginUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(msg);
    }

    /**
     * 商品信息更新共享库存
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:info:stockupdate')")
    @Log(title = "商品信息更新共享库存", businessType = BusinessType.UPDATE)
    @PostMapping("/upload/stock/update")
    public AjaxResult goodsStockUpdate(@RequestParam("goodsStockFile") MultipartFile goodsFile, HttpServletResponse response) {
        logger.info("导入修改商品库存：{}",goodsFile.getOriginalFilename());
        String msg = "";
        try {
            msg = goodsInfoService.uploadGoodsStockUpdate(goodsFile, response,  getLoginUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success(msg);
    }

    /**
     * 商品图片全部删除
     * @param id)
     * @return
     */
    @PreAuthorize("@ss.hasPermi('goods:pic:deleteall')")
    @DeleteMapping("/pic/delete/{goodsId}")
    @Log(title = "商品图片全部删除", businessType = BusinessType.DELETE)
    public AjaxResult picDeleteAll(@PathVariable("goodsId") String id) {
        if(StringUtils.isEmpty(id)){
            return AjaxResult.error("请求信息有误");
        }
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(id);
        String goodsBarcode = goodsInfo.getPlatBarcode();
        if(StringUtils.isEmpty(goodsBarcode)) {
            goodsBarcode = goodsInfo.getGoodsBarcode();
        }
        goodsPicInfoService.deleteByBarcodeAndPicversion(goodsBarcode, goodsInfo.getPicVersion());
        return AjaxResult.success();
    }

    @PostMapping("/getPicById")
    public AjaxResult getGoodsPicByGoodsId(@RequestBody GoodsInfo goodsInfo) {
        if(StringUtils.isBlank(goodsInfo.getId())) {
            return AjaxResult.error("系统错误");
        }

        GoodsInfo query = goodsInfoService.selectGoodsInfoById(goodsInfo.getId());


        String picVersion = "0";
        if(StringUtils.isEmpty(query.getGoodsBarcode())) {
            return AjaxResult.success(new ArrayList<GoodsPicInfo>());
        }
        if(StringUtils.isNotEmpty(query.getPicVersion())) {
            picVersion = query.getPicVersion();
        }

        String goodsBarcode = query.getPlatBarcode();
        if(StringUtils.isEmpty(goodsBarcode)) {
            goodsBarcode = query.getGoodsBarcode();
        }

        GoodsPicInfo goodsPicInfo = new GoodsPicInfo();
        goodsPicInfo.setGoodsId(goodsBarcode);
        goodsPicInfo.setPicVersion(picVersion);
        goodsPicInfo.setDelFlag(1);
        return AjaxResult.success(goodsPicInfoService.selectGoodsPicInfoList(goodsPicInfo));
    }

}
