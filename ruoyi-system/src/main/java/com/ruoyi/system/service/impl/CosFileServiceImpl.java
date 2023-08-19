package com.ruoyi.system.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.tencentcos.TCCOSConfig;
import com.ruoyi.system.domain.vo.OssRecoverVO;
import com.ruoyi.system.service.ICosFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CosFileServiceImpl implements ICosFileService {

    @Autowired
    private COSClient cosClient;

    @Autowired
    private TCCOSConfig tccosConfig;

    //    视频后缀 校验视频格式
    public static final String VIDEO_SUFFIX = "wmv,avi,dat,asf,mpeg,mpg,rm,rmvb,ram,flv,mp4,3gp,mov,divx,dv,vob,mkv,qt,cpk,fli,flc,f4v,m4v,mod,m2t,swf,webm,mts,m2ts";
    //    图片格式
    public static final String IMG_SUFFIX = "jpg,png,jpeg,gif,svg";
    //    音频格式
    public static final String AUDIO_SUFFIX = "cda,wav,mp1,mp2,mp3,wma,vqf";


    @Override
    public String uploadFile(MultipartFile file) {
        String url ="";
        try {
            url = uploadFile(Objects.requireNonNull(file.getOriginalFilename()),file.getInputStream());
        }catch (IOException e) {
            throw new ServiceException("图片上传失败");
        }
        return url;
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream) {
        String url = "";
        assert fileName != null;
        int split = fileName.lastIndexOf(".");
        // 文件后缀，用于判断上传的文件是否是合法的
        String suffix = fileName.substring(split+1);
        fileName = tccosConfig.getPath()+ fileName;
        if(IMG_SUFFIX.contains(suffix) || VIDEO_SUFFIX.contains(suffix) || AUDIO_SUFFIX.contains(suffix)) {
            // 1 初始化用户身份信息（secretId, secretKey）。
            COSCredentials cred = new BasicCOSCredentials(tccosConfig.getSecretId(), tccosConfig.getSecretKey());
            // 2 设置 bucket 的区域, COS 地域的简称请参照
            Region region = new Region(this.tccosConfig.getRegion());
            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端。
            COSClient cosClient = new COSClient(cred, clientConfig);
            // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
            // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
            ExecutorService threadPool = Executors.newFixedThreadPool(32);

            // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
            TransferManager transferManager = new TransferManager(cosClient, threadPool);

            // 设置高级接口的配置项
            // 分块上传阈值和分块大小分别为 5MB 和 1MB
            TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
            transferManagerConfiguration.setMultipartUploadThreshold(5*1024*1024);
            transferManagerConfiguration.setMinimumUploadPartSize(1*1024*1024);
            transferManager.setConfiguration(transferManagerConfiguration);


            String bucketName = tccosConfig.getBucketName();//储存桶名称
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, new ObjectMetadata());

            try {
                Upload upload = transferManager.upload(putObjectRequest);//上传
                UploadResult uploadResult = upload.waitForUploadResult();
                uploadResult.getKey();//上传后的文件名字
            } catch (CosServiceException e) {
                e.printStackTrace();
                throw new ServiceException("上传连接获取失败");
            } catch (CosClientException e) {
                e.printStackTrace();
                throw new ServiceException("上传连接获取失败");
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new ServiceException("上传连接获取失败");
            }finally {
                transferManager.shutdownNow(true);
            }

           url = tccosConfig.getUrl()+fileName;
        }else {
            //错误的类型，返回错误提示
            throw new ServiceException("请选择正确的文件格式");
        }

        return url;
    }

    @Override
    public OssRecoverVO policy() {
        OssRecoverVO recoverVO = new OssRecoverVO();
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY
        String secretId = tccosConfig.getSecretId();
        String secretKey =tccosConfig.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 存储桶
        String bucketName = tccosConfig.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识
        String key = "exampleobject";

        ClientConfig clientConfig = new ClientConfig(new Region(tccosConfig.getRegion()));

        // 用来生成签名
        COSSigner signer = new COSSigner();
        // 设置签名过期时间(可选)，若未进行设置，则默认使用 ClientConfig 中的签名过期时间(1小时)
        long expireEndTime = System.currentTimeMillis() + tccosConfig.getPolicyExpire() * 1000;
        Date expiration = new Date(expireEndTime);

        // 填写本次请求的参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("param1", "value1");

        // 填写本次请求的头部
        Map<String, String> headers = new HashMap<String, String>();
        // host 必填
        headers.put(Headers.HOST, clientConfig.getEndpointBuilder().buildGeneralApiEndpoint(bucketName));
        headers.put("header1", "value1");

        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        HttpMethodName method = HttpMethodName.GET;
        String sign = signer.buildAuthorizationStr(method, key, headers, params, cred, expiration);
        String dir = tccosConfig.getPath() + expiration.getTime() + "_" + UUID.randomUUID().toString().substring(0, 8);


        // OSS accesskeyId
        recoverVO.setAccessid(secretId);
        // 协议
      /*  recoverVO.setPolicy();*/
        // 签名
        recoverVO.setSignature(sign);
        // 目录名字
        recoverVO.setDir(dir);
        // 提交地址
        recoverVO.setHost(tccosConfig.getUrl());
        // 到期时间
        recoverVO.setExpire(String.valueOf(expireEndTime / 1000));

        return recoverVO;
    }

    @Override
    public String uploadFileWithFolder(String folder, MultipartFile uploadFile) throws IOException {
        if (Objects.isNull(uploadFile)){
            throw new ServiceException("上传文件有误");
        }
        String srcFileName = uploadFile.getOriginalFilename();
//        String fileExtension = srcFileName.substring(srcFileName.lastIndexOf(".") + 1);
        // 校验上传的格式
//        if(IMG_SUFFIX.contains(fileExtension.toLowerCase())
//                || VIDEO_SUFFIX.contains(fileExtension.toLowerCase())
//                || AUDIO_SUFFIX.contains(fileExtension.toLowerCase())) {
            // 1 初始化用户身份信息（secretId, secretKey）。
            COSCredentials cred = new BasicCOSCredentials(tccosConfig.getSecretId(), tccosConfig.getSecretKey());
            // 2 设置 bucket 的区域, COS 地域的简称请参照
            Region region = new Region(tccosConfig.getRegion());
            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端。
            COSClient cosClient = new COSClient(cred, clientConfig);
            // 文件新路径
            String filePath = tccosConfig.getPath()+folder+"/"+ srcFileName;
            //上传文件
            try {
                // 指定要上传到的存储桶
                String bucketName = tccosConfig.getBucketName();
                // 指定要上传到 COS 上对象键
                String key = filePath;
                //这里的key是查找文件的依据，妥善保管
                cosClient.putObject(bucketName,key,new ByteArrayInputStream(uploadFile.getBytes()),null);
                //设置输出信息
                return tccosConfig.getUrl()+filePath;
            }
            catch (Exception e){
                e.printStackTrace();
                throw new ServiceException("上传连接获取失败");
            }finally {
                // 关闭客户端(关闭后台线程)
                cosClient.shutdown();
            }
//        }else {
//            throw new ServiceException("请选择正确的文件格式");
//        }
    }
}
