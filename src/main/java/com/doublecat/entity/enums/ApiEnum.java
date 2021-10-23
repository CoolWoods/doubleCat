package com.doublecat.entity.enums;

import com.doublecat.service.IDoubleCatService;
import com.doublecat.service.impl.DefaultQueryServiceImpl;
import com.doublecat.service.impl.MacroServiceImpl;
import com.doublecat.service.impl.QueryServiceImpl;
import com.doublecat.service.impl.TeamServiceImpl;

/**
 * @author xxxx
 */

public enum ApiEnum {
    /**
     * IMAJAD Api服务
     */
    TEAM("CREATETEAM", TeamServiceImpl.class),
    // SIGNUP("SIGNUP", SignUpServiceImpl.class),
    QUERY("QUERY", QueryServiceImpl.class),
    MACRO("MACRO", MacroServiceImpl.class),
    DEFAULT("DEFAULT", DefaultQueryServiceImpl.class)
    ;
    private String menuDic;
    private Class aClass;

    public String getMenuDic() {
        return menuDic;
    }

    public void setMenuDic(String menuDic) {
        this.menuDic = menuDic;
    }

    public Class getAClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    ApiEnum(String menuDic, Class<? extends IDoubleCatService> aClass) {
        this.menuDic = menuDic;
        this.aClass = aClass;
    }

    /**
     * 根据枚举值获取对应Class
     * 匹配不到时返回默认的查询实现类
     * @param menuDic
     * @return
     */
    public static ApiEnum getApiInstanceByValue(String menuDic) {
        ApiEnum[] types = ApiEnum.values();
        for (int i = 0; i < types.length; i++) {
            String apiCode = types[i].getMenuDic();
            if (apiCode.equals(menuDic)) {
                return types[i];
            }
        }
        return DEFAULT;
    }
}
