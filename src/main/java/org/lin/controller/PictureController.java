package org.lin.controller;


import org.lin.entity.vo.R;
import org.lin.exception.BussinessException;
import org.lin.service.IPictureService;
import org.lin.utils.FileUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@RestController
@RequestMapping("/picture")
@ControllerAdvice
public class PictureController {

    @Resource
    private IPictureService pictureService;

    @PostMapping()
    public R<Integer> upload(@RequestParam("file")MultipartFile file){
        return pictureService.upload(file,null);
    }

}
