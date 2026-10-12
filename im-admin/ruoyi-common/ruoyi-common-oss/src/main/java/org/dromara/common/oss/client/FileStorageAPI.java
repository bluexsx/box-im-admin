package org.dromara.common.oss.client;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件对象存储
 */
public interface FileStorageAPI {

    void ensureBucket();

    String upload(String path, MultipartFile file);

    String upload(String path, String name, byte[] data, String contentType);

    boolean remove(String objectKey);

    boolean isExist(String objectKey);

    byte[] getObject(String objectKey);

    String resolveObjectKey(String fileUrl);

    String getFileUrl(String path, String objectName);

}
