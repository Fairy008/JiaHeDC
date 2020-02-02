package com.jh.briefing.entity;

/**
 * 作物返回数据字段
 * @version <1> 2017-12-06 cxw : Created.
 */
public class CropData {

    private int cropId; //作物ID
    private String cropCode;//作物代码
    private String cropName;//作物名称

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
}
