package org.lin.service.impl;

import org.lin.entity.Picture;
import org.lin.entity.vo.R;
import org.lin.mybatis.PictureMapper;
import org.lin.service.IPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.utils.MinioFileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {


    @Resource
    private MinioFileUtil minioFileUtil;


    public R<String> upload(MultipartFile file){
        String fileName = file.getName();

        try {
            boolean uploadFile = minioFileUtil.uploadFile(file, "rest");
            System.out.println(uploadFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new R<String>().ok().data("asd");
    }
}
