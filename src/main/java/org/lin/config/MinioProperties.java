package org.lin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author LvWei
 * @Date 2024/1/24 17:19
 */
@Component
@Data
@ConfigurationProperties(prefix = "minio.config")
public class MinioProperties {

    /**
     * API调用地址ip
     */
    private String ip;

    /**
     * API调用地址端口
     */
    private Integer port;

    /**
     * 连接账号
     */
    private String accessKey;

    /**
     * 连接秘钥
     */
    private String secretKey;

    /**
     * minio存储桶的名称
     */
    private String bucketName;

    /**
     * 文件下载到本地的路径
     */
    private String downloadDir;

    /**
     * #如果是true，则用的是https而不是http,默认值是true
     */
    private Boolean secure;

}
