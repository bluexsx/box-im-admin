package org.dromara.system.controller.system;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.oss.service.FileService;
import org.dromara.common.oss.vo.UploadImageVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/system")
@Tag(name = "文件上传")
@RequiredArgsConstructor
public class SysFileController {

    private final FileService fileService;

    @Operation(summary = "上传图片", description = "上传图片,上传后返回原图和缩略图的url")
    @PostMapping("/image/upload")
    public R<UploadImageVO> uploadImage(@RequestParam("file") MultipartFile file,@RequestParam(defaultValue = "false")Boolean withThumb) {
        return R.ok(fileService.uploadImage(file,withThumb));
    }


    @Operation(summary = "上传文件", description = "上传文件，上传后返回文件url")
    @PostMapping("/file/upload")
    public R<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return R.ok(fileService.uploadFile(file));
    }

}