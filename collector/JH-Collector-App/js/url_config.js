/**********************开发环境*******************/
// var project_path = "http://192.168.1.63:8001/";
// var project_path_index = "http://192.168.1.225:8080";
/************************************************/


/**********************测试环境*******************/
var project_path = "http://192.168.1.227:8001/";
var project_path_index = "http://192.168.1.225:8080";
/************************************************/

/**********************正式环境*******************/
// var project_path = "http://gateway.datall.cn/";
// var project_path_index = "http://cloud.datall.cn";
/************************************************/


var project_storage_prefix = "/a/data/forum/";
var img_prefix = project_path_index + "/a/";
var share_prefix = project_path_index + "/forum/shareArticleInfo.html?articleId=";

var FORUM_ARTICLE = {
	//查询最热帖子(无需登录)
	nolog_findHotArticleList_url: project_path + "jh-forum/nolog/article/findHotArticleList",
	//查询最新帖子(无需登录)
	nolog_findNewArticleList_url: project_path + "jh-forum/nolog/article/findNewArticleList",
	//分页查询帖子(无需登录)
	nolog_findCombinationPage_url: project_path + "jh-forum/nolog/article/findCombinationPage",
	//查询帖子详情(无需登录)
	nolog_findArticleInfo_url: project_path + "jh-forum/nolog/article/findArticleInfo",
	//分页查询帖子(需登录)
	log_findCombinationPage_url: project_path + "jh-forum/api/article/findCombinationPage",
	//删除帖子(需登录)
	log_deleteArticle_url: project_path + "jh-forum/api/article/deleteArticle",
	//删除帖子(需登录)
	log_updateArticle: project_path + "jh-forum/api/article/updateArticle",
	//新增帖子(需登录)
	log_addArticle_url: project_path + "jh-forum/api/article/addArticle",
	//查询关注或收藏列表
	log_findListByFollower_url: project_path + "jh-forum/api/article/findListByFollower",
	//修改帖子
	log_updateArticle_url: project_path + "jh-forum/api/article/updateArticle",
};

var FORUM_TASK = {
	//获取已完成任务（需登录）
	log_findFinshTaskInfo_url: project_path + 'jh-forum/collection/taskInfo/findApiByPage',
	//获取调研中任务（需登录）  暂时无相关接口后期需更改url
	log_findOnGoingTaskInfo_url: project_path + 'jh-forum/collection/taskInfo/findApiByPage',
	//获取未开始任务（需登录）  暂时无相关接口后期需更改url
	log_findUnStartTaskInfo_url: project_path + 'jh-forum/collection/taskInfo/findApiByPage',
	//获取任务模版（需登录）
	log_findCltTemplatePageInfo_url: project_path + 'jh-forum/cltTemplate/findCltTemplatePageInfo',
	//获取任务列表（需登录）
	log_findCombinationPage_url: project_path + 'jh-forum/cltTaskInfo/findCombinationPage',
	//获取任务列表（无需登录）
	nolog_findCollectionTaskList_url: project_path + "jh-forum/nolog/collection/taskInfo/findByPage",
	//新增任务（需登录）
	log_createTaskInfo_url: project_path + 'jh-forum/cltTaskInfo/createTaskInfo',
	//新版本获取任务列表（无需登录）
	nolog_findCombinationPage_url: project_path + 'jh-forum/nolog/cltTaskInfo/findCombinationPage',
	//修改任务状态（需登录）
	log_updateTaskStatusByTaskId_url: project_path + 'jh-forum/cltTaskInfo/updateTaskStatusByTaskId',
	//删除任务（需登录）
	log_deleteTaskInfoByTaskId_url: project_path + 'jh-forum/cltTaskInfo/deleteTaskInfoByTaskId',
	//根据taskId查询任务详情（需登录）
	log_getCltTaskInfoByTaskId_url: project_path + 'jh-forum/cltTaskInfo/getCltTaskInfoByTaskId',
	//根据taskId查询任务的参与人(不带分页)（需登录）
	log_findCltTaskUserList_url: project_path + 'jh-forum/cltTaskUser/findCltTaskUserList',
	//修改任务（需登录）
	log_updateTaskInfoByTaskId_url: project_path + 'jh-forum/cltTaskInfo/updateTaskInfoByTaskId',
	//我发布的任务列表（需登录）
	log_findShareCltTaskInfoPageInfo_url: project_path + 'jh-forum/cltTaskInfo/findShareCltTaskInfoPageInfo'
}

var FORUM_TP = {
	//新增模版
	log_createCltTemplate_url: project_path + 'jh-forum/cltTemplate/createCltTemplate',
	//删除未启用模版
	log_deleteCltTemplate_url: project_path + 'jh-forum/cltTemplate/deleteCltTemplate',
	//查询模版不带分页
	log_findCltTemplateList_url: project_path + 'jh-forum/cltTemplate/findCltTemplateList',
	//查询模版带分页
	log_findCltTemplatePageInfo_url: project_path + 'jh-forum/cltTemplate/findCltTemplatePageInfo',
}

var FORUM_TASK_ITEM = {
	//新增采集点
	log_createTaskItem_url: project_path + 'jh-forum/cltTaskItem/createTaskItem',
	//根据任务id和用户查看自己的采集点数据列表
	log_findTaskItemListSelf_url: project_path + 'jh-forum/cltTaskItem/findTaskItemListSelf',
	//根据任务id查询任务明细数据列表(带分页)
	log_findTaskItemPageInfo_url: project_path + 'jh-forum/cltTaskItem/findTaskItemPageInfo',
	//根据采集点数据id查询明细数据
	log_findTaskItemByItemId_url: project_path + 'jh-forum/cltTaskItem/findTaskItemByItemId',
	//上传图片音频文件
	log_muiUploadFile_url: project_path + 'jh-forum/cltTaskItem/muiUploadFile',
}

var SYS_LOGIN = {
	//登录 
	nolog_loginForApp_url: project_path + 'jh-sys/nolog/user/loginForApp',
	//根据用户Id组获取用户信息
	nolog_batchFindPersonByAccountIdArr_url: project_path + 'jh-sys/nolog/user/batchFindPersonByAccountIdArr',
	//找回密码，短信发送
	nolog_findValidCodeForPwd_url: project_path + 'jh-sys/nolog/user/findValidCodeForPwd',
	//社区用户注册
	nolog_forumRegister_url: project_path + 'jh-sys/nolog/user/forumRegister',
	//社区用户注册，短信发送
	nolog_findForumValidCodeForRegister_url: project_path + 'jh-sys/nolog/user/findForumValidCodeForRegister',
	//根据关键词，查找用户名或者备注匹配的用户
	nolog_findPersonByKeyword: project_path + 'jh-sys/nolog/user/findPersonByKeyword',
	//找回密码：通过短信的方式发送验证码后修改新密码
	nolog_resetPwd_url: project_path + 'jh-sys/nolog/user/resetPwd',
}

var SYS_DIC = {
	//根据父亲ID获取字典数据（无需登录）
	nolog_queryDictByParentId_url: project_path + 'jh-sys/nolog/dict/queryDictByParentId',
}

/*关注与收藏相关接口*/
var FORUM_FOLLOW = {
	//查询个人中心关注或点赞列表(需登录)
	log_findFollowList_url: project_path + 'jh-forum/api/follow/findByPage',
	//查询个人中心关注或点赞列表信息(需登录)
	log_findFollowListInfo_url: project_path + 'jh-forum/api/follow/findByPageInfo',
	//取消个人中心关注或点赞信息(需登录)
	log_removeFollow_url: project_path + 'jh-forum/api/follow/removeFollow',
	//新增关注或点赞
	log_addFollow_url: project_path + "jh-forum/api/follow/addFollow",
	//查询我的关注列表
	log_findMyFollowList_url: project_path + "jh-forum/api/follow/findMyFollowList",
}

/*专家相关接口*/
var FORUM_EXPERT = {
	//查询专家详情(无需登录)
	nolog_findExpertInfo_url: project_path + "jh-forum/nolog/expert/findExpertInfo",
	//分页查询专家列表(无需登录)
	nolog_findExpertList_url: project_path + "jh-forum/nolog/expert/findExpertList",
	//根据专家accountId查询专家详情(无需登录)
	nolog_findExpertByAccountId_url: project_path + "jh-forum/nolog/expert/findExpertByAccountId",
}

/*用户信息相关接口*/
var SYS_USER = {
	//无需登录查看用户信息
	nolog_getByAccountId_url: project_path + "jh-sys/nolog/user/getByAccountId",
	//无需登录批量查询用户信息
	nolog_batchFindPersonByAccountIdArr_url: project_path + "jh-sys/nolog/user/batchFindPersonByAccountIdArr",
	//登录修改用户密码
	log_updateAccountPwd_url: project_path + "jh-sys/permAccount/updateAccountPwd",
	//修改头像
	log_editPersonPhotoApp_url: project_path + "jh-sys/person/editPersonPhotoApp",
	//根据phone查询用户信息
	nolog_findUserInfoByPhone_url: project_path + "jh-sys/user/findUserInfoByPhone",
	//根据accountid数组查询用户信息
	findPermPersonListByAccIds_url: project_path + "jh-sys/person/findPermPersonListByAccIds",
	//app用户修改
	appEditUser_url: project_path + "jh-sys/person/appEditUser",
}

/*帖子评论相关*/
var FORUM_COMMENT = {
	//无需登录查看帖子的一级评论
	nolog_queryCommentsByPage_url: project_path + 'jh-forum//nolog/comment/queryCommentsByPage',
	//无需登录，根据帖子的一级评论id，查询该评论下的所有二级评论
	nolog_findCommentsByFirstCommentId_url: project_path + 'jh-forum//nolog/comment/findCommentsByFirstCommentId',
	//新增一级评论,调用移动端接口
	addComment_url: project_path + 'jh-forum/api/comment/addComment',
	//新增二级评论,调用移动端接口
	addCommentInFirstCommentId: project_path + 'jh-forum/api/comment/addCommentInFirstCommentId',
	//根据评论id删除评论
	deleteComment_url: project_path + 'jh-forum/api/comment/deleteComment',
}

/*农险定标相关接口*/
var AgriculturalInsurance = {
	//获取任务列表及子任务列表
	log_askTaskList_url: project_path + "jh-datamanage/taskInfoController/taskList",
	log_askTaskList_url_current: "http://192.168.1.145:8006/taskInfoController/taskList",
	//获取子任务向下保单列表//192.168.1.145:8006/policyInfo/policyInfoList?subtaskId=1
	log_askInsuraceBySubTaskId_url: project_path + "jh-datamanage/policyInfo/policyInfoList",
	log_askInsuraceBySubTaskId_url_current: "http://192.168.1.145:8006/policyInfo/policyInfoList",
	//设定定标人// localhost:8006/subtask/relsteCalibrator?subtaskId=5&phone=18717145660
	log_askSetCalibrator_url: project_path + "jh-datamanage/subtask/relsteCalibrator",
	log_askSetCalibrator_url_current:  "http://192.168.1.145:8006/subtask/relsteCalibrator",
	//提交上传子任务数据// localhost:8006/calibrationInfo/submitSubTask
	log_submitSubtask_url: project_path + "jh-datamanage/calibrationInfo/submitSubTask",
	log_submitSubtask_url_current:  "http://192.168.1.145:8006/calibrationInfo/submitSubTask",
	
}
