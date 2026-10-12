package org.dromara.common.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoragePlatformType {

    MINIO("minio", "MinIO"),
    ALIYUN("aliyun", "阿里云OSS"),
    TENCENT("tencent", "腾讯云COS"),
    HUAWEI("huawei", "华为云OBS"),
    QINIU("qiniu", "七牛云"),
    UNKNOWN("unknown", "未知");

    private final String code;
    private final String desc;

    public static StoragePlatformType fromCode(String code) {
        for (StoragePlatformType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return StoragePlatformType.UNKNOWN;
    }

}
