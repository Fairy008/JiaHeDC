package com.jh.produce.process.service;

import com.github.pagehelper.PageInfo;
import com.jh.base.service.IBaseService;
import com.jh.produce.process.entity.HandleData;
import com.jh.produce.process.model.DataStepParam;
import com.jh.produce.process.model.HandleDataParam;
import com.jh.produce.process.model.HandleRelateDataParam;
import com.jh.vo.ResultMessage;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* 数据处理服务：
*  1.创建数据处理任务：通过数据检索界面进行创建
*  2.查询数据处理任务列表
*  3.查看数据处理任务明细
*  4.执行数据处理任务
* @version <1> 2018-01-29 Hayden:Created.
*/
public interface IHandleDataService extends IBaseService<HandleDataParam, HandleData, Integer> {
 

	/**
	 * 保存任务信息
	 * 创建原始文件link 连接
	 * 保存关联关系
	 * @param handleData
	 * @return
	 * @version <1> 2018-03-12 cxj： Created.
	 */
	ResultMessage saveTask(HandleData handleData);

	/**
	 * 任务分页查询
	 * @param handleDataParam
	 * @return
	 * @version <1> 2018-03-12 cxj： Created.
	 */
    PageInfo<HandleData> taskPage(HandleDataParam handleDataParam);

	/**
	 * 查询执行任务时所需的参数
	 * @param handleDataParam
	 * @return
	 * @version <I> 2018-03-13 cxj: Created
	 */
    ResultMessage findTaskParams(HandleDataParam handleDataParam);

	/**
	 * 查询执行任务时所需的参数
	 * @param handleDataParam
	 * @return
	 * @version <I> 2018-03-13 cxj: Created
	 */
	ResultMessage findHandleFileInfo(HandleDataParam handleDataParam);

	/**
	 * 查询要处理的数据及参数信息
	 * @param handleDataParam
	 * @param resultMap
	 * @return
	 * @version <1> 2019-10-08 cxw： Created.
	 */
	 ResultMessage findHandleDataInfo(HandleDataParam handleDataParam);

	/**
	 * 添加任务步骤
	 * @param dataStepParam
	 * @param permAccount
	 * @return
	 * @version <I> 2018-03-15 cxj: Created
	 */
    ResultMessage addHandleData(DataStepParam dataStepParam);

	/**
	 * 更新任务和步骤状态
	 * @param handleDataParam
	 * @return
	 * @version <I> 2018-03-21 cxj: Created
	 */
	ResultMessage updateStatus(HandleDataParam handleDataParam);

	/**
	 * 创建数据生产任务
	 * @param handleData
	 * @return
	 * @version <1> 2019-02-14 zhangshen： Created.
	 */
	ResultMessage createHandleTask(HandleData handleData);

	/**
	 * 数据集入库
	 * @param handleData
	 * @return
	 * @version <1> 2019-02-18 zhangshen： Created.
	 */
	ResultMessage findTaskByHandleId(HandleData handleData);

	/**
	 * 删除任务
	 * @param handleId
	 * @return
	 * @version <1> 2019-04-02 lijie： Created.
	 */
	ResultMessage deleteHandle(Integer handleId);


	/**
	 * 更新任务数据执行顺序
	 * @param dataIdList
	 * @return
	 * @version <1> 2019-10-08 cxw： Created.
	 */
	ResultMessage updateHandleDataIndex(List<HandleRelateDataParam> dataIdList);
}