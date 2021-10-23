package com.doublecat.entity.enums;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 16:32
 * @Modified By:
 */
public enum ItemNameEnum {
    DAY_WAR("DayWar", "大战"),
    DAY_BATTLE("DayBattle", "战场"),
    DAY_PUBLIC("dayPublic", "公共日常"),
    DayDraw("DayDraw", "美人图"),
    dayCamp("dayCamp", "矿车"),
    weekPublic("weekPublic", "公共周常"),
    WeekFive("WeekFive", "周常"),
    WeekTeam("WeekTeam", "团队周常"),
    ;
    private String itemName;
    private String itemValue;

    ItemNameEnum(String itemName, String itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
