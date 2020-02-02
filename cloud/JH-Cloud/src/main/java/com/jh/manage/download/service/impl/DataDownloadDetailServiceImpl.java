package com.jh.manage.download.service.impl;

import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.impl.BaseServiceImpl;
import com.jh.manage.download.entity.DataDownloadDetail;
import com.jh.manage.download.mapping.IDataDownloadDetailMapper;
import com.jh.manage.download.model.DownloadDetailParam;
import com.jh.manage.download.service.IDataDownloadDetailService;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by XZG on 2018/3/7.
 */
@Service
@Transactional
public class DataDownloadDetailServiceImpl extends BaseServiceImpl<DownloadDetailParam,DataDownloadDetail,Integer> implements IDataDownloadDetailService {

    @Autowired
    private IDataDownloadDetailMapper dataDownloadDetailMapper;


    @Override
    protected IBaseMapper<DownloadDetailParam, DataDownloadDetail, Integer> getDao() {
        return dataDownloadDetailMapper;
    }

    @Override
    public void saveBatch(List<DataDownloadDetail> detailList) {
        if (detailList != null && !detailList.isEmpty()){
            dataDownloadDetailMapper.batchInsert(detailList);
        }
    }

    @Override
    public ResultMessage findDetailList(Integer downloadId) {
        List<DataDownloadDetail> detailList = dataDownloadDetailMapper.findDetailByDownloadId(downloadId);
        return ResultMessage.success(detailList);
    }

}
