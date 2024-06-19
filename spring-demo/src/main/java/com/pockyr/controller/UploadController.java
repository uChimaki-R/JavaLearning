package com.pockyr.controller;

import com.pockyr.pojo.Result;
import com.pockyr.utils.qiniuKodoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {

    /**
     * 处理上传图片文件的请求
     * @param image 图片文件
     * @return 响应信息，包含图片访问路径
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile image){
        String path = qiniuKodoUtils.uploadImage(image);
        log.info("文件上传, 文件名: {}, 访问的url: {}", image.getOriginalFilename(), path);
        return Result.success(path);
    }
}
