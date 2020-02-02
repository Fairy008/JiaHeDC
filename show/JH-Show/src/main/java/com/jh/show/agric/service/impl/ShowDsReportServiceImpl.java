package com.jh.show.agric.service.impl;

import com.jh.biz.feign.IDsReportService;
import com.jh.show.agric.service.IShowDsReportService;
import com.jh.util.DownloadUtil;
import com.jh.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018/8/23 16:11 zhangshen: Created.
 */
@Service
public class ShowDsReportServiceImpl implements IShowDsReportService{

    @Autowired
    private IDsReportService dsReportService;

    /**
     * Description: JiaHeShow报告预览
     * @param reportId
     * @param response
     * @return
     * @version <1> 2018/8/23 16:23 zhangshen: Created.
     */
    @Override
    public void previewReportPdf(Integer reportId, HttpServletResponse response) {
        ResultMessage result = dsReportService.findReportShowById(reportId);
        Map<String, Object> map = (Map<String, Object>) result.getData();
        if (map != null) {
            String filePath = map.get("file_path").toString();
            DownloadUtil.getInstance().previewReportPdf(filePath, response);
        }
    }

}
