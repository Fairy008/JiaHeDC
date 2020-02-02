package com.jh.collector.service.impl;

import com.jh.collector.entity.*;
import com.jh.collector.enums.SubtaskDataPackageEnum;
import com.jh.collector.mapping.CalibrationInfoMapper;
import com.jh.collector.service.*;
import com.jh.collector.util.UploadUtil;
import com.jh.collector.vo.CalibrationInfoVo;
import com.jh.collector.vo.PolicyVo;
import com.jh.collector.vo.SubmitSubTaskVo;
import com.jh.util.UUIDUtil;
import com.jh.vo.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CalibrationInfoServiceImpl implements CalibrationInfoService {

    @Value("${imageStoreBase}")
    private String imageStoreBase;

    @Autowired
    private CalibrationInfoMapper calibrationInfoMapping;

    @Autowired
    private PolicyInfoService policyInfoService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MassifInfoService massifInfoService;

    @Autowired
    private CalibrationSubtaskService calibrationSubtaskService;

    /**
     * 保存图片地址
     * @param urlList
     * @return
     */
    private Set<Integer> saveUploadImgUrl(List<String> urlList) {
        boolean isEmpty = urlList.stream()
                .anyMatch(StringUtils::isBlank);
        if (isEmpty){
            return Collections.EMPTY_SET;
        }

        List<Image> imageList = urlList.stream()
                .map(u -> {
                    Image image = new Image();
                    image.setImgUrl(u);
                    return image;
                })
                .collect(Collectors.toList());

        //批量保存图片路径
        imageService.insertImgUrls(imageList);

        //返回图片主键
        return imageList.stream()
                .map(Image::getImgId)
                .collect(Collectors.toSet());
    }

    /**
     * 上传地块的图片
     * @param fileList
     * @return
     */
    @Override
    public ResultMessage uploadImg(List<MultipartFile> fileList,Integer policyNo) {
        ResultMessage message = new ResultMessage();
        message.setFlag(false);
        if (CollectionUtils.isEmpty(fileList)){
            message.setMsg("上传文件为空");
            return message;
        }


        String imageStorePath = generateImageRelativePath(policyNo);
        if (StringUtils.isBlank(imageStorePath)){
            message.setMsg("无法构造文件路径");
            return message;
        }
        //1、上传图片
        List<String> urlList = fileList
                .stream()
                .map(file -> UploadUtil.upload(file,imageStorePath))
                .collect(Collectors.toList());

        //2、保存图片地址
        Set<Integer> imgIdSet =  saveUploadImgUrl(urlList);

        message.setFlag(true);
        message.setData(imgIdSet);
        return message;
    }

    /**
     * 根据保单号获取 地块图片保存的相对路径  （ /任务id/子任务id/保单号/ ）
     * @param policyNo
     * @return
     */
    private String generateImageRelativePath(Integer policyNo){
        PolicyInfo policyInfo = policyInfoService.findPolicyInfoByNo(policyNo);
        if (policyInfo == null){
            return StringUtils.EMPTY;
        }

        CalibrationSubtask subtask = calibrationSubtaskService.findByVillage(policyInfo.getRegionId());
        if (subtask == null){
            return StringUtils.EMPTY;
        }

        String[] folderNames = {imageStoreBase,subtask.getTaskId().toString(),subtask.getSubtaskId().toString(),policyNo.toString()};
        return StringUtils.join(folderNames,"\\");
    }


    /**
     * 子任务提交成功 更新子任务状态为 已上传
     * @param submitSubTaskVo
     * @return
     */
    @Override
    public ResultMessage submitSubTask(SubmitSubTaskVo submitSubTaskVo) {
        ResultMessage message = new ResultMessage();
        message.setFlag(false);

        List<PolicyVo> policyVoList = submitSubTaskVo.getPolicy_list();

        //1、校验保单是否存在
        boolean policyNoAllExists =  policyVoList.stream()
                .allMatch(policyVo -> {
                    PolicyInfo policyInfo = policyInfoService.findPolicyInfoByNo(policyVo.getPolicy_no());
                    return policyInfo != null;
                });
        if (!policyNoAllExists){
            message.setMsg("保单号不存在");
            return message;
        }


        //2、地块是否是新建的 （保存定标信息）
        List<Image> imageEntityList = new ArrayList<>();
        for (PolicyVo policyVo : policyVoList){
            for (CalibrationInfoVo calibrationInfoVo : policyVo.getArea_list()){
                CalibrationInfo calibrationInfo = new CalibrationInfo();
                calibrationInfo.setCalibrateCrop(calibrationInfoVo.getCalibrateCrop());
                calibrationInfo.setCalibratorArea(calibrationInfoVo.getCalibratorArea());

                if (calibrationInfoVo.getBsm() == null){
                    // 新建地块 、定标信息
                    MassifInfo massifInfo = new MassifInfo();
                    String massifInfoBsm = massifInfoService.addMassifInfo(massifInfo);
                    calibrationInfo.setMassifInfoBsm(massifInfoBsm);

                    calibrationInfo.setBsm(generateCalibrationInfoBsm());
                    calibrationInfoMapping.insertSelective(calibrationInfo);
                } else {
                    // 更新定标信息
                    calibrationInfo.setBsm(calibrationInfoVo.getBsm());
                    calibrationInfoMapping.updateInfoById(calibrationInfo);
                }

                for (Integer imageId : calibrationInfoVo.getImg_url()){
                    Image imageEntity = new Image();
                    imageEntity.setCalibrationInfoBsm(calibrationInfo.getBsm());
                    imageEntity.setImgId(imageId);
                    imageEntityList.add(imageEntity);
                }
            }
        }

        //3、保存定标信息 与 图片的关联
        imageService.updateImages(imageEntityList);

        //4、更新子任务上传状态为已上传
        calibrationSubtaskService.modifySubtaskUploadStatus(SubtaskDataPackageEnum.UPLOAD_YES,submitSubTaskVo.getSub_task_id());

        message.setFlag(true);
        return message;
    }

    /**
     * 定标地块标识码生产规则
     * @return
     */
    private String generateCalibrationInfoBsm(){
        return UUIDUtil.generateUUID();
    }

    @Override
    public ResultMessage findCalibrationInfoListByPolicyNo(Integer policyNo) {
        PolicyInfo policyInfo = policyInfoService.findPolicyInfoByNo(policyNo);
        if (policyInfo == null){
            return ResultMessage.fail("保单号不存在");
        }
        CalibrationInfo calibrationInfoParam = new CalibrationInfo();
        calibrationInfoParam.setPolicyNo(policyNo);
        List<CalibrationInfo> calibrationInfoList = calibrationInfoMapping.selectCalibrationInfoList(calibrationInfoParam);

        Map<String,Object> resultData = new HashMap<>();
        resultData.put("policy",policyInfo);
        resultData.put("calibration",calibrationInfoList);
        return ResultMessage.success(resultData);
    }


}
