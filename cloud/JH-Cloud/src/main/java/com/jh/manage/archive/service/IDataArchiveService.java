package com.jh.manage.archive.service;


import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.archive.entity.DataArchive;
import com.jh.manage.archive.model.ArchiveParam;
import com.jh.vo.ResultMessage;

/**
* 数据归档服务：
*	1.查询数据归档信息列表
*	2.创建数据归档任务
*	3.执行数据归档任务
*	4.归档明细查询
* @version <1> 2018-01-29 Hayden:Created.
*/
public interface IDataArchiveService extends IBaseService<ArchiveParam, DataArchive, Integer> {

	/**
	 * 归档任务分页查询
	 * @param archiveParam
	 * @return
	 * @version<1> 2018-03-21 wl: Created.
	 */
	PageInfo<DataArchive> findByPage(ArchiveParam archiveParam);

	/**
	 * @description: 根据id查询详细信息
	 * @param archiveId 数据归档主键
	 * @return
	 * @version<1> 2018-03-21 wl: Created.
	 */
	ResultMessage findById(Integer archiveId);

	/**
	 * @description: 创建文件夹 存放数据归档文件连接
	 * @param archiveParam 数据归档实体
	 * @return
	 * @version<1> 2018-03-23 wl: Created.
	 */
	ResultMessage saveArchive(ArchiveParam archiveParam);

	/**
	 * @description: 编辑数据归档信息
	 * @param archiveParam 数据归档实体
	 * @return
	 * @version<1> 2018-03-21 wl: Created.
	 */
	ResultMessage updateArchive(ArchiveParam archiveParam);

	/**
	 * 创建数据归档任务,
	 * 1.检索数据存储文件夹，
	 * 2.创建数据归档批处理命令
	 * 3.保存数据归档明细
	 *
	 * @param  dataArchiveObj:
	 * @return ResultMessage :
	 * @version <1> 2018-01-29 Hayden:Created.
	 */
	public ResultMessage saveDataArchiveTask(DataArchive dataArchiveObj);

 	/**
 	* 执行数据归档任务 （执行归档过程中不可修改归档任务信息）
 	*	1.指定归档路径
 	*	2.调用批处理命令执行数据归档
 	*	3.若归档路径中存在文件，则直接进行覆盖（防止数据丢失）
 	*	4.归档完成后，更新归档任务状态，更新归档明细
	* @param  dataArchiveId:
	* @return ResultMessage :
	* @version <1> 2018-01-29 Hayden:Created.
 	*/
 	public ResultMessage startDataArchiveTask(Integer dataArchiveId);

 	/**
 	* 归档明细查询
	* @param  dataArchiveId:
	* @return ResultMessage :
	* @version <1> 2018-01-29 Hayden:Created.
 	*/
 	public ResultMessage findDataArchiveDetailById(Integer dataArchiveId);

	/**
	 * 数据归档详情
	 * @return
	 * @version<1> 2018-03-26 wl : Created.
	 */
	ResultMessage findDetail(Integer archiveId);
	/**
	 * 数据归档执行
	 * @return
	 * @version<1> 2018-03-27 wl : Created.
	 */
	ResultMessage exec(Integer archiveId);

	/**
	 *统计不同卫星数据的归档数量
	 * @param  archiveParam 订单对象
	 * @version <1> 2018-04-18 wl： Created.
	 */
	public ResultMessage queryArchiveSateNum(ArchiveParam archiveParam);

	/**
	 *统计不同卫星数据的归档数量(当天和总计)
	 * @param  archiveParam 订单对象
	 * @version <1> 2018-06-12 wl： Created.
	 */
	public ResultMessage queryArchiveSateSum(ArchiveParam archiveParam);

    ResultMessage queryArchiveSateSumTable(ArchiveParam archiveParam);
}