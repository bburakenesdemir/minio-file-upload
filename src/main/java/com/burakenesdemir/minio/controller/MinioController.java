package com.burakenesdemir.minio.controller;

import com.burakenesdemir.minio.service.MinioService;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class MinioController {

    @Autowired
    MinioService minioService;

    @GetMapping(path = "/buckets")
    public List<Bucket> getAll(){
        return minioService.getAllBuckets();
    }

    @PostMapping(path = "/upload" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map<String, String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        return minioService.uploadFile(file.getOriginalFilename(), file.getBytes());
    }

    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
        return minioService.getFile(file);
    }

}
