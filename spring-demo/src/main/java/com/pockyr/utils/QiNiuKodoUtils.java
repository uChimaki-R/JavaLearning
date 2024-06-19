package com.pockyr.utils;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class QiNiuKodoUtils {
    // 使用配置文件传递参数，实现软编码
    @Autowired
    private QiNiuKodoProperties qiNiuKodoProperties;

    public String uploadImage(MultipartFile image) {
        // 上传到云服务
        Configuration cfg = new Configuration(Region.huanan()); // 华南存储区域
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
        // 配置信息
        String accessKey = qiNiuKodoProperties.getAccessKey();
        String secretKey = qiNiuKodoProperties.getSecretKey();
        String bucket = qiNiuKodoProperties.getBucket();
        String filename = image.getOriginalFilename();
        // 使用uuid避免文件名重复
        String key = qiNiuKodoProperties.getFolder() + UUID.randomUUID() + filename;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(image.getInputStream().readAllBytes(), key, upToken);
            //解析上传成功的结果
            log.info("response: {}", response.bodyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 拼装图片访问路径并返回
        return qiNiuKodoProperties.getDomain() + key;
    }
}
