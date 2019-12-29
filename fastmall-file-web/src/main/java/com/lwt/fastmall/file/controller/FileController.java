package com.lwt.fastmall.file.controller;

import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.api.constant.FileTypeEnum;
import com.lwt.fastmall.common.util.FileTypeUtils;
import com.lwt.fastmall.common.util.IOUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author lwt
 * @date 2019/12/8 21:22
 */
@Controller
@CrossOrigin
public class FileController {

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    private String path="C:\\Users\\lwt\\IdeaProjects\\fast-mall\\fastmall-file-web\\file";

    @RequestMapping("/fileUpload")
    @ResponseBody
    public Result fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        InputStream inputStream =null;
        FileOutputStream outputStream=null;
        try {
            byte[] bytes = multipartFile.getBytes();
            FileTypeEnum fileTypeEnum = FileTypeUtils.getTypeByFileBytes(bytes);
            if (fileTypeEnum ==null){
                return ResultUtils.result(CodeEnum.PARAMS_TYPE_ERROR);
            }
            String md5Hex = DigestUtils.md5Hex(bytes);
            String fileName=md5Hex+"."+ fileTypeEnum;
            File file = new File(path, fileName);
            if (file.exists()){
                return ResultUtils.success("http://localhost:6011/fileDownload/"+fileName);
            }
            inputStream = multipartFile.getInputStream();
            outputStream = new FileOutputStream(file);
            int len = -1;
            byte[] b = new byte[1024];
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            return ResultUtils.success("http://localhost:6011/fileDownload/"+fileName);
        }catch (Exception e){
            logger.error("fileUpload()",e);
        }finally {
            IOUtils.close(inputStream);
            IOUtils.close(outputStream);
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping("/fileDownload/{fileName}")
    public Result fileDownload(@PathVariable("fileName")String fileName, HttpServletResponse response) {
        FileInputStream inputStream=null;
        ServletOutputStream outputStream=null;
        try {
            File file = new File(path, fileName);
            if (!file.exists()){
                return null;
            }
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            int len=-1;
            byte[] b=new byte[1024];
            while ((len=inputStream.read(b))!=-1){
                outputStream.write(b,0,len);
            }
        } catch (Exception e) {
            logger.error("fileDownload()",e);
        }finally {
            IOUtils.close(inputStream);
            IOUtils.close(outputStream);
        }
        return null;
    }

}
