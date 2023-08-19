package com.ruoyi.common.utils.file;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件下载工具类
 */
@Slf4j
public class FileDownloadUtils {
    /**
     * 批量下载文件
     *
     * @param list     批量文件下载路径集合
     * @param request  request
     * @param response response
     */
    public static void batchDownloadGoodsFile(String barcode, List<String> list, HttpServletRequest request, HttpServletResponse response) {

        //设置压缩包的名字，date为时间戳
        String date = DateUtil.formatDate(new Date());
        String downloadName = barcode + "-" + date + ".zip";

        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
//        IOUtils.write(data, response.getOutputStream());


        //返回客户端浏览器的版本号、类型
        String agent = request.getHeader("USER-AGENT");
        try {
            //针对IE或者以IE为内核的浏览器：
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                downloadName = URLEncoder.encode(downloadName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                downloadName = new String(downloadName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
        } catch (Exception e) {
            log.error("系统异常", e);
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");

        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipOs = null;
        //循环将文件写入压缩流
        DataOutputStream os = null;
        //文件
        File file;
        try {
            zipOs = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            //设置压缩方法
            zipOs.setMethod(ZipOutputStream.DEFLATED);
            //遍历文件信息（主要获取文件名/文件路径等）
            int fileNum = 1;
            for (String t : list) {
                try {
                    //      文件存储路径
                    String path = t;
                    //      压缩文件中每个文件的文件名称
                    String name = path.substring(path.lastIndexOf("/") + 1);
                    log.info("batchDownloadFile:[filePath:{}]", path);
                    file = new File(path);
                    if (!file.exists()) {
                        log.info("商品图片地址{}不存在！！！", path);
                        continue;
                    }
                    FileInputStream fs = null;
                    try {
                        //添加ZipEntry，并将ZipEntry中写入文件流
                        zipOs.putNextEntry(new ZipEntry(name));
                        os = new DataOutputStream(zipOs);
                        fs = new FileInputStream(file);
                        byte[] b = new byte[100];
                        int length;
                        //读入需要下载的文件的内容，打包到zip文件
                        while ((length = fs.read(b)) != -1) {
                            os.write(b, 0, length);
                        }
                    } catch (Exception e) {
                        log.error("系统异常", e);
                    } finally {
                        //关闭流
                        assert fs != null;
                        fs.close();
                        zipOs.closeEntry();
                    }

                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error("系统异常", e);
        } finally {
            //关闭流
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (zipOs != null) {
                    zipOs.close();
                }
            } catch (IOException e) {
                log.error("系统异常", e);
            }
        }
    }


}
