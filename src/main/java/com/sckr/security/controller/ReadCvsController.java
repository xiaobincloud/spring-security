package com.sckr.security.controller;

import com.csvreader.CsvReader;
import com.sckr.security.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author xiaobin
 * @Title: ReadCvsController
 * @date 2019/05/17
 * @Description:
 */
@Api(value = "读取CVS文档", tags = {"读取噪声文档信息"})
@RestController
public class ReadCvsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCvsController.class);

    @PostMapping(value = "/uploadFile")
    @ApiOperation(value="上传文件", notes="返回视频路径")
    public Result uploadFile(@RequestParam("file") MultipartFile file){
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file.getName());
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            LOGGER.warn("产生临时文件失败:{}", e.getMessage(), e);
        }
        try {
            CsvReader csvReader = new CsvReader(tmpFile.getPath());
            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                System.out.println(csvReader.getRawRecord());
                // 读这行的某一列
                System.out.println(csvReader.get("Link"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result();
    }
}
