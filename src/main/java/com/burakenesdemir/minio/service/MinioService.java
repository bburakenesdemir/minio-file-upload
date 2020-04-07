package com.burakenesdemir.minio.service;

import com.google.api.client.util.Value;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class MinioService {

    @Autowired
    MinioClient minioClient;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public File convertMultipartToFile(MultipartFile uploadedFile) throws IOException {
        String millis = String.valueOf(System.currentTimeMillis());
        File file = new File(uploadedFile.getOriginalFilename());

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(uploadedFile.getBytes());
        fos.close();

        return file;
    }

    private void uploadFileToMinio(String fileName, File file) {
        try {
            minioClient.putObject("deneme", fileName, file.getAbsolutePath());
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultipartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = "http://64.*****" + "/" + "deneme" + "/" + fileName;
            uploadFileToMinio(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

   /* public ResponseEntity getFile(String key) {
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
*/
    @PostConstruct
    public void init() {
    }


}
