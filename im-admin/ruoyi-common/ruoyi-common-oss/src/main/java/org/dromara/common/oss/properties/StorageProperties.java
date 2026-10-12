package org.dromara.common.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件存储配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    /**
     * 存储厂商：minio | aliyun | tencent | huawei | qiniu
     */
    private String platform = "minio";

    /**
     * 对外访问根地址
     */
    private String domain;

    /**
     * SDK 连接地址（MinIO / 阿里云 / 华为云）
     */
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String imagePath;

    private String filePath;

    private String videoPath;

    private String stickerPath;

    /**
     * 腾讯云地域 / 七牛区域
     */
    private String region;

}
