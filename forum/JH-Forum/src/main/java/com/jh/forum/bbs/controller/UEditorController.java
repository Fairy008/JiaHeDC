package com.jh.forum.bbs.controller;

import com.jh.forum.ueditor.ActionEnter;
import com.jh.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ProjectName: JH-Cloud
 * @Package: com.jh.forum.bbs.controller
 * @ClassName: UEditorController
 * @Description: java类作用描述
 * @Author: wangli
 * @CreateDate: 2019/3/6 9:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/6 9:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Controller
public class UEditorController {


    @RequestMapping("/ue")
    private String showPage(){
        return "index";
    }

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
//        response.setContentType("application/json");
        String rootPath2 = request.getSession().getServletContext().getRealPath("/");
        String rootPath = ResourceUtils.getURL("classpath:").getPath();
        try {
//1

            request.setCharacterEncoding( "utf-8" );
            response.setHeader("contentType", "text/html; charset=utf-8");
            //7
            String exec = new ActionEnter(request, rootPath).exec();
            //15
            String action = request.getParameter("action");

            if(StringUtils.isNotEmpty(exec) && !StringUtils.equals(action,"config")){
                JSONObject obj =null;
                try {
                    obj=new JSONObject(exec);
                }catch (Exception e){
//                    e.printStackTrace();
//                    //复制在线图片问题、
//                    System.out.println(exec);
//
//                    int startPos = exec.indexOf("{");
//                    int endPos = exec.lastIndexOf("}");
//                    exec = exec.substring(startPos,endPos+1);
//
//                    obj=new JSONObject(exec);
//                    obj.put("url","");
//
//                    System.out.println(exec);

                    if(exec.contains("(")&&exec.contains(")")){
                        exec=(exec.split("\\("))[1];
                        exec=(exec.split("\\)"))[0];
                        obj=new JSONObject(exec);
                        obj.put("url","");
                    }
                }
                String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
                Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
                Matcher mc = pat.matcher(obj.getString("url"));//条件匹配
                while (mc.find()) {
                    String fileName = mc.group();//截取文件名后缀名
                    String dateStr = DateUtil.dateToString(new Date(), "yyyyMMdd");
                    obj.put("url", "/" + dateStr + "/" + fileName);
                    exec = obj.toString();
                }

            }
            response.getWriter().write(exec);

//            PrintWriter writer = response.getWriter();
//            writer.write(exec);
//            writer.flush();
//            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/uploadimage")
    public void uploadimage(@RequestParam("file") MultipartFile upfile) throws Exception {
        String fileName = System.currentTimeMillis() + upfile.getOriginalFilename();
        File destFile = new File("E:/AA");
        destFile.getParentFile().mkdirs();
        upfile.transferTo(destFile);
    }

}
