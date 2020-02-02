package com.jh.forum.bbs.util;

import com.jh.entity.BaseEntity;
import com.jh.forum.bbs.Enum.AccuracyRangeEnum;
import com.jh.forum.bbs.vo.ArticleVO;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * 1.工具类
 * @version <1> 2019/3/6 18:33 xiayong: Created.
 */
public class CommonUtils {
    /**
     * 换一批 随机获取count条(比如5条)
     * @param list
     *      list结果集
     * @param count
     *      指定条数
     * @return
     */
    public static List getSubStringByRadom(List<? extends BaseEntity> list, int count){
        List backList = null;
        backList = new ArrayList<>();
        Random random = new Random();
        int backSum = 0;
        if (list.size() >= count) {
            backSum = count;
        }else {
            backSum = list.size();
        }
        for (int i = 0; i < backSum; i++) {
//			随机数的范围为0-list.size()-1
            int target = random.nextInt(list.size());
            backList.add(list.get(target));
            list.remove(target);
        }
        return backList;
    }


    /**
     * 获取开始时间
     * @return
     */
    public static LocalDate getDate(int timeFlag){

        long monthFlag = -1L;

        //近一月
        if(timeFlag == 1){
            monthFlag = -1L;
        }else if(timeFlag == 2){
            monthFlag = -3L;
        }else if(timeFlag == 3){
            monthFlag = -6L;
        }else if(timeFlag == 4){
            monthFlag = -12L;
        }

        LocalDate startDate = null;
        LocalDate currentDate = LocalDate.now();

        startDate = currentDate.plusMonths(monthFlag);
        System.out.println(currentDate);
        System.out.println(startDate);

        return startDate;
    }



    public static  String getAccuracyRange(String accuracyRange){

        if(StringUtils.isNotBlank(accuracyRange)){
            int id = Integer.parseInt(accuracyRange);
            if(id == AccuracyRangeEnum.Accuracy_range_1.getId().intValue()){
                return AccuracyRangeEnum.Accuracy_range_1.getName();
            }else if(id == AccuracyRangeEnum.Accuracy_range_2.getId().intValue()){
                return AccuracyRangeEnum.Accuracy_range_2.getName();
            }else if(id == AccuracyRangeEnum.Accuracy_range_3.getId().intValue()){
                return AccuracyRangeEnum.Accuracy_range_3.getName();
            }else if(id == AccuracyRangeEnum.Accuracy_range_4.getId().intValue()){
                return AccuracyRangeEnum.Accuracy_range_4.getName();
            }else if(id == AccuracyRangeEnum.Accuracy_range_5.getId().intValue()){
                return AccuracyRangeEnum.Accuracy_range_5.getName();
            }
        }

        return AccuracyRangeEnum.Accuracy_range_3.getName();

    }

}
