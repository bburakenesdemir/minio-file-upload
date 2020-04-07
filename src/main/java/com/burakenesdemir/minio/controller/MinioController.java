package com.burakenesdemir.minio.controller;

import com.burakenesdemir.minio.service.MinioService;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/storage/")
public class MinioController {

    @Autowired
    MinioService minioService;

    @GetMapping(path = "/buckets")
    public List<Bucket> getAll(){
        return minioService.getAllBuckets();
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.minioService.uploadFile(file);
    }

    /*
    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
        return minioService.getFile(file);
    }

     */

}
