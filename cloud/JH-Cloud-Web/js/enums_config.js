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