package com.jh.show.report.service.impl;

import com.jh.show.report.service.ILayerService;
import com.jh.util.JsonByHttp;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LayerServiceImpl implements ILayerService {
    private static Logger logger = Logger.getLogger(LayerServiceImpl.class);

    /**
     *  叠加栅格影像
     * 1. 直接加载给定URL的图层信息
     * 2. 加载对URL过滤后的图层信息,如filter= "code like 'USA%' and level='2'"
     * @return  png  返回地图底图
     * @version <1> 2017年11月21日 14:30-cxw: Created.
     */
    @Override
    public void findGeoPng(HttpServletRequest req, HttpServletResponse res, String path) {
        OutputStream out = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        try {
            req.setCharacterEncoding("UTF-8");
            out = res.getOutputStream();// 得到向客户端输出二进制数据的对象
            int size = 0;
            int BUFFER_SIZE = 1024*1024;
            byte[] buf = new byte[BUFFER_SIZE];
            URL url = null;
            url = new URL(path);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            res.setHeader("Content-Type", "image/png");//设置响应的媒体类型，这样浏览器会识别出响应的是图片
            int n = 0;
            while (-1 != (n = bis.read(buf))) out.write(buf, 0, n);
            out.flush();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            try {
                if(out!=null) out.close();
                if(bis!=null) bis.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * 查询Json图层数据(将WMS图层过滤，构建成新的Vector Layer)
     * @return  json : 返回图层数据
     * @version <1> 2017年11月21日 14:30-cxw: Created.
     * @version <1> 2018年01月26日 14:30-xzg: Created.  直接返回字符串，不转成JSON
     */
    @Override
    public Object findGeoJson(HttpServletRequest req, HttpServletResponse res, String path) {
        JSONObject string_to_json = null;
        String result = null;
        try{
            JsonByHttp jsonByHttp = new JsonByHttp();
            //返回geoserver json数据
            result= jsonByHttp.sendPost(path,"");
        } catch (Exception e) {
            string_to_json = null;
            logger.info("异常"+ e.getMessage());
        } finally {
            return result;
        }

    }
}
