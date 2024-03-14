package org.lin.entity.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Author LvWei
 * @Date 2024/3/13 16:45
 */
@Data
@Builder
public class MinioUploadRes {

    private String url;

    private String originalFilename;

    private String bucketName;
}
