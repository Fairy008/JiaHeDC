/**枚举类常量配置**/

/**
 *
 * @type {{}}
 */
var order_orderType = {
    SJ : 1301,  //数据订单
    XQ : 1302   //需求订单
}

/**
 * 改造系统角色功能，将各前端工程的角色分开，减少非当前系统的菜单显示
 *
 *
 * 系统角色组
 * 定义角色所属前端工程
 * system_role_web:私有云后台管理系统
 * system_role_show:农情遥感展示平台
 * system_role_forum:珈和遥感社区
 * @type {{system_role_web: number, system_role_show: number, system_role_forum: number}}
 * @version<1> 2019-03-26 lcw : Created
 */
var system_role_group = {
    system_role_group: 16000,
    system_role_web: 16001,
    system_role_show: 16002,
    system_role_forum: 16003
}


/**
 * 论坛-帖子状态
 */
var forum_articleStatus = {
    WAIT_SUBMIT : 12001,  //待提交 未分享
    WAIT_AUDIT : 12002,   //待审核
    REJECTED : 12003,   //已驳回
    PUBLISHED : 12004,   //已发布  已分享
    RECALLED : 12005,   //已撤回
}
/*
* 任务-发布状态
*/

var forum_publishStatus={
	WAIT_SUBMIT : 22001,  //未分享
    WAIT_AUDIT : 22002,   //待审核
    REJECTED : 22003,   //已驳回
    PUBLISHED : 22004,   //已分享
    RECALLED : 22005,   //已撤回
}


/**
 * 点赞与收藏
 * @type {{}}
 */
var forum_followType = {
    follow_follow:13001,  //收藏
    follow_like:13002,     //点赞
    follow_focus:13003     //关注
}


/**
 * 论坛-帖子类型
 */
var forum_articleType = {
    follow_paper: 11001,    //报告
    follow_survey: 11002,   //调研
    follow_faq: 11003,      //问答
    follow_share: 11004,    //数据分享
    follow_other: 11005     //其他
}

/**
 * 用户类型类型
 */
var forum_userType = {
    expert_user: 1,    //专家
    ordinary_user: 0,   //用户
}

/*
 * 任务状态
 */
var clt_task_status = {
	clt_not_started: 21001,//未开始
	clt_under_way: 21002,//进行中
	clt_complete: 21003//已完成
}

var clt_task_type = {
	distribution: 1801,//分布
	growth: 1803,//长势
	yield: 1802//估产
}

/**
 * 最热和最新帖子标记
 */
var articleHotNew = {
	articleHot : '1',//热门帖子
	articleNew : '1'//最新帖子
}