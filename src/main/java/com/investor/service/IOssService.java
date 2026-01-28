package com.investor.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOssService {
    String uploadFile(MultipartFile file);
    
}
