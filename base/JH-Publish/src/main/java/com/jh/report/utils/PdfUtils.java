package com.jh.report.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.jh.report.model.*;
import com.jh.util.DateUtil;
import com.jh.util.ceph.CephUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 报告生成工具类
 * @version <1> 2018-05-14 zhangshen : Created.
 */
@SuppressWarnings("unchecked")
public class PdfUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtils.class);


    /**
     * 获取资源文件路径
     * @return
     */
    private static String getPath(){
        //String str = PdfUtils.class.getClassLoader().getResource("").getPath().substring(1) + PropertyUtil.getPropertiesForConfig("REPORT_TFL_RESOURCE");
        String str = PdfUtils.class.getClassLoader().getResource("").getPath().substring(1) + CephUtils.getCephUrl("REPORT_TFL_RESOURCE");
        return str;
        //return PropertyUtil.getPropertiesForConfig("REPORT_TFL_RESOURCE");
    }
    /**
     * 获取Freemarker配置
     * @param templatePath  模板路径
     * @return
     */
    public static Configuration getFreemarkerConfig(String templatePath){
        Configuration config = new Configuration(new Version("2.3.0"));
        try {
            config.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.setEncoding(Locale.CHINA, "utf-8");
        return config;
    }

    /**
     * 生成word,并返回路径
     * @param templateName 模板名称
     * @param obj 传入模板的参数
     * @param sourcePath 源文件（模板）路径
     * @return
     */
    public static String generateWord(String templateName, Object obj,String sourcePath,String storageFullPathWord){
        FileOutputStream fos = null;
        Writer out = null;
        //String tmpFilePath = "";
        try {
            //System.out.println("templateName====" + templateName);
            //System.out.println("sourcePath====" + sourcePath);
            LOGGER.info("templateName====" + templateName);
            //LOGGER.info("sourcePath====" + sourcePath);
            //Template tpl = getFreemarkerConfig(sourcePath).getTemplate(templateName,"utf-8");
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
            //设置模板目录
            cfg.setClassForTemplateLoading(PdfUtils.class, File.separator + CephUtils.getCephUrl("REPORT_TFL_RESOURCE"));
            //设置默认编码格式
            cfg.setDefaultEncoding("UTF-8");
            Template tpl = cfg.getTemplate(templateName);

            //String fileName = UUID.randomUUID().toString();
            //tmpFilePath = storageFullPathWord;//tmpPath + File.separator + fileName + ".doc";
            fos = new FileOutputStream(new File(storageFullPathWord));
            out = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
            tpl.process(obj, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return storageFullPathWord;
    }

    /**
     * 将图片转换成BASE64字符串
     * @param storagePath
     * @return
     */
    public static String getImageString(String storagePath){
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(storagePath);
            data = new byte[in.available()];
            in.read(data);


            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return data != null ? encoder.encode(data) : "";
    }

    public String generateBarChartImage(ImageParam imageParam){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        List<ImageData> imageDataList = imageParam.getImageDataList();
        if(imageDataList != null && !imageDataList.isEmpty()){
            for(ImageData imageData : imageDataList) {
                if(StringUtils.isNotBlank(imageData.getRegionName())) {
                    dataset.addValue((Number) imageData.getArea(), imageData.getYear(), imageData.getRegionName());
                }else{
                    dataset.addValue((Number) imageData.getArea(), imageData.getYear(), "");
                }
            }
        }

        String title = imageParam.getYear() + "年" + imageParam.getRegionName() + imageParam.getCropName() + imageParam.getDescription() + "对比图（" + imageParam.getUnit() + "）";
        JFreeChart barChart = ChartFactory.createBarChart3D(
                "",
//                imageParam.getRegionName(),
                "",
                imageParam.getDescription(),
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot categoryplot = (CategoryPlot) barChart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        /*------设置X轴坐标上的文字-----------*/
        domainAxis.setTickLabelFont(new java.awt.Font("sans-serif", java.awt.Font.PLAIN, 11));
        /*------设置X轴的标题文字------------*/
        domainAxis.setLabelFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));
        /*------设置Y轴坐标上的文字-----------*/
        numberaxis.setTickLabelFont(new java.awt.Font("sans-serif", java.awt.Font.PLAIN, 12));
        /*------设置Y轴的标题文字------------*/
        numberaxis.setLabelFont(new java.awt.Font("黑体", java.awt.Font.PLAIN, 12));
        /*------这句代码解决了底部汉字乱码的问题-----------*/
        barChart.getLegend().setItemFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));
        /*******这句代码解决了标题汉字乱码的问题********/
        barChart.getTitle().setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));

        //显示每个柱的数值，并修改该数值的字体属性
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer3D();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        //默认的数字显示在柱子中，通过如下两句可调整数字的显示
        //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setItemLabelAnchorOffset(12D);
//        renderer.setItemMargin(0.1);//组内柱子间隔为组宽的10%
        renderer.setMaximumBarWidth(0.1);
        renderer.setMinimumBarLength(0.1);
        plot.setRenderer(renderer);

        int width = 640;
        int height = 400;

        String imageName = UUID.randomUUID().toString() + ".png";
        File barChart3D = new File(imageParam.getStoragePth() + File.separator + imageName);
        try {
            ChartUtilities.saveChartAsPNG( barChart3D, barChart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barChart3D.getPath();
    }

    /**
     * 生成pdf
     * @param templateName  模板名称
     * @param obj   传入模板的参数
     * @param storageFullPathPdf  目标文件的全路径，如d://pdf/test.pdf
     * @param storageFullPathWord  目标文件的全路径，如d://word/test.doc
     */
    public static File generatePdf(String templateName, Object obj,String storageFullPathPdf,String storageFullPathWord){
        String path = generateWord(templateName,obj,getPath(),storageFullPathWord);
        return wordToPdf(storageFullPathPdf, path);
    }

    /**
     * 将word装换成pdf
     * @param destFullPath 生成文件 pdf路径
     * @param path word路径
     * @return
     */
    public static File wordToPdf(String destFullPath, String path){
        /*ActiveXComponent app = null;
        Dispatch doc = null;
        File tofile = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            doc = Dispatch.invoke(docs,"Open",Dispatch.Method,new Object[] {path, new Variant(false),new Variant(true) }, new int[1]).toDispatch();
            tofile = new File(destFullPath);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {destFullPath, new Variant(17) }, new int[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }catch(Throwable t){
            t.printStackTrace();
        } finally {
            Dispatch.call(doc,"Close",false);
            if (app != null)
                app.invoke("Quit", new Variant[] {});
        }
        ComThread.Release();
        return tofile;*/
        return DocUtils.docToPdf(path,destFullPath);
    }

    /**
     * Description: 生成柱状、折线图(无下级分布报告中用到)
     * @param
     * @return
     * @version <1> 2018/7/20 13:54 zhangshen: Created.
     */
    public static String createBarAndLineChart(ImageParam imageParam) {
        //创建主题样式 ,以下代码用于解决中文乱码问题
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
        //应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        //1.声明
        DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();//柱状图数据
        DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();//折线图数据

        //2.在声明中添加数据
        for (ImageData imageData : imageParam.getImageDataList()) {
            barDataSet.addValue(imageData.getArea(), "", imageData.getYear() + "年");
            lineDataSet.addValue(imageData.getArea(), "", imageData.getYear() + "年");
        }

        //3.生成综合图
        BarRenderer barRender = new BarRenderer();
        // 设置柱子为平面图不是立体的
        barRender.setBarPainter(new StandardBarPainter());

        barRender.setShadowVisible(false); //不显示阴影

        //展示柱状图数值
        barRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        //barRender.setBaseItemLabelsVisible(true);
        barRender.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_CENTER));

        //最短的BAR长度，避免数值太小而显示不出
        barRender.setMinimumBarLength(0.5);

        // 设置柱形图上的文字偏离值
        barRender.setItemLabelAnchorOffset(10D);

        //设置柱子的最大宽度
        barRender.setMaximumBarWidth(0.14);
        barRender.setItemMargin(0.000000005);

        LineAndShapeRenderer lineRender = new LineAndShapeRenderer();

        //展示折线图节点值
        lineRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineRender.setBaseItemLabelsVisible(true);
        lineRender.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

        // 设置柱形图上的文字偏离值
        lineRender.setItemLabelAnchorOffset(5D);
        lineRender.setBaseItemLabelFont(new Font("sans-serif", Font.PLAIN, 14));//设置折线值字体

        /*BasicStroke brokenLine = new BasicStroke(2f,//线条粗细
                BasicStroke.CAP_SQUARE,           //端点风格
                BasicStroke.JOIN_MITER,           //折点风格
                8.f,                              //折点处理办法 ,如果要实线把该参数设置为NULL
                new float[]{8.0f},               //虚线数组
                0.0f);*/
        BasicStroke brokenLine = new BasicStroke(1.8f); // 设置实线

        //设置第一条折线的风格
        lineRender.setSeriesStroke(0, brokenLine);

        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, barDataSet);
        plot.setDataset(1, lineDataSet);
        plot.setRenderer(0, barRender);
        plot.setRenderer(1, lineRender);

        plot.setDomainAxis(new CategoryAxis());

        //设置水平方向背景线颜色
        plot.setRangeGridlinePaint(Color.gray);

        //设置是否显示水平方向背景线,默认值为true
        plot.setRangeGridlinesVisible(true);

        //设置垂直方向背景线颜色
        //plot.setDomainGridlinePaint(Color.gray);

        //设置是否显示垂直方向背景线,默认值为true
        plot.setDomainGridlinesVisible(false);

        //设置图表透明图0.0~1.0范围。0.0为完全透明，1.0为完全不透明。
        plot.setForegroundAlpha(0.8F);

        plot.setRangeAxis(new NumberAxis());

        //设置Y轴刻度
        //plot.setRangeAxis(1, new NumberAxis());

        // 设置折线图数据源应用Y轴右侧刻度
        //plot.mapDatasetToRangeAxis(1, 1);

        //折线在柱面前面显示
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        //plot.setOutlinePaint(null);

        for (int i = 0; i < plot.getRangeAxisCount(); i++) {
            CategoryAxis domainAxis = plot.getDomainAxis(i);//对X轴做操作
            domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 14));//设置X轴坐标上的文字


            ValueAxis rAxis = plot.getRangeAxis(i);//对Y轴做操作
            rAxis.setUpperMargin(0.25);//设置最高的一个柱与图片顶端的距离
            rAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15));//Y轴标题字体样式
            rAxis.setLabel(imageParam.getDescription() + "(" + imageParam.getUnit() + ")");//Y轴内容
            rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 14));//设置Y轴坐标上的文字

            barRender.setSeriesPaint(i, Color.decode("#4472C4"));//设置柱子颜色
        }

        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle(imageParam.getRegionName() + imageParam.getYear() + "年" + imageParam.getCropName() + "种植面积变化图");
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 25));//设置标题字体
        textTitle.setPadding(15D, 0, 10D, 0);
        chart.setBackgroundPaint(SystemColor.WHITE);//背景色
        chart.setBorderVisible(true);//有边框
        //chart.setBorderPaint(SystemColor.black);
        chart.removeLegend();//移除图例

        int width = 640;
        int height = 400;

        String imageName = UUID.randomUUID().toString() + ".png";

        File barChart = new File(imageParam.getStoragePth() + File.separator + imageName);
        try {
            ChartUtilities.saveChartAsPNG( barChart, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barChart.getPath();
    }

    /**
     * Description: 生成饼状图
     * @param imageParam
     * @return
     * @version <1> 2018/7/23 15:41 zhangshen: Created.
     */
    public static String createReportPieChart(DistributionCityData distributionCityData, ImageParam imageParam) {
        //创建主题样式 ,以下代码用于解决中文乱码问题
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
        //应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        DefaultPieDataset dataset = new DefaultPieDataset();
        /*for (ImageData imageData : imageParam.getImageDataList()) {
            dataset.setValue(imageData.getRegionName(), imageData.getArea());
        }*/
        for (DistributionBaseData dis : distributionCityData.getDistributionList()) {
            dataset.setValue(dis.getRegionName(), dis.getEndYearArea());
        }

        JFreeChart jfreeChart= ChartFactory.createPieChart("", dataset, false, true, false);

        // 副标题
        //jfreeChart.addSubtitle(new TextTitle("单位:" + imageParam.getUnit()));

        PiePlot pieplot=(PiePlot)jfreeChart.getPlot();
        pieplot.setLabelFont(new Font("宋体",0,12));
        // 设置饼图是圆的（true），还是椭圆的（false）；默认为true
        pieplot.setCircular(true);
        // 没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}, {1}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
        pieplot.setLabelGenerator(standarPieIG);

        pieplot.setShadowXOffset(0D);//无阴影
        pieplot.setShadowYOffset(0D);

        pieplot.setBackgroundPaint(SystemColor.WHITE);//背景色


        int width = 640;
        int height = 400;

        String imageName = UUID.randomUUID().toString() + ".png";

        File barChart = new File( imageParam.getStoragePth() + File.separator + imageName);
        try {
            ChartUtilities.saveChartAsPNG( barChart, jfreeChart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barChart.getPath();
    }

    /**
     * 生成两条折线图
     * @param distributionCityData
     * @param imageParam
     * @return
     * @version <1> 2018/7/24 09:41 zhangshen: Created.
     */
    public static String createTwoLineChart(DistributionCityData distributionCityData, ImageParam imageParam) {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);

        //1.声明
        DefaultCategoryDataset lineDataset1 = new DefaultCategoryDataset();//折线图数据1
        DefaultCategoryDataset lineDataset2 = new DefaultCategoryDataset();//折线图数据2

        // 绘图数据集
        //2.在声明中添加数据
        for (DistributionBaseData dis : distributionCityData.getDistributionList()) {
            lineDataset1.setValue(dis.getEndYearArea(), DateUtil.getYearByDate(distributionCityData.getSatelliteImageDate()), dis.getRegionName());
            if (distributionCityData.getSatelliteImageDateLast() != null) {
                lineDataset2.setValue(dis.getBeginYearArea(), DateUtil.getYearByDate(distributionCityData.getSatelliteImageDateLast()), dis.getRegionName());
            }
        }

        //3.生成综合图
        LineAndShapeRenderer lineRender1 = new LineAndShapeRenderer();
        LineAndShapeRenderer lineRender2 = new LineAndShapeRenderer();

        //展示折线图节点值
        lineRender1.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineRender1.setBaseItemLabelsVisible(true);
        lineRender1.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

        // 设置柱形图上的文字偏离值
        lineRender1.setItemLabelAnchorOffset(5D);
        lineRender1.setBaseItemLabelFont(new Font("sans-serif", Font.PLAIN, 12));//设置折线值字体

        lineRender2.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineRender2.setBaseItemLabelsVisible(true);
        lineRender2.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        lineRender2.setItemLabelAnchorOffset(5D);
        lineRender2.setBaseItemLabelFont(new Font("sans-serif", Font.PLAIN, 12));//设置折线值字体


        lineRender1.setSeriesStroke(0, new BasicStroke(1.8f));//设置第一条折线的风格, 实线
        lineRender2.setSeriesStroke(1, new BasicStroke(0.8f));//设置第一条折线的风格, 实线

        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, lineDataset1);
        plot.setDataset(1, lineDataset2);
        plot.setRenderer(0, lineRender1);
        plot.setRenderer(1, lineRender2);

        plot.setDomainAxis(new CategoryAxis());

        //设置水平方向背景线颜色
        plot.setRangeGridlinePaint(Color.gray);

        //设置是否显示水平方向背景线,默认值为true
        plot.setRangeGridlinesVisible(true);

        //设置垂直方向背景线颜色
        //plot.setDomainGridlinePaint(Color.gray);

        //设置是否显示垂直方向背景线,默认值为true
        plot.setDomainGridlinesVisible(false);

        //设置图表透明图0.0~1.0范围。0.0为完全透明，1.0为完全不透明。
        plot.setForegroundAlpha(0.8F);

        plot.setRangeAxis(new NumberAxis());

        //设置Y轴刻度
        //plot.setRangeAxis(1, new NumberAxis());

        // 设置折线图数据源应用Y轴右侧刻度
        //plot.mapDatasetToRangeAxis(1, 1);

        //折线在柱面前面显示
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        //plot.setOutlinePaint(null);

        for (int i = 0; i < plot.getRangeAxisCount(); i++) {
            CategoryAxis domainAxis = plot.getDomainAxis(i);//对X轴做操作
            domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));//设置X轴坐标上的文字
            //domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

            ValueAxis rAxis = plot.getRangeAxis(i);//对Y轴做操作
            rAxis.setUpperMargin(0.25);//设置最高的一个柱与图片顶端的距离
            rAxis.setLabelFont(new Font("黑体", Font.PLAIN, 15));//Y轴标题字体样式
            rAxis.setLabel(imageParam.getUnit());//Y轴内容
            rAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));//设置Y轴坐标上的文字
        }

        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle(distributionCityData.getRegionName() + DateUtil.getYearByDate(distributionCityData.getSatelliteImageDate()) + "年" + distributionCityData.getCropName() + "种植面积变化图");
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 25));//设置标题字体
        textTitle.setPadding(15D, 0, 10D, 0);
        chart.setBackgroundPaint(SystemColor.WHITE);//背景色

        int width = 840;
        int height = 500;

        String imageName = UUID.randomUUID().toString() + ".png";

        File barChart = new File( imageParam.getStoragePth() + File.separator + imageName);
        try {
            ChartUtilities.saveChartAsPNG( barChart, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barChart.getPath();
    }
}
