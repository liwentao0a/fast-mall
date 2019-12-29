package com.lwt.fastmall.common.util;

import com.lwt.fastmall.api.constant.FileTypeEnum;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lwt
 * @date 2019/12/8 21:56
 */
public class FileTypeUtils {

    /**
     * 二进制转化为16进制
     */
    private static String bytes2hex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                hex.append("0");
            }
            hex.append(temp.toLowerCase());
        }
        return hex.toString();
    }

    /**
     * 读取文件头
     */
    private static String getFileHeader(String filePath) throws IOException {
        byte[] b = new byte[28];//这里需要注意的是,每个文件的magic word的长度都不相同,因此需要使用startwith
        InputStream inputStream = null;
        inputStream = new FileInputStream(filePath);
        inputStream.read(b, 0, 28);
        inputStream.close();

        return bytes2hex(b);
    }

    /**
     * 判断文件类型
     */
    public static FileTypeEnum getTypeByFilePath(String filePath) throws IOException {
        String fileHead = getFileHeader(filePath);
        return getTypeByFileHead(fileHead);
    }

    public static FileTypeEnum getTypeByFileBytes(byte[] fileBytes) throws IOException {
        byte[] bytes=new byte[28];
        for (int i = 0; i < fileBytes.length; i++) {
            bytes[i]=fileBytes[i];
            if (i==27){
                break;
            }
        }
        String fileHead = bytes2hex(bytes);
        return getTypeByFileHead(fileHead);
    }

    public static FileTypeEnum getTypeByFileHead(String fileHead) throws IOException {
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileTypeEnum[] fileTypeEnums = FileTypeEnum.values();
        for (FileTypeEnum type : fileTypeEnums) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

}
