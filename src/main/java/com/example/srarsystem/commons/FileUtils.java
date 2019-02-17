package com.example.srarsystem.commons;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Chen
 * @createTime 2019116 20:51
 * @description the utils for file
 */
public class FileUtils {

    public static boolean upload(MultipartFile file, String path, String fileName){
        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);
        //使用原文件名
        String realPath = path + "/" + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public static void deleteFile(MultipartFile file, String path, String fileName){
        String realPath = path + "/" + fileName;
        File dest = new File(realPath);
        if (dest.isFile() && dest.exists()) {
            dest.delete();
        }
    }
}
