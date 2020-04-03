package com.burakenesdemir.minio.service;

import com.google.api.client.util.Value;
import io.minio.MinioClient;
import io.minio.messages.Bucket;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MinioService {

    @Value("${minio.buckek.name}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    @Autowired
    MinioClient minioClient;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<String, String> uploadFile(String name, byte[] content) {
        String millis = String.valueOf(System.currentTimeMillis());
        File file = new File("/deneme/" + millis + name);
        file.canWrite();
        file.canRead();
        Map<String, String> result = new HashMap<>();
        result.put("key", name);
        try {
            FileOutputStream iofs = new FileOutputStream(file);
            iofs.write(content);

            minioClient.putObject("/deneme", "/" + name, file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    public ResponseEntity getFile(String key) {
        try {
            InputStream obj = minioClient.getObject(defaultBucketName, defaultBaseFolder + "/" + key);

            byte[] content = IOUtils.toByteArray(obj);
            ByteArrayResource resource = new ByteArrayResource(content);
            obj.close();

            return ResponseEntity.ok().contentLength(key.length())
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + key + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostConstruct
    public void init() {
    }


}
