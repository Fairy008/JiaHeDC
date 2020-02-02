package com.jh.forum.bbs.Enum;

/**
* @Description:    精度范围枚举
 * @version<1> 2019-07-03 admin:Created.
*/
public enum OrderDataStatusEnum {

        Order_status_wait("1", "status_wait","待处理"),
        Order_status_ok("2", "status_ok","已处理"),
        Order_status_reject("3", "status_reject","驳回");

        private String id;
        private String code;
        private String name;

        OrderDataStatusEnum(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
        }

        public String getId() {
                return id;
                }

        public void setId(String id) {
                this.id = id;
                }

        public String getCode() {
                return code;
                }

        public void setCode(String code) {
                this.code = code;
                }

        public String getName() {
                return name;
                }

        public void setName(String name) {
                this.name = name;
                }
}
