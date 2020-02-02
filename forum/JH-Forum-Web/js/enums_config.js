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
    WAIT_SUBMIT : 12001,  //待提交
    WAIT_AUDIT : 12002,   //待审核
    REJECTED : 12003,   //已驳回
    PUBLISHED : 12004,   //已发布
    RECALLED : 12005,   //已撤回
}


/**
 * 论坛-任务状态
 */
var forum_taskStatus = {
    WAIT_SUBMIT : 22001,  //待分享
    WAIT_AUDIT : 22002,   //待审核
    REJECTED : 22003,   //已驳回
    PUBLISHED : 22004,   //已分享
    RECALLED : 22005,   //已撤回
}


/**
 * 论坛-进行状态
 */
var forum_goStatus = {
    NO_START : 21001,  //未开始
    DOING : 21002,   //进行中
    FINISHED : 21003,   //已完成
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


/**
 * 写报告页面显示调研数据或遥感数据
 * @type {{yg_data: number, dy_data: number}}
 */
var forum_map_data = {
    yg_data:1,  //遥感数据
    dy_data:2  //调研数据
}


/**
 * 定制数据订单状态
 */
var forum_dataOrderStatus = {
    WAIT_AUDIT : 1,   //待审核
    REJECTED : 3,   //已驳回
    HANDLER : 2,   //已处理
}

/**
 * 广告位状态
 */
var forum_advertStatus = {
    allStatus : 19001,          //全部
    draftStatus : 19002,        //草稿
    publishedStatus : 19003,    //已发布
    offShelf : 19004,           //已下架
}