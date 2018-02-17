package com.fangminx.service.house;

import com.fangminx.ApplicationTests;
import com.fangminx.service.IQiNiuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class QiNiuServiceTests extends ApplicationTests{
    @Autowired
    private IQiNiuService qiNiuService;

    @Test
    public void testUploadFile(){
        String fileName = "/Users/fangmin/project/xunwu/tmp/download.jpg";
        File file = new File(fileName);
        Response response = null;
        try {
            response = qiNiuService.uploadFile(file);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
        String key = "FiauvRP_11ZnZP9Qvn2kt-BC9zex";
        try {
            Response response = qiNiuService.delete(key);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
