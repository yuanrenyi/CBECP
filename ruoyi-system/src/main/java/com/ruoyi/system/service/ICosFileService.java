package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.OssRecoverVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface ICosFileService {

    String uploadFile( MultipartFile file);

    String uploadFile(String fileName, InputStream inputStream);


    OssRecoverVO policy();

    String uploadFileWithFolder(String folder, MultipartFile uploadFile) throws IOException;

}

