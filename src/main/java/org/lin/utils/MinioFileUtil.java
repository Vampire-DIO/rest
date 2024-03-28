package org.lin.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.lin.config.MinioProperties;
import org.lin.entity.dto.MinioUploadRes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MinioFile
 * @Author: jdh
 * @CreateTime: 2022-04-15
 * @Description:
 */
@Configuration
@Slf4j
public class MinioFileUtil {

    @Resource
    private MinioProperties minioProperties;

    @Resource
    private ApplicationContext applicationContext;

    private MinioClient minioClient;

    @PostConstruct
    public void init(){
        applicationContext.getBean(MinioClient.class);
    }

    /**
     * 这个是6.0.左右的版本
     * @return MinioClient
     */
//    @Bean
//    public MinioClient getMinioClient(){
//
//        String url = "http:" + minioProperties.getIp() + ":" + minioProperties.getPort();
//
//        try {
//            return new MinioClient(url, minioProperties.getAccessKey(), minioProperties.getSecretKey());
//        } catch (InvalidEndpointException | InvalidPortException e) {
//            e.printStackTrace();
//            log.info("-----创建Minio客户端失败-----");
//            return null;
//        }
//    }

    /**
     * 下面这个和上面的意思差不多，但是这个是新版本
     * 获取一个连接minio服务端的客户端
     *
     * @return MinioClient
     */
    @Bean
    public MinioClient getClient() {

        String url = "http:" + minioProperties.getIp() + ":" + minioProperties.getPort();
        MinioClient minioClient = MinioClient.builder()
                .endpoint(url)    //两种都可以,这种全路径的其实就是下面分开配置一样的
//                        .endpoint(minioProperties.getIp(),minioProperties.getPort(),minioProperties.getSecure())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        this.minioClient = minioClient;
        log.info("minio client 初始化完成...");
        return minioClient;
    }

    /**
     * 创建桶
     *
     * @param bucketName 桶名称
     */
    public void createBucket(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("创建桶的时候，桶名不能为空！");
        }

        // Create bucket with default region.
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 创建桶,固定minio容器
     *
     * @param bucketName 桶名称
     */
    public void createBucketByRegion(String bucketName, String region) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("创建桶的时候，桶名不能为空！");
        }
        MinioClient minioClient = this.getClient();

        // Create bucket with specific region.
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .region(region) //
                .build());

//        // Create object-lock enabled bucket with specific region.
//        minioClient.makeBucket(
//                MakeBucketArgs.builder()
//                        .bucket("my-bucketname")
//                        .region("us-west-1")
//                        .objectLock(true)
//                        .build());
    }

    /**
     * 修改桶名
     * (minio不支持直接修改桶名，但是可以通过复制到一个新的桶里面，然后删除老的桶)
     *
     * @param oldBucketName 桶名称
     * @param newBucketName 桶名称
     */
    public void renameBucket(String oldBucketName, String newBucketName) throws Exception {
        if (!StringUtils.hasLength(oldBucketName) || !StringUtils.hasLength(newBucketName)) {
            throw new RuntimeException("修改桶名的时候，桶名不能为空！");
        }

    }

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     */
    public void deleteBucket(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("删除桶的时候，桶名不能为空！");
        }

        minioClient.removeBucket(
                RemoveBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
    }

    /**
     * 检查桶是否存在
     *
     * @param bucketName 桶名称
     * @return boolean true-存在 false-不存在
     */
    public boolean checkBucketExist(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("检测桶的时候，桶名不能为空！");
        }

        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 列出所有的桶
     *
     * @return 所有桶名的集合
     */
    public List<Bucket> getAllBucketInfo() throws Exception {

        //列出所有桶
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets;
    }

    /**
     * 列出某个桶中的所有文件名
     * 文件夹名为空时，则直接查询桶下面的数据，否则就查询当前桶下对于文件夹里面的数据
     *
     * @param bucketName 桶名称
     * @param folderName 文件夹名
     * @param isDeep     是否递归查询
     */
    public Iterable<Result<Item>> getBucketAllFile(String bucketName, String folderName, Boolean isDeep) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("获取桶中文件列表的时候，桶名不能为空！");
        }
        if (!StringUtils.hasLength(folderName)) {
            folderName = "";
        }
        System.out.println(folderName);
        Iterable<Result<Item>> listObjects = minioClient.listObjects(
                ListObjectsArgs
                        .builder()
                        .bucket(bucketName)
                        .prefix(folderName + "/")
                        .recursive(isDeep)
                        .build());

//        for (Result<Item> result : listObjects) {
//            Item item = result.get();
//            System.out.println(item.objectName());
//        }

        return listObjects;
    }

    /**
     * 删除文件夹
     *
     * @param bucketName 桶名
     * @param objectName 文件夹名
     * @param isDeep     是否递归删除
     * @return
     */
    public Boolean deleteBucketFile(String bucketName, String objectName) {
        if (!StringUtils.hasLength(bucketName) || !StringUtils.hasLength(objectName)) {
            throw new RuntimeException("删除文件的时候，桶名或文件名不能为空！");
        }
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        } catch (Exception e) {
            log.info("删除文件失败");
            return false;
        }
    }

    /**
     * 删除文件夹
     *
     * @param bucketName 桶名
     * @param objectName 文件夹名
     * @param isDeep     是否递归删除
     * @return
     */
    public Boolean deleteBucketFolder(String bucketName, String objectName, Boolean isDeep) {
        if (!StringUtils.hasLength(bucketName) || !StringUtils.hasLength(objectName)) {
            throw new RuntimeException("删除文件夹的时候，桶名或文件名不能为空！");
        }
        try {
            ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).prefix(objectName + "/").recursive(isDeep).build();
            Iterable<Result<Item>> listObjects = minioClient.listObjects(args);
            listObjects.forEach(objectResult -> {
                try {
                    Item item = objectResult.get();
                    minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                } catch (Exception e) {
                    log.info("删除文件夹中的文件异常", e);
                }
            });
            return true;
        } catch (Exception e) {
            log.info("删除文件夹失败");
            return false;
        }
    }

    /**
     * 获取文件下载地址
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @param expires    过期时间,默认秒
     * @return
     * @throws Exception
     */
    public String getFileDownloadUrl(String bucketName, String objectName, Integer expires) throws Exception {

        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)//下载地址的请求方式
                .bucket(bucketName)
                .object(objectName)
                .expiry(expires, TimeUnit.SECONDS)//下载地址过期时间
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 获取文件上传地址(暂时还未实现)
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @param expires    过期时间,默认秒
     * @return
     * @throws Exception
     */
    public String getFileUploadUrl(String bucketName, String objectName, Integer expires) throws Exception {

        // 过期时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now().plusSeconds(60);
        PostPolicy postPolicy = new PostPolicy(bucketName, zonedDateTime);

        // 获取对象的默认权限策略
        StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        String objectPolicy = statObjectResponse.headers().get("x-amz-object-policy");

        String presignedObjectUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .method(Method.POST)
                        .expiry(expires) // 预签名的 URL 有效期为 1 小时
                        .build());


        return presignedObjectUrl;
    }

    /**
     * 创建文件夹
     *
     * @param bucketName 桶名
     * @param folderName 文件夹名称
     * @return
     * @throws Exception
     */
    public ObjectWriteResponse createBucketFolder(String bucketName, String folderName) throws Exception {

        if (!checkBucketExist(bucketName)) {
            throw new RuntimeException("必须在桶存在的情况下才能创建文件夹");
        }
        if (!StringUtils.hasLength(folderName)) {
            throw new RuntimeException("创建的文件夹名不能为空");
        }
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(folderName + "/")
                .stream(new ByteArrayInputStream(new byte[0]), 0, 0)
                .build();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);


        return objectWriteResponse;
    }

    /**
     * 检测某个桶内是否存在某个文件
     *
     * @param objectName 文件名称
     * @param bucketName 桶名称
     */
    public boolean getBucketFileExist(String objectName, String bucketName) throws Exception {
        if (!StringUtils.hasLength(objectName) || !StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("检测文件的时候，文件名和桶名不能为空！");
        }

        try {
            // 判断文件是否存在
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()) &&
                    minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build()) != null;
            return exists;
        } catch (ErrorResponseException e) {
            log.info("文件不存在 ! Object does not exist");
            return false;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 判断桶中是否存在文件夹
     *
     * @param bucketName 同名称
     * @param objectName 文件夹名称
     * @param isDeep     是否递归查询(暂不支持)
     * @return
     */
    public Boolean checkBucketFolderExist(String bucketName, String objectName, Boolean isDeep) {

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(isDeep).build());

        return results.iterator().hasNext(); // 文件夹下存在文件
    }

    /**
     * 根据MultipartFile file上传文件
     * minio 采用文件流上传，可以换成下面的文件上传
     *
     * @param file       上传的文件
     * @param bucketName 上传至服务器的桶名称
     */
    public MinioUploadRes uploadFile(MultipartFile file, String bucketName) throws Exception {

        if (file == null || file.getSize() == 0 || file.isEmpty()) {
            throw new RuntimeException("上传文件为空，请重新上传");
        }

        if (!StringUtils.hasLength(bucketName)) {
            log.info("传入桶名为空，将设置默认桶名：" + minioProperties.getBucketName());
            bucketName = minioProperties.getBucketName();
            if (!this.checkBucketExist(minioProperties.getBucketName())) {
                this.createBucket(minioProperties.getBucketName());
            }
        }

        if (!this.checkBucketExist(bucketName)) {
            throw new RuntimeException("当前操作的桶不存在！");
        }

        // 获取上传的文件名
        String filename = file.getOriginalFilename();
        assert filename != null;
        //可以选择生成一个minio中存储的文件名称
        String minioFilename = UUID.randomUUID().toString() + "_" + filename;

        InputStream inputStream = file.getInputStream();
        long size = file.getSize();
        String contentType = file.getContentType();

        // Upload known sized input stream.
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName) //上传到指定桶里面
                        .object(minioFilename)//文件在minio中存储的名字
                        //p1:上传的文件流；p2:上传文件总大小；p3：上传的分片大小
                        .stream(inputStream, size, -1) //上传分片文件流大小，如果分文件上传可以采用这种形式
                        .contentType(contentType) //文件的类型
                        .build());
        String fileDownloadUrl = this.getFileDownloadUrl(bucketName, minioFilename, -1);
        return MinioUploadRes.builder().url(fileDownloadUrl).originalFilename(minioFilename).bucketName(bucketName).build();
    }

    /**
     * 上传本地文件，根据路径上传
     * minio 采用文件内容上传，可以换成上面的流上传
     *
     * @param filePath 上传本地文件路径
     * @Param bucketName 上传至服务器的桶名称
     */
    public boolean uploadPath(String filePath, String bucketName) throws Exception {

        File file = new File(filePath);
        if (!file.isFile()) {
            throw new RuntimeException("上传文件为空，请重新上传");
        }

        if (!StringUtils.hasLength(bucketName)) {
            log.info("传入桶名为空，将设置默认桶名：" + minioProperties.getBucketName());
            bucketName = minioProperties.getBucketName();
            if (!this.checkBucketExist(minioProperties.getBucketName())) {
                this.createBucket(minioProperties.getBucketName());
            }
        }

        if (!this.checkBucketExist(bucketName)) {
            throw new RuntimeException("当前操作的桶不存在！");
        }

        String minioFilename = UUID.randomUUID().toString() + "_" + file.getName();//获取文件名称
        String fileType = minioFilename.substring(minioFilename.lastIndexOf(".") + 1);

        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(minioFilename)//文件存储在minio中的名字
                        .filename(filePath)//上传本地文件存储的路径
                        .contentType(fileType)//文件类型
                        .build());

        return this.getBucketFileExist(minioFilename, bucketName);
    }

    /**
     * 文件下载,通过http返回，即在浏览器下载
     *
     * @param response   http请求的响应对象
     * @param bucketName 下载指定服务器的桶名称
     * @param objectName 下载的文件名称
     */
    public void downloadFile(HttpServletResponse response, String bucketName, String objectName) throws Exception {
        if (response == null || !StringUtils.hasLength(bucketName) || !StringUtils.hasLength(objectName)) {
            throw new RuntimeException("下载文件参数不全！");
        }

        if (!this.checkBucketExist(bucketName)) {
            throw new RuntimeException("当前操作的桶不存在！");
        }

        //获取一个下载的文件输入流操作
        GetObjectResponse objectResponse = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());

        OutputStream outputStream = response.getOutputStream();
        int len = 0;
        byte[] buf = new byte[1024 * 8];
        while ((len = objectResponse.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
        if (outputStream != null) {
            outputStream.close();
            outputStream.flush();
        }
        objectResponse.close();
    }

    /**
     * 文件下载到指定路径
     *
     * @param downloadPath 下载到本地路径
     * @param bucketName   下载指定服务器的桶名称
     * @param objectName   下载的文件名称
     */
    public void downloadPath(String downloadPath, String bucketName, String objectName) throws Exception {
        if (downloadPath.isEmpty() || !StringUtils.hasLength(bucketName) || !StringUtils.hasLength(objectName)) {
            throw new RuntimeException("下载文件参数不全！");
        }

        if (!new File(downloadPath).isDirectory()) {
            throw new RuntimeException("本地下载路径必须是一个文件夹或者文件路径！");
        }

        if (!this.checkBucketExist(bucketName)) {
            throw new RuntimeException("当前操作的桶不存在！");
        }

        downloadPath += objectName;

        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket(bucketName) //指定是在哪一个桶下载
                        .object(objectName)//是minio中文件存储的名字;本地上传的文件是user.xlsx到minio中存储的是user-minio,那么这里就是user-minio
                        .filename(downloadPath)//需要下载到本地的路径，一定是带上保存的文件名；如 d:\\minio\\user.xlsx
                        .build());
    }

}