package com.jh.manage.download.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.alarm.entity.DataAlarm;
import com.jh.manage.download.entity.DataDownload;
import com.jh.manage.download.model.DownloadParam;
import com.jh.vo.ResultMessage;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
* 数据下载服务：
* 1. 创建数据下载任务
* 2.执行下载任务(执行下载任务后，任务不可进行修改)
* 3.下载任务暂停（停止）、重新开始
* 4.查看数据明细
* @version <1> 2018-01-29 Hayden:Created.
*/

public interface IDataDownloadService extends IBaseService<DownloadParam, DataDownload, Integer> {

	/**
	 * 下载任务分页查询
	 * @param downloadParam
	 * @return
	 * @version <I> 2018-02-28 XZG: Created
     */
	public PageInfo<DownloadParam> findTaskPage(DownloadParam downloadParam);

	/**
	 * 新增下载任务
	 * @param downloadParam  下载任务参数
     * @return
	 * @version <I> 2018-02-28 XZG: Created
     */
	public ResultMessage saveDownload(DownloadParam downloadParam);

	/**
	 * 重新启动下载任务
	 * @param downloadParam
	 * @return
	 * @version <I> 2018-02-28 XZG: Created
     */
	public ResultMessage tryAgainTask(DownloadParam downloadParam);


	/**
	 * 根据任务主键查询
	 * @param downloadId
	 * @return
	 * @version <I> 2018-02-28 XZG: Created
     */
	public ResultMessage findById(Integer downloadId);

	/**
	 * 查询任务详细信息
	 * @param downloadId
	 * @return
	 * @version <I> 2018-03-08 XZG: Created
     */
	public DownloadParam findDownloadById(Integer downloadId);

	/**
	 * 数据下载成功，保存信息（修改下载任务状态、添加下载明细）
	 * @param jsonObject  算法执行，返回信息
	 * @version <I> 2018-03-07 XZG: Created
     */
	public void saveDetail(JSONObject jsonObject, DataDownload dataDownload);

	/**
	 * 任务下载失败 保存信息（修改下载状态为失败、添加告警信息）
	 * @param dataDownload
	 * @version <I> 2018-03-07 XZG: Created
     */
	public void downloadFail(DataDownload dataDownload, DataAlarm dataAlarm);

	/**
	 * 根据参数查询下载任务，创建时间升序
	 * @param downloadParam
	 * @return
	 * @version <I> 2018-03-07 XZG: Created
     */
	public List<DataDownload> findByParam(DownloadParam downloadParam);

	/**
	 * @description: 编辑下载信息
	 * @param downloadParam 下载信息实体
	 * @return
	 * @version<1> 2018-05-31 wl: Created.
	 */
	ResultMessage updateDownload(DownloadParam downloadParam);

	/**
	 * Description: 获取大屏显示所需的数据(下载数据)
	 * @param
	 * @return
	 * @version <1> 2018/8/16 13:57 zhangshen: Created.
	 */
	List<Map<String, Object>> getEchartsDownloadData(String beginTime, String endTime);

	/**
	 * Description: 获取下载历史总数量
	 * @param
	 * @return
	 * @version <1> 2018/8/16 20:41 zhangshen: Created.
	 */
	Integer queryDownloadEchartsTotal();
}