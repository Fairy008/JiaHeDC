package com.jh.forum.bbs.Enum;

/**
* @Description:    精度范围枚举
 * @version<1> 2019-07-03 admin:Created.
*/
public enum AccuracyRangeEnum {

        Accuracy_range_1(1, "accuracy_one","1m"),
        Accuracy_range_2(2, "accuracy_two","1m-10m"),
        Accuracy_range_3(3, "accuracy_three","10m-100m"),
        Accuracy_range_4(4, "accuracy_four","100m-1000m"),
        Accuracy_range_5(5, "accuracy_five","1000m以上");

        private Integer id;
        private String code;
        private String name;

        AccuracyRangeEnum(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
        }

        public Integer getId() {
                return id;
                }

        public void setId(Integer id) {
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
