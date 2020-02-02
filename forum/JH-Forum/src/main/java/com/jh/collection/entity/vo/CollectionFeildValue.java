package com.jh.collection.entity.vo;

import java.util.List;

/**
 * 接受移动端采集数据参数
 * @version <1> 2018/12/5 10:28 xy: Created.
 */
public class CollectionFeildValue {

    private List<CollectionTaskFeildItem> collectionTaskFeildItemList ;//接受参数列表

    public List<CollectionTaskFeildItem> getCollectionTaskFeildItemList() {
        return collectionTaskFeildItemList;
    }

    public void setCollectionTaskFeildItemList(List<CollectionTaskFeildItem> collectionTaskFeildItemList) {
        this.collectionTaskFeildItemList = collectionTaskFeildItemList;
    }
}
