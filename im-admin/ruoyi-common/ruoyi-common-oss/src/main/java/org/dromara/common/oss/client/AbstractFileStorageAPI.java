package org.dromara.common.oss.client;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.oss.properties.StorageProperties;

/**
 * 对象命名与 URL 拼装公共逻辑
 */
public abstract class AbstractFileStorageAPI implements FileStorageAPI {

    protected final StorageProperties props;

    protected AbstractFileStorageAPI(StorageProperties props) {
        this.props = props;
    }

    protected String buildObjectName(String originalFilename) {
        String fileName = IdUtil.getSnowflakeNextIdStr();
        if (StringUtils.isNotBlank(originalFilename) && originalFilename.lastIndexOf(".") >= 0) {
            fileName += originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + "/" + fileName;
    }

    protected String fullObjectKey(String path, String objectName) {
        return path + "/" + objectName;
    }

    protected String buildPathStyleUrl(String path, String objectName) {
        return StrUtil.join("/", props.getDomain(), props.getBucketName(), path, objectName);
    }

    protected String buildVirtualHostUrl(String path, String objectName) {
        return StrUtil.join("/", props.getDomain(), path, objectName);
    }

    protected String stripDomain(String fileUrl) {
        if (StringUtils.isBlank(fileUrl) || StringUtils.isBlank(props.getDomain())) {
            return null;
        }
        String domain = props.getDomain();
        int idx = fileUrl.indexOf(domain);
        if (idx < 0) {
            return null;
        }
        String rest = fileUrl.substring(idx + domain.length());
        if (rest.startsWith("/")) {
            rest = rest.substring(1);
        }
        return StringUtils.isBlank(rest) ? null : rest;
    }

}
