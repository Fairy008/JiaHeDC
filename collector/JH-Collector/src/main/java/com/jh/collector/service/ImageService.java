package com.jh.collector.service;

import com.jh.collector.entity.Image;

import java.util.List;

/**
 * XZG 2019-07-25  16:30
 */
public interface ImageService {


    /**
     * 图片记录持久化
     * @param imageList
    * XZG 2019-07-29  13:09
    * @return
    */
    Integer insertImgUrls(List<Image> imageList);

    /**
     * 更新图片记录
     * @param imageList
    * XZG 2019-07-29  13:09
    * @return
    */
    void updateImages(List<Image> imageList);

}
