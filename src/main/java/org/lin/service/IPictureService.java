package org.lin.service;

import org.lin.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
public interface IPictureService extends IService<Picture> {

    public R<String> upload(MultipartFile file);
}
