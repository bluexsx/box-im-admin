package org.dromara.common.oss.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.oss.client.FileStorageAPI;
import org.dromara.common.oss.enums.FileType;
import org.dromara.common.oss.properties.StorageProperties;
import org.dromara.common.oss.service.FileService;
import org.dromara.common.oss.util.FileUtil;
import org.dromara.common.oss.util.ImageUtil;
import org.dromara.common.oss.vo.UploadImageVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * 文件上传服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStorageAPI fileStorageAPI;

    private final StorageProperties storageProps;

    @PostConstruct
    public void init() {
        fileStorageAPI.ensureBucket();
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = fileStorageAPI.upload(storageProps.getFilePath(), file);
        if (StringUtils.isEmpty(fileName)) {
            throw new ServiceException("文件上传失败");
        }
        String url = generUrl(FileType.FILE, fileName);
        log.info("文件文件成功,url:{}", url);
        return url;
    }

    @Override
    public UploadImageVO uploadImage(MultipartFile file, boolean withThumb) {
        try {
            UploadImageVO vo = new UploadImageVO();
            if (!FileUtil.isImage(file.getOriginalFilename())) {
                throw new ServiceException("图片格式不合法");
            }
            String fileName = fileStorageAPI.upload(storageProps.getImagePath(), file);
            if (StringUtils.isEmpty(fileName)) {
                throw new ServiceException("图片上传失败");
            }
            vo.setOriginUrl(generUrl(FileType.IMAGE, fileName));
            if (withThumb) {
                if (!FileUtil.isGif(file.getOriginalFilename())) {
                    byte[] imageByte = ImageUtil.compressForScale(file.getBytes(), 30);
                    fileName = fileStorageAPI.upload(storageProps.getImagePath(),
                        file.getOriginalFilename(), imageByte, file.getContentType());
                    if (StringUtils.isEmpty(fileName)) {
                        throw new ServiceException("图片上传失败");
                    }
                } else {
                    byte[] coverBytes = ImageUtil.extractGifCoverImage(file.getBytes());
                    if (coverBytes == null || coverBytes.length == 0) {
                        throw new ServiceException("GIF封面生成失败");
                    }
                    fileName = fileStorageAPI.upload(storageProps.getImagePath(),
                        "cover.png", coverBytes, "image/png");
                    if (StringUtils.isEmpty(fileName)) {
                        throw new ServiceException("图片上传失败");
                    }
                }
                vo.setThumbUrl(generUrl(FileType.IMAGE, fileName));
            }
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (!Objects.isNull(bufferedImage)) {
                vo.setWidth(bufferedImage.getWidth());
                vo.setHeight(bufferedImage.getHeight());
            }
            log.info("文件图片成功，url:{}", vo.getOriginUrl());
            return vo;
        } catch (IOException e) {
            log.error("上传图片失败，{}", e.getMessage(), e);
            throw new ServiceException("图片上传失败");
        }
    }

    private String generUrl(FileType fileType, String fileName) {
        String path = switch (fileType) {
            case FILE -> storageProps.getFilePath();
            case IMAGE -> storageProps.getImagePath();
            case VIDEO -> storageProps.getVideoPath();
            default -> storageProps.getFilePath();
        };
        return fileStorageAPI.getFileUrl(path, fileName);
    }

}
