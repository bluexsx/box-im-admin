package org.dromara.common.oss.config;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.oss.client.AliyunOssFileStorageAPI;
import org.dromara.common.oss.client.FileStorageAPI;
import org.dromara.common.oss.client.HuaweiObsFileStorageAPI;
import org.dromara.common.oss.client.MinioFileStorageAPI;
import org.dromara.common.oss.client.QiniuFileStorageAPI;
import org.dromara.common.oss.client.TencentCosFileStorageAPI;
import org.dromara.common.oss.enums.StoragePlatformType;
import org.dromara.common.oss.properties.StorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

/**
 * 文件存储配置
 */
@Configuration
@RequiredArgsConstructor
public class FileStorageConfig {

    private final StorageProperties props;

    @Bean
    FileStorageAPI fileStorageAPI() {
        if (StringUtils.isAnyBlank(props.getAccessKey(), props.getSecretKey(), props.getBucketName(),
            props.getDomain())) {
            throw new IllegalStateException("storage 配置不完整，请检查 access-key、secret-key、bucket-name、domain");
        }
        StoragePlatformType platform = StoragePlatformType.fromCode(props.getPlatform());
        return switch (platform) {
            case MINIO -> {
                if (StringUtils.isBlank(props.getEndpoint())) {
                    throw new IllegalStateException("storage.platform=minio 时请配置 endpoint");
                }
                yield new MinioFileStorageAPI(props);
            }
            case ALIYUN -> {
                if (StringUtils.isBlank(props.getEndpoint())) {
                    throw new IllegalStateException("storage.platform=aliyun 时请配置 endpoint");
                }
                yield new AliyunOssFileStorageAPI(props);
            }
            case TENCENT -> {
                if (StringUtils.isBlank(props.getRegion())) {
                    throw new IllegalStateException("storage.platform=tencent 时请配置 region");
                }
                yield new TencentCosFileStorageAPI(props);
            }
            case HUAWEI -> {
                if (StringUtils.isBlank(props.getEndpoint())) {
                    throw new IllegalStateException("storage.platform=huawei 时请配置 endpoint");
                }
                yield new HuaweiObsFileStorageAPI(props);
            }
            case QINIU -> new QiniuFileStorageAPI(props);
            default -> throw new IllegalStateException(
                "storage.platform 配置无效，支持 minio、aliyun、tencent、huawei、qiniu");
        };
    }

}
