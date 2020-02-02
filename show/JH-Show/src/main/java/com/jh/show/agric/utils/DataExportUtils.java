package com.jh.show.agric.utils;

import com.jh.enums.DataStatusEnum;
import com.jh.util.CollectionUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 数据集下载列名和参数
 * @version <1> 2018/11/27 lijie: Created.
 */
public class DataExportUtils implements Serializable{

    //表头

    /**估产*/
    private final static String[] YIELD_COLS={"区域","产量预估（万吨）","去年同期（万吨）","增长率（%）"};
    /**分布*/
    private static String[] AREA_COLS={"区域","YYYY（万亩）","YYYY（万亩）","YYYY（万亩）"};
    /**长势*/
    private final static String[] GROWTH_COLS={"等级","面积"};
    /**降雨*/
    private final static String[] TRMM_COLS={"区域","日期","降雨（mm）","上月同比（mm）","增长率"};
    /**地温*/
    private final static String[] T_COLS = {"区域","日期","地温（℃）","上月同比（℃）","增长率"};
    /**气温*/
    private final static String[] WEATHER_COLS = {"区域","日期","最高温","最低温"};

    private final static String[] LEVEL_COLS={"很好","好","较好","正常","较差","差","很差"};
    private final static String[] PROP_COLS={"highest","higher","high","normal","low","lower","lowest"};


    //获取标题
    public static String[] getTitle (Integer dsId,Integer year){
        String [] title =null;
        switch (dsId){
            case 1801 :
                title = AREA_COLS;
                title[1]=(year-2)+"（万亩）";
                title[2]=(year-1)+"（万亩）";;
                title[3]=year+"（万亩）";;
                break;
            case 1802 :
                title = YIELD_COLS;
                break;
            case 1803 :
                title = GROWTH_COLS;
                break;
            case 1804 :
                title = T_COLS;
                break;
            case 1805 :
                title = TRMM_COLS;
                break;
            case 1807 :
                title = WEATHER_COLS;
                break;
            default:
                break;
        }
        return title;

    }

    //获取内容
    public static String[][] getValues (Integer dsId,Map<String, Object> allMap,List list,String dataTime){
        Integer lastTwoYear = Integer.parseInt(dataTime.substring(0,4))-2;//前2年
        Integer lastYear = Integer.parseInt(dataTime.substring(0,4))-1;//前一年
        Integer currentYear = Integer.parseInt(dataTime.substring(0,4));//当年
        String [][] values =null;
        switch (dsId){
            case 1801 :
                List listA=(List)allMap.get(lastTwoYear.toString());
                List listB=(List)allMap.get(lastYear.toString());
                List listC=(List)allMap.get(currentYear.toString());
                values =new String [listA.size()][4];
                for(int i=0;i<listC.size();i++){
                    Map<String,Object> mapA = (Map<String,Object>) (listA.get(i));
                    Map<String,Object> mapB = (Map<String,Object>) (listB.get(i));
                    Map<String,Object> mapC = (Map<String,Object>) (listC.get(i));
                    BigDecimal valueA =mapA.get("value")==null ?BigDecimal.ZERO : new BigDecimal(mapA.get("value").toString());
                    BigDecimal valueB =mapB.get("value")==null ?BigDecimal.ZERO : new BigDecimal(mapB.get("value").toString());
                    BigDecimal valueC =mapC.get("value")==null ?BigDecimal.ZERO : new BigDecimal(mapC.get("value").toString());
                    values[i][0] = mapA.get("regionName")+"";
                    values[i][1] = valueA.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP).toString();
                    values[i][2] = valueB.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP).toString();
                    values[i][3] = valueC.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP).toString();
                }
                break;
            case 1802 :
                List list1=(List)allMap.get(lastYear.toString());
                List list2=(List)allMap.get(currentYear.toString());
                values =new String [list1.size()][4];
                for(int i=0;i<list2.size();i++){
                    Map<String,Object> map2 = (Map<String,Object>) (list1.get(i));
                    Map<String,Object> map = (Map<String,Object>) (list2.get(i));
                    BigDecimal value =map.get("value")==null ?BigDecimal.ZERO : new BigDecimal(map.get("value").toString());
                    BigDecimal value1 =map2.get("value")==null ?BigDecimal.ZERO : new BigDecimal(map2.get("value").toString());
                    BigDecimal percent =map.get("percent")==null ?BigDecimal.ZERO : new BigDecimal(map.get("percent").toString());
                    values[i][0] = map.get("regionName")+"";
                    values[i][1] = value.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP).toString();
                    values[i][2] = value1.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP).toString();
                    values[i][3] =percent.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
                }
                break;
            case 1803 :
                values =new String [PROP_COLS.length][2];
                if(CollectionUtil.isNotEmpty(list)){
                    Map<String,Object> map = (Map<String,Object>)list.get(0);
                    String [] vals = new String [PROP_COLS.length];
                    for(int i=0;i<PROP_COLS.length;i++){
                        BigDecimal value =map.get(PROP_COLS[i])==null ?BigDecimal.ZERO : new BigDecimal(map.get(PROP_COLS[i]).toString());
                        String va = value.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP).toString();
                        vals[i]= va;
                    }
                    for(int i=0;i<PROP_COLS.length;i++){
                        values[i]= new String []{LEVEL_COLS[i],vals[i]};
                    }
                }
                break;
            case 1804 :
                values =new String [list.size()][5];
                for(int i=0;i<list.size();i++){
                    Map<String,Object> map = (Map<String,Object>) (list.get(i));
                    String value =map.get("value")==null ?"--" : map.get("value").toString();
                    String value1 =map.get("lastValue")==null ?"--": map.get("lastValue").toString();
                    BigDecimal percent =map.get("percent")==null ?BigDecimal.ZERO : new BigDecimal(map.get("percent").toString());
                    values[i][0] = map.get("regionName")+"";
                    values[i][1] = map.get("date")+"";
                    values[i][2] = value;
                    values[i][3] = value1;
                    values[i][4] =percent.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
                }
                break;
            case 1805 :
                values =new String [list.size()][5];
                for(int i=0;i<list.size();i++){
                    Map<String,Object> map = (Map<String,Object>) (list.get(i));
                    String value =map.get("value")==null ?"--": map.get("value").toString();
                    String value1 =map.get("lastValue")==null ?"--": map.get("lastValue").toString();
                    BigDecimal percent =map.get("percent")==null ?BigDecimal.ZERO : new BigDecimal(map.get("percent").toString());
                    values[i][0] = map.get("regionName")+"";
                    values[i][1] = map.get("date")+"";
                    values[i][2] = value;
                    values[i][3] = value1;
                    values[i][4] =percent.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
                }
                break;
            case 1807 :
                values =new String [list.size()][4];
                for(int i=0;i<list.size();i++){
                    Map<String,Object> map = (Map<String,Object>) (list.get(i));
                    String value =map.get("maxValue")==null ?"--": map.get("maxValue").toString();
                    String value1 =map.get("minValue")==null ?"--": map.get("minValue").toString();
                    values[i][0] = map.get("regionName")+"";
                    values[i][1] = map.get("date")+"";
                    values[i][2] = value;
                    values[i][3] = value1;
                }
                break;
            default:
                break;
        }
        return values;

    }

}
