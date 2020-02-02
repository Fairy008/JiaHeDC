package com.jh.collector.service.impl;

import com.jh.collector.entity.Image;
import com.jh.collector.mapping.ImageMapper;
import com.jh.collector.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * XZG 2019-07-25  16:30
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;


    @Override
    public Integer insertImgUrls(List<Image> imageList) {
        return imageMapper.insertImgUrls(imageList);
    }

    @Override
    public void updateImages(List<Image> imageEntityList) {
        imageEntityList.forEach(image -> {
            imageMapper.updateImages(image);
        });
    }
}
