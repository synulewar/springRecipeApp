package com.synulewar.receipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public void saveImageFile(Long id, MultipartFile file);
}
