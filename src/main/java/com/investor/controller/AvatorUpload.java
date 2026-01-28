package com.investor.controller;

import com.investor.common.Result;
import com.investor.service.IOssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AvatorUpload {
    private final IOssService ossService;
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        String result=ossService.uploadFile(file);
        return Result.successMsg("上传成功",result);
    }
    
}