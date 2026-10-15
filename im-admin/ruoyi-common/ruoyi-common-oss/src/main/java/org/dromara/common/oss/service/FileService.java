package org.dromara.common.oss.service;

import org.dromara.common.oss.vo.UploadImageVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file);

    UploadImageVO uploadImage(MultipartFile file, boolean withThumb);

}
