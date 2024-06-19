package com.pockyr.utils;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class qiniuKodoUtils {
    public static String uploadImage(MultipartFile image) {
        // 上传到云服务
        Configuration cfg = new Configuration(Region.huanan()); // 华南存储区域
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);
        // 配置信息
        String accessKey = "*****";
        String secretKey = "*****";
        String bucket = "pockyr-public";
        String filename = image.getOriginalFilename();
        // 使用uuid避免文件名重复
        String key = "images/" + UUID.randomUUID() + filename;
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
        return "http://sfb62n5c3.hn-bkt.clouddn.com/" + key;
    }
}
