package org.lin.utils;


import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.lin.common.FileType;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author LvWei
 * @Date 2024/1/24 15:35
 */
@Slf4j
public class FileUtils {

    private static final String endpoint = "106.13.196.131";

    private static final String accessKeyId = "FC5SVCzrA3dUSAiqKz8m";

    private static final String accessKeySecret = "WZyq8Iq73xr2iZvExqmnAEmxxZQe0qJDidC3agRz";

    private static final String bucketname = "rest";


    public static String upload(InputStream inputStream, String module, String originalFilename) {

        MinioClient minioClient = MinioClient.builder().endpoint(endpoint,9000,true).credentials(accessKeyId,accessKeySecret).build();
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketname).build());
            if (!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketname).build());
            }
            //构建日期路径：avatar/2020/02/26/文件名
            String folder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            //文件名：时间 yyyyMMddHHmmss + uuid
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + UUID.randomUUID();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String key = module + "/" + folder + "/" + fileName + fileExtension;

            File file = is2File(fileName, inputStream);

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketname).object(fileName).stream(inputStream, file.length(),-1).contentType(fileExtension).build());

        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件异常",e);
        }

        //返回url地址
        return "https://" + bucketname + "." + endpoint + "/";
    }

    public static File is2File(String fileName, InputStream is) throws IOException {
        String[] filename = fileName.split("\\.");
        File file = File.createTempFile(UUID.randomUUID().toString(), "." + filename[1]);
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    public static FileType getFileType(InputStream is) throws IOException {
        byte[] src = new byte[28];
        is.read(src, 0, 28);
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        FileType[] fileTypes = FileType.values();
        for (FileType fileType : fileTypes) {
            if (stringBuilder.toString().startsWith(fileType.getValue())) {
                return fileType;
            }
        }
        return null;
    }
}
