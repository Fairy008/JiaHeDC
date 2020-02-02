package com.jh.collection.entity.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 模板任务XML列表
 * @version <1> 2018/12/4 19:40 xy: Created.
 */
@XmlRootElement(name="attir")
public class CollectionTaskVo {
    private List<CollectionFieldModelVo> collectionFieldModelVoList;//任务模板字段模型

    public CollectionTaskVo(){

    }
    public CollectionTaskVo(List<CollectionFieldModelVo> collectionFieldModelVoList) {
        this.collectionFieldModelVoList = collectionFieldModelVoList;
    }

    @XmlElement(name = "Item")
    public List<CollectionFieldModelVo> getCollectionFieldModelVoList() {
        return collectionFieldModelVoList;
    }

    public void setCollectionFieldModelVoList(List<CollectionFieldModelVo> collectionFieldModelVoList) {
        this.collectionFieldModelVoList = collectionFieldModelVoList;
    }
}
