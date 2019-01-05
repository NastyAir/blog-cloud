package com.nastryair.project.hotelmanager.hotel.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by D_Air on 2017/3/19.
 */
public class FileUploadUtil {


    /**
     * @param file    待上传文件
     * @param dirPath 文件服务器子目录
     * @return
     */
    public static String uploadImgToPath(MultipartFile file, String dirPath) throws Exception {
        String fileName = "";
        // Get the filename and build the local file path
        fileName = file.getOriginalFilename();
        // 判断文件后缀名
        String ext = fileName.substring(fileName.lastIndexOf('.'), fileName.length());

        if (ext.equals(".jpg") | ext.equals(".png") | ext.equals(".gif") | ext.equals(".bmp")) {
            fileName = UUID.randomUUID().toString() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);// uploadfile.getOriginalFilename();

            String directory = "J:\\project-temp\\upload-files\\" + dirPath;
            String filepath = Paths.get(directory, fileName).toString();

            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(file.getBytes());
            stream.close();
        } else {
            return null;
//            throw new FileNameExtensionNotSupportedException("File type \'"+ ext + "\' not supported." );
        }
        return "/upload/" + dirPath + "//" + fileName;
    }
}
