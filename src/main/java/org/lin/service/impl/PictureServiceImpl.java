package org.lin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.lin.entity.bo.Picture;
import org.lin.entity.dto.MinioUploadRes;
import org.lin.entity.vo.R;
import org.lin.mapper.PictureMapper;
import org.lin.service.IPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.utils.MinioFileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Slf4j
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {


    @Resource
    private MinioFileUtil minioFileUtil;


    public R<Integer> upload(MultipartFile file) {
        MinioUploadRes uploadRes = null;
        try {
            uploadRes = minioFileUtil.uploadFile(file, "rest");
            Picture picture = new Picture();
            picture.setUrl(uploadRes.getUrl());
            picture.setBucketName(uploadRes.getBucketName());
            picture.setFileName(uploadRes.getOriginalFilename());
            save(picture);
            return new R<Integer>().data(picture.getId());
        } catch (Exception e) {
            if (uploadRes != null && uploadRes.getUrl() != null) {
                if (minioFileUtil.deleteBucketFile(uploadRes.getBucketName(), uploadRes.getOriginalFilename())) {
                    log.info("上传文件异常, 回滚上传文件成功");
                }else {
                    log.error("上传文件异常, 回滚上传文件失败");
                }
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBatch(List<Picture> deleteList) {
        deleteList.forEach(picture -> {
            this.removeById(picture.getId());
            if (!minioFileUtil.deleteBucketFile(picture.getBucketName(), picture.getFileName())) {
                log.error("删除文件失败 bucketName: {}, fileName: {}", picture.getBucketName(), picture.getFileName());
            }
        });
        return true;
    }

}
