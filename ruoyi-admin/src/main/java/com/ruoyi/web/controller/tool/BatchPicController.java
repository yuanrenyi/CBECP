package com.ruoyi.web.controller.tool;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.ProjectConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.GoodsPicInfo;
import com.ruoyi.system.service.ICosFileService;
import com.ruoyi.system.service.IGoodsPicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/batch/pic")
@Slf4j
public class BatchPicController {

    @Autowired
    private IGoodsPicInfoService picInfoService;

    @Autowired
    private ICosFileService cosFileService;

    @GetMapping("/handle1")
    public void handle1() {
        int count = 0;
        List<GoodsPicInfo> hisList = picInfoService.hisPicInfoList();
        while(ObjectUtil.isNotNull(hisList) && hisList.size() > 0) {
            for(GoodsPicInfo picInfo : hisList) {
                String srcPicUrl = picInfo.getPicUrl();
                srcPicUrl = srcPicUrl.replaceAll(ProjectConstants.PROJECT_DOMAIN_IP, ProjectConstants.PROJECT_UPLOADPATH);
                srcPicUrl = srcPicUrl.replaceAll(ProjectConstants.PROJECT_DOMAIN_REALM, ProjectConstants.PROJECT_UPLOADPATH);
                File file = new File(srcPicUrl);
                if(file.exists()) {
                    String url = "";
                    try {
                        url = cosFileService.uploadFileWithFolder(picInfo.getGoodsId() + "-" + picInfo.getPicVersion() + "/" + picInfo.getPicType(), FileUtils.getMultipartFile(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.info("id={}, url={}图片上传到腾讯云COS失败！", picInfo.getId(), srcPicUrl);
                        continue;
                    }
                    picInfo.setPicUrl(url);
                    int i = picInfoService.updateGoodsPicInfo(picInfo);
                    count = count + i;
                } else {
                    log.info("file={}文件不存在", srcPicUrl);
                }
            }
            hisList = picInfoService.hisPicInfoList();
        }

        new Thread(){

        }.start();

        log.info("执行结束，成功{}条数据", count);
    }

    @GetMapping("/handle2")
    public void handle2() {
        int count = 0;
        List<GoodsPicInfo> hisList = picInfoService.hisPicInfoList2();
        while(ObjectUtil.isNotNull(hisList) && hisList.size() > 0) {
            for(GoodsPicInfo picInfo : hisList) {
                String srcPicUrl = picInfo.getPicUrl();
                srcPicUrl = srcPicUrl.replaceAll(ProjectConstants.PROJECT_DOMAIN_IP, ProjectConstants.PROJECT_UPLOADPATH);
                srcPicUrl = srcPicUrl.replaceAll(ProjectConstants.PROJECT_DOMAIN_REALM, ProjectConstants.PROJECT_UPLOADPATH);
                File file = new File(srcPicUrl);
                if(file.exists()) {
                    String url = "";
                    try {
                        url = cosFileService.uploadFileWithFolder(picInfo.getGoodsId() + "-" + picInfo.getPicVersion() + "/" + picInfo.getPicType(), FileUtils.getMultipartFile(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.info("id={}, url={}图片上传到腾讯云COS失败！", picInfo.getId(), srcPicUrl);
                        continue;
                    }
                    picInfo.setPicUrl(url);
                    int i = picInfoService.updateGoodsPicInfo(picInfo);
                    count = count + i;
                } else {
                    log.info("file={}文件不存在", srcPicUrl);
                }
            }
            hisList = picInfoService.hisPicInfoList2();
        }

        new Thread(){

        }.start();

        log.info("执行结束，成功{}条数据", count);
    }
}
