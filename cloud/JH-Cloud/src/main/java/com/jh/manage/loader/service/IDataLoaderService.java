package com.jh.manage.loader.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.manage.download.entity.DataDownload;
import com.jh.manage.loader.entity.DataLoader;
import com.jh.manage.loader.model.ImportParam;
import com.jh.manage.loader.model.LoaderParam;
import com.jh.vo.ResultMessage;

/**
* 数据入库服务：
* 1. 将下载完成任务转化为入库任务。
* 2. 将导入目录下载的文件手动转化为入库任务。
* @version <1> 2018-01-29 Hayden:Created.
*/

public interface IDataLoaderService extends IBaseService<LoaderParam, DataLoader, Integer> {
	/**
	* 将下载任务转化为入库任务
	*     1.下载任务记录转化为入库任务记录
	 *    2.下载任务明细转化为入库任务明细
	* @param  downloadObj:
	* @return ResultMessage :
	* @version <1> 2018-03-01 Hayden:Created.
	*/
	public ResultMessage convertToDataLoadTask(DataDownload downloadObj);

	/**
	* 将导入任务转化为入库任务
	*  对入库文件夹进行标记（加入前缀）
	* @param  dataLoader:
	*      入库文件夹在存储中的名称
	*      创建人
	* @return ResultMessage :
	* @version <1> 2018-01-29 Hayden:Created.
	*/
	public ResultMessage convertToDataLoadTask(DataLoader dataLoader, ImportParam importParam);

	/**
	* 根据入库任务ID获取入库任务明细列表
	* @param  dataLoaderId:
	* @return ResultMessage:
	* @version <1> 2018-01-29 Hayden:Created.
	*/
	public ResultMessage findDataLoaderDetailById(Integer dataLoaderId);

	/**
	* 执行数据入库任务
	* 1.任务状态从失败状态更新为正在入库中
	* 2.将入库明细数据进行更新
	* 		1）重新读取元数据并入库（检查元数据是否入库，若入库，则需要覆盖）
	* 		2）复制缩略图至指定存放位置（若存在该缩略图， 则直接覆盖，若不存在，则直接移入）
	* 		3）复制影像至指定存放位置
	* 3.入库明细全部完成入库后，任务状态更新为入库完成
	*    入库若失败，则需记录告警信息，插入告警信息表中
	* 4.删除临时目录数据
	 * @param dataLoader
	* @version <1> 2018-01-29 Hayden:Created.
	 * @version<2> 2018-03-06 lcw : mofify
	 * 	 修改参数loaderId 为DataLoader对象，任务执行人为当前系统登录人员，任务执行人填入修改人字段中
	*/
	public ResultMessage  startDataLoaderTask(DataLoader dataLoader);


	/**
	 * 入库任务列表查询
	 * @param loaderParam
	 * @return
	 * @version<1> 2018-03-01 lcw : Created.
	 */
    PageInfo<DataLoader> findByPage(LoaderParam loaderParam);


	/**
	 * 从ceph服务器中获取未转化为入库任务的待导入数据列表
	 * 	1.指定目录：从config.properties中获取
	 * 	2.获取没有添加“import_"前缀的文件和文件夹的名称
	 * @return
	 * @version<1> 2018-03-07 lcw : Created.
     */
	PageInfo<ImportParam> findImportDataFromCeph(ImportParam importParam);

	/**
	 * 从ceph服务器中获取未转化为入库任务的待导入数据列表的详情
	 * 	1.指定目录：从config.properties中获取
	 * 	2.获取没有添加“import_"前缀的文件和文件夹的名称详情
	 * @return
	 * @version<1> 2018-03-12 wl : Created.
	 */
	ResultMessage findImportDataDetail(ImportParam importParam, String suffixs);

	/*
	  重新入库  更新数据库状态  并 存入更新人的名字 时间
	 */
	ResultMessage reloadFile(DataLoader dataLoader);
	/**
	 * @description: 根据id查询详细信息
	 * @param loaderId 入库任务主键
	 * @return
	 * @version <1> 2018-03-22 wl： Created.
	 */
	ResultMessage findById(Integer loaderId);

	/**
	 * 判断文件夹（待导入路径下）是否为空
	 * @param path
	 * @return
     */
    ResultMessage checkNull(String path);

    ResultMessage queryLoaderSateSum(LoaderParam loaderParam);

	ResultMessage queryLoaderSateSumTable(LoaderParam loaderParam);

	/**
	 * Description: 获取大屏显示所需的数据(入库和下载数据)
	 * @param
	 * @return
	 * @version <1> 2018/8/16 20:57 zhangshen: Created.
	 */
	ResultMessage getEchartsShowData();

	ResultMessage getEchartsShowData2();
}