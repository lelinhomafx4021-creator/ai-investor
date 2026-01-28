package com.investor.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "oss")
public class OssConfig {
    private    String endpoint;
    private  String bucket;
    private      String accessKey;
    private      String accessSecret;

    @Bean
    public OSS ossClient(){
        return  new OSSClientBuilder().build(endpoint,accessKey,accessSecret);
    }
}