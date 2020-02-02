package com.jh.collector.service;

import com.jh.collector.vo.SubmitSubTaskVo;
import com.jh.vo.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CalibrationInfoService {

    /**
     * 上传图片 并保存图片地址
     * @param fileList
     * @param fileList 保单号
     * XZG 2019-07-29  13:09
     * @return
     */
    ResultMessage uploadImg(List<MultipartFile> fileList, Integer policyNo);

    /**
     * 提交子任务
     * @param submitSubTaskVo
     * XZG 2019-07-29  13:09
     * @return
     */
    ResultMessage submitSubTask(SubmitSubTaskVo submitSubTaskVo);

    /**
     * 获取保单下的所有地块信息
     * @param policyNo
    * xzg 2019/7/31 14:12
    * @return
    */
    ResultMessage findCalibrationInfoListByPolicyNo(Integer policyNo);


}
