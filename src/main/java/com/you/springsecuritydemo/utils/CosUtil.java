package com.you.springsecuritydemo.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @ClassName: COS 对象存储
 * @Description:
 * @author: D
 * @create: 2020-07-24 14:45
 **/
@Component
@Slf4j
public class CosUtil {


    @Value("${cos.secretId}")
    private String secretId;

    @Value("${cos.secretKey}")
    private String secretKey;

    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.pathPrefix}")
    private String pathPrefix;

    @Value("${cos.cosRegion}")
    private String cosRegion;

    @Value("${cos.fileFolder}")
    private String fileFolder;



    /**
     * @Description 初始化COS  获取 cosClient 对象
     **/
    public COSClient cosInit(){
        // 1 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    /**
     * @Description 获取指定目录下的文件列表
     **/
    public void cosGetFileList(){
        COSClient cosClient = cosInit();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix(fileFolder);
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return ;
            } catch (CosClientException e) {
                e.printStackTrace();
                return ;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                log.info(key);
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

        cosClose(cosClient);
    }


    /**
     * @Description 上传文件   key 是唯一值 可以讲文件夹名和文件名组合起来作为 key
     **/
    public void cosUpload(String filePath,String fileName){
        COSClient cosClient = cosInit();
        // 指定要上传的文件
        File localFile = new File(filePath);
        // 指定要上传到的存储桶
        //String bucketName = "examplebucket-1250000000";
        // 指定要上传到 COS 上对象键
        // 以/为分隔  文件夹
        String key = fileFolder+fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        cosClose(cosClient);
    }



    /**
     * @Description 下载文件，下载的文件在项目根目录下
     **/
    public void cosDownLoad(String fileName){
        COSClient cosClient = cosInit();
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        //String bucketName = "examplebucket-1250000000";
        String key = fileFolder+fileName;
        // 方法1 获取下载输入流
        //GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        //COSObject cosObject = cosClient.getObject(getObjectRequest);
        //COSObjectInputStream cosObjectInput = cosObject.getObjectContent();

        // 方法2 下载文件到本地
        // 输出的文件的路径 （文件夹名+文件名）
        String outputFilePath = fileFolder+fileName;
        File downFile = new File(outputFilePath);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);

        cosClose(cosClient);
    }


    /**
     * @Description 文件删除  根据文件夹和文件名删除  即key
     **/
    public void cosDel(String fileName){
        COSClient cosClient = cosInit();
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        //String bucketName = "examplebucket-1250000000";
        String key = fileFolder+fileName;
        cosClient.deleteObject(bucketName, key);

        cosClose(cosClient);

    }


    /**
     * @Description 关闭 cosClient 对象
     **/
    public void cosClose(COSClient cosClient){
        cosClient.shutdown();
    }

}
