package com.jh.cltApp.utils;


import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 通过Java的Zip输入输出流实现压缩和解压文件
 * 
 * @author liujiduo
 * 
 */
public final class ZipUtil {
 
    private ZipUtil() {
        // empty
    }
 
    /**
     * 压缩文件
     * @param fileList 待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zip(String path, List<File> fileList) {
        File target = null;
        if (fileList != null && fileList.size() > 0) {
            // 压缩文件名=源文件名.zip
            target = new File(path, "downloadZip.zip");
            if (target.exists()) {
                target.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry(path + "/", fileList, zos);
            } catch (IOException e) {
//                e.printStackTrace();
            } finally {
                closeQuietly(zos, fos);
            }
        }

        return target;
    }

    /**
     * 压缩文件
     * @param fileList 待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zip(String path, List<File> fileList,String fileName) {
        File target = null;
        if (fileList != null && fileList.size() > 0) {
            // 压缩文件名=源文件名.zip
            target = new File(path, fileName + ".zip");
            if (target.exists()) {
                target.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry(path + "/", fileList, zos);
            } catch (IOException e) {
//                e.printStackTrace();
            } finally {
                closeQuietly(zos, fos);
            }
        }
        return target;
    }
 
    /**
     * 扫描添加文件Entry
     * @param base 基路径
     * @param source 源文件
     * @param zos Zip文件输出流
     */
    private static void addEntry(String base, List<File> source, ZipOutputStream zos) throws IOException {
        
        for (File file : source) {
        	String entry = file.getAbsolutePath();
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024 * 10];
                fis = new FileInputStream(entry);
                bis = new BufferedInputStream(fis, buffer.length);
                int read = 0;
                zos.putNextEntry(new ZipEntry(file.getName()));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                closeQuietly(bis, fis);
            }
        }
    }
 
    /** 
    * 关闭一个或多个流对象 
    * @param closeables 可关闭的流对象列表 
    * @throws IOException 
    */  
   public static void close(Closeable... closeables) throws IOException {  
       if (closeables != null) {  
           for (Closeable closeable : closeables) {  
               if (closeable != null) {  
                   closeable.close();  
               }  
           }  
       }  
   }  
  
   /** 
    * 关闭一个或多个流对象 
    * @param closeables  可关闭的流对象列表 
    */  
   public static void closeQuietly(Closeable... closeables) {  
       try {  
           close(closeables);  
       } catch (IOException e) {  
           // do nothing  
       }  
   }  
 
}