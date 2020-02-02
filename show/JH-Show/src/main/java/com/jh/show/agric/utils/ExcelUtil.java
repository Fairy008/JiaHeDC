package com.jh.show.agric.utils;

import com.jh.util.CollectionUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Excel导出工具类
 * @version <1> 2018/11/27 lijie: Created.
 */
public class ExcelUtil {

    private static Logger log= org.slf4j.LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.length-1));//第一行单元格合并
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //第一行：创建标题
        HSSFCell cell=row.createCell(0);
        row.setHeight((short)(15.625*40));
        row.setHeightInPoints((float)40);
        cell.setCellValue(sheetName);
        HSSFFont font = wb.createFont();
        font.setFontName("幼圆");
        font.setFontHeightInPoints((short)14);
        font.setColor(HSSFColor.WHITE.index);

        //将字体格式设置到HSSFCellStyle上

        style.setFont(font);
        cell.setCellStyle(style);
        //第二行：创建表头
        row = sheet.createRow(1);//第2行标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 2);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
            sheet.setColumnWidth(i, (int)35.7*200);//设置行间距200px
        }
        log.info("开始导出Excel:title:{},sheetName:{}",title,sheetName);
        return wb;
    }

    /**
     * 导出Excel
     *
     * @param list  sheet名称
     * @param clazz 标题
     * @return
     */
    public static HSSFWorkbook export(List list, Class clazz, String []colsName, String [] param, String sheetName, HSSFWorkbook wb) throws Exception {
        log.info("开始导出Excel:cols:{},sheetName:{}>>>>>>>",colsName,sheetName);
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        String[][] content = new String[list.size()][param.length];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[colsName.length];
            Field[] fields = getAllFields(clazz);
            for (Field field : fields) {
                for (int k = 0; k < param.length; k++) {
                    field.setAccessible(true);
                    if (field.getName().equals(param[k])) {
                        //content[i][k] = field.get(list.get(i))+"";
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                        Method rM = pd.getReadMethod();//获得读方法
                        content[i][k] = rM.invoke(list.get(i))+"";
                    }
                }

            }
        }
        //创建HSSFWorkbook
        wb= ExcelUtil.getHSSFWorkbook(sheetName, colsName, content, wb);
        log.info("导出Excel成功");
        return wb;
    }

    /**
     * 获取所有属性值，包括私有属性和继承属性
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

}
