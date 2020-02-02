package com.jh.show.agric.service;

import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * 1.
 *
 * @version <1> 2018/8/23 16:11 zhangshen: Created.
 */
public interface IShowDsReportService {

    /**
     * Description: JiaHeShow报告预览
     * @param reportId
     * @param response
     * @return
     * @version <1> 2018/8/23 16:23 zhangshen: Created.
     */
    void previewReportPdf(Integer reportId , HttpServletResponse response);
}
