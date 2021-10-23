package com.doublecat.entity.enums;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 15:44
 * @Modified By:
 */
public enum MenuEnum {
    DAILY("日常", "DAILY", "server");
    private String queryName;
    private String queryValue;
    private String queryCode;

    MenuEnum(String queryName, String queryValue, String queryCode) {
        this.queryName = queryName;
        this.queryValue = queryValue;
        this.queryCode = queryCode;
    }

    /**
     * @param queryName
     * @return
     */
    public static MenuEnum getApiInstanceByValue(String queryName) {
        MenuEnum[] types = MenuEnum.values();
        for (int i = 0; i < types.length; i++) {
            String name = types[i].getQueryName();
            if (name.equals(queryName)) {
                return types[i];
            }
        }
        throw new RuntimeException("没有匹配到相应菜单！");
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }
}
