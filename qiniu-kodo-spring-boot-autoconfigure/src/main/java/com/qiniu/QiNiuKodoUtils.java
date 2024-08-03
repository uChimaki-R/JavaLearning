package com.qiniu;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class QiNiuKodoUtils {
    // 使用配置文件传递参数，实现软编码
    private QiNiuKodoProperties qiNiuKodoProperties;

    public QiNiuKodoProperties getQiNiuKodoProperties() {
        return qiNiuKodoProperties;
    }

    public void setQiNiuKodoProperties(QiNiuKodoProperties qiNiuKodoProperties) {
        this.qiNiuKodoProperties = qiNiuKodoProperties;
    }

    public String uploadImage(MultipartFile image) throws IOException {
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
        String key = qiNiuKodoProperties.getFolder() + UUID.randomUUID() + "_" + filename;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        // 上传
        uploadManager.put(image.getInputStream().readAllBytes(), key, upToken);

        // 拼装图片访问路径并返回
        return qiNiuKodoProperties.getDomain() + key;
    }
}
