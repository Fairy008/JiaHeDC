package com.jh.data.utils;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by cxw on 2017/8/28.
 */

/**
 * 导出Excel文档工具类
 * */
public class ExcelUtil {

    private static Logger logger = Logger.getLogger(ExcelUtil.class);
    /**
     * excel导出excel数据记录
     */
    public static void exportExcel(OutputStream out, List<Map<String,Object>> listContent,long type,String titleName,String titleNameAvg)  {
        // 创建工作簿对象
        HSSFWorkbook wb=new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet=wb.createSheet();
        sheet.setDefaultRowHeight((short)(2*256));
        //设置列宽
        sheet.setColumnWidth(0, 22 * 256);
        sheet.setColumnWidth(1, 22 * 256);
        sheet.setColumnWidth(2, 22 * 256);
        sheet.setColumnWidth(3, 22 * 256);
        sheet.setColumnWidth(4, 22 * 256);
        sheet.setColumnWidth(5, 22 * 256);
        sheet.setColumnWidth(6, 22 * 256);
        sheet.setColumnWidth(7, 22 * 256);
        sheet.setColumnWidth(8, 22 * 256);
        sheet.setColumnWidth(9, 22 * 256);
        //设置字体
        HSSFFont font=wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        //有多少行显示多少行
        HSSFRow row=sheet.createRow((int) 0);
        row.setHeightInPoints(25);
        // 生成一个样式
        HSSFCellStyle cellStyle=wb.createCellStyle();
        //左右居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("区域");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(1);
        cell.setCellValue("区域编号");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellValue("区域级别");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(3);
        cell.setCellValue("月旬序列");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(4);
        cell.setCellValue(titleName);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(5);
        cell.setCellValue(titleNameAvg);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(6);
        cell.setCellValue("距均值");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(7);
        cell.setCellValue("距平比例%");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(8);
 /*       cell.setCellValue("地温均值");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(9);
        cell.setCellValue("历年地温均值");
        cell.setCellStyle(cellStyle);
      */
        for (int i=0;listContent != null && i<listContent.size();i++){
            HSSFRow row1=sheet.createRow((int)i + 1);
            row1.setHeightInPoints(25);
            Map<String,Object> vo=listContent.get(i);
            HSSFCell hcell = row1.createCell(0);
            hcell.setCellValue(vo.get("regionName").toString());
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(1);
            hcell.setCellValue(vo.get("regionId").toString());
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(2);
            hcell.setCellValue(vo.get("level").toString());
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(3);
            hcell.setCellValue(vo.get("dateFlag").toString());
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(4);
            if (type==1l) {
                hcell.setCellValue(vo.get("trmm").toString());
            }
            else{
                hcell.setCellValue(vo.get("temp").toString());
            }
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(5);
            if (type==1l) {
                hcell.setCellValue(vo.get("trmmYeasAvg").toString());
            }
            else{
                hcell.setCellValue(vo.get("tempYearsAvg").toString());
            }
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(6);
            hcell.setCellValue(vo.get("distance").toString());
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(7);
            hcell.setCellValue(vo.get("percent").toString());
            hcell.setCellStyle(cellStyle);
         /* hcell =  row1.createCell(8);
            hcell.setCellValue(vo.get("temp").toString());
            hcell.setCellStyle(cellStyle);
            hcell =  row1.createCell(9);
            hcell.setCellValue(vo.get("tempYearsAvg").toString());
            hcell.setCellStyle(cellStyle);*/
        }

        try{
            wb.write(out);
            out.close();
        }

        catch (Exception e1){
            logger.info("=====导出excel异常====");
        }
    }




    /**
     *@Author S12434
     *@Description 导出Excel
     *@Date 10:36 2019/2/1
     *@Param [sheetName sheet名称, title 标题, values 内容, wb HHSWorkbook对象]
     *@Return org.apache.poi.hssf.usermodel.HSSFWorkbook
     */
    public static HSSFWorkbook getHHSWorkbook(String sheetName,String[] title,String[][] values,HSSFWorkbook wb ){
        //第一步，创建一个HSSFWorkbook,对应一个Excel文件
        if(wb==null){
            wb=new HSSFWorkbook();
        }

        //第二步，在workbook中添加一个sheet，对应Excel文件中的sheet
        HSSFSheet sheet=wb.createSheet(sheetName);

        //第三步，在sheet中添加表头第0行，注意老版本poi对EXCEL的行数列数的限制
        HSSFRow row=sheet.createRow(0);

        //第四步，创建单元格，并设置值表头，设置表头居中
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //声明列对象
        HSSFCell cell=null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                row.createCell(j).setCellValue(values[i][j]);
            }
        }

        return wb;
    }



    /**
     *@Description 发送响应流方法
     *@Return void
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}


