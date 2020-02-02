package com.jh.report.enums;

/**
 * 定义非跨年生长的作物
 */
public enum YearCropEnum {

    Soybean(501,"大豆"),
    Corn(502,"玉米"),
    SpringCorn(503,"春玉米"),
    SummerCorn(504,"夏玉米"),
    WinterWheat(506,"冬小麦"),
    Rice(507,"水稻"),
    EarlyRice(508,"早稻"),
    MiddleRice(509,"中稻"),
    LateRice(510,"晚稻");

    private Integer id;
    private String name;

    YearCropEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
