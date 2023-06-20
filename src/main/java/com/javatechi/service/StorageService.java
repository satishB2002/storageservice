package com.javatechi.service;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javatechi.entity.ImageData;
import com.javatechi.repository.StorageRepository;
import com.javatechi.util.ImageUtils;

import lombok.Builder;
@Builder
@Service
public class StorageService {
	  @Autowired
	    private StorageRepository repository;

	    public String uploadImage(MultipartFile file) throws IOException {

	        ImageData imageData = repository.save(ImageData.builder()
	                .name(file.getOriginalFilename())
	                .type(file.getContentType())
	                .imageData(ImageUtils.compressImage(file.getBytes())).build());
	        if (imageData != null) {
	            return "file uploaded successfully : " + file.getOriginalFilename();
	        }
	        return null;
	    }

	    public byte[] downloadImage(String fileName){
	        java.util.Optional<ImageData> dbImageData = repository.findByName(fileName);
	        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
	        return images;
	    }
		
	
}
