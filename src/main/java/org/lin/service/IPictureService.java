package org.lin.service;

import org.lin.entity.bo.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.vo.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
public interface IPictureService extends IService<Picture> {

    R<Integer> upload(MultipartFile file, Integer menuId);

    Boolean insertMany(List<MultipartFile> files, int menuId);
}
