package com.jh.collector.util;

import com.jh.util.DateUtil;
import com.jh.util.ceph.CephUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 上传文件
 * xzg 2019/7/30 13:18
 */
public class UploadUtil {

    /**
     * 保存图片数据至本机
     * @param file 文件对象
     * @param imageStorePath  保存文件的全路径
    * xzg 2019/7/30 13:18
    * @return java.lang.String
    */
    public static String upload(MultipartFile file,String imageStorePath){
        String fileUrl = StringUtils.EMPTY;
        if (file != null && !file.isEmpty()){
            CephUtils.mkdirs(imageStorePath);
            String fileName = DateUtil.getNowTime() + "_" + file.getOriginalFilename();
            fileUrl = StringUtils.join(new String[]{imageStorePath, fileName},"\\");
            InputStream input = null;
            OutputStream out = null;
            try {
                input = file.getInputStream();
                byte[] data = new byte[1024];
                out = new FileOutputStream(fileUrl);
                while (input.read(data) != -1){
                    out.write(data);
                }
            } catch (IOException e) {
                fileUrl = StringUtils.EMPTY;
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileUrl;
    }

}
