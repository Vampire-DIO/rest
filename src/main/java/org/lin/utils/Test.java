package org.lin.utils;

import io.minio.*;
import io.minio.messages.Bucket;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/1/24 17:44
 */
public class Test {
    public static void main(String[] args) {

        try {
            // 创建客户端
            MinioClient minioClient =
                    MinioClient.builder()
                            // api地址
                            .endpoint("106.13.196.131",9000,false)
//                            .endpoint("http://127.0.0.1:9000")
                            // 前面设置的账号密码
                            .credentials("V3GH8Kr0tJWhc7r4EBdI", "IQOCkcBY7dXfhLkH21GSwvb9MHwgJBl2lXcvDEMj")
                            .build();

            System.out.println(minioClient);
            // 检查桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("rest").build());
            if (!found) {
                // 创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("rest").build());
            }

            //列出所有桶名
            List<Bucket> buckets = minioClient.listBuckets();
            for (Bucket i : buckets){
                System.out.println(i.name());
            }

            //删除某一个桶
//            minioClient.removeBucket(
//                    RemoveBucketArgs.builder()
//                            .bucket("桶名称")
//                            .build());



            System.out.println("开始你的操作");

            File file = new File("C:\\Users\\Administrator\\Pictures\\东财水印.jpg");

            String fileName = file.getName();
            String realFileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."));
            String fileType = fileName.substring(fileName.lastIndexOf(".")+1);


            //通过文件格式上传一个文件
            InputStream fileInputStream = new FileInputStream(file);
            long size = file.length();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("rest")
                            .object("222")
                            .stream(fileInputStream, size, -1)
                            .contentType(fileType)
                            .build());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
