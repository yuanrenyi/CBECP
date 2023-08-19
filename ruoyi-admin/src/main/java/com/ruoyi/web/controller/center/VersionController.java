package com.ruoyi.web.controller.center;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 版本控制
 */
@RequestMapping("/version")
@RestController
@Slf4j
public class VersionController {

    @PostMapping("/update")
    public AjaxResult batchUploadPic(@RequestParam("dist") MultipartFile dist) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String saveFilePath = "/home/dev/dist/" + sdf.format(new Date()) + dist.getOriginalFilename();
        //String saveFilePath = "C:/ruoyi/uploadPath/version/" + dist.getOriginalFilename();
        File saveFile = new File(saveFilePath);
        //File nginxHtml = new File("C:/ruoyi/uploadPath/dist/");
        File nginxHtml = new File("/usr/local/nginx/html/");
        //删除ng/html中的原有文件
        if(nginxHtml.listFiles().length > 0) {
            FileUtils.deleteFile(nginxHtml.listFiles());
        }
        log.info("删除原有文件ok....");
        //解压压缩包
        R result = FileUtils.dealZip(dist, saveFile, nginxHtml);
        log.info("压缩包解压OK...");
        if (500 == result.getCode()) {
            return AjaxResult.error(result.getMsg());
        }
        return AjaxResult.success();
    }
}
