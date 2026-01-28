package com.investor.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSSClient;
import com.investor.config.OssConfig;
import com.investor.service.IOssService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class OsssServiceImpl implements IOssService {
    private final OssConfig ossConfig;
    private final OSSClient ossClient;

    @Override
    public String uploadFile(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = "avatar/" + IdUtil.simpleUUID() + suffix;
            ossClient.putObject(ossConfig.getBucket(), fileName, inputStream);
            return "http://" + ossConfig.getBucket() + "." + ossConfig.getEndpoint() + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
