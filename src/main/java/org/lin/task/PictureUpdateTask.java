package org.lin.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.lin.entity.bo.Picture;
import org.lin.mapper.PictureMapper;
import org.lin.service.IPictureService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvwei
 * @Description: TODO
 * @DateTime: 2024/3/28 10:38
 **/
@Component
@Slf4j
public class PictureUpdateTask {


    @Resource
    private IPictureService pictureService;

    @PostConstruct
    public void init(){
        refreshPicUrl();
    }

    @Scheduled(cron = "0 0 12 * * ? ")
    public void refreshPicUrl(){
        log.info("更新图片URL task start ...");
        int size = pictureService.updateUrl();
        log.info("本次任务执行更新图片URL: {} 个, task over...", size);
    }
}
