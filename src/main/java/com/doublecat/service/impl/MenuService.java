package com.doublecat.service.impl;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 9:35
 * @Modified By:
 */

import com.doublecat.common.Constant;
import com.doublecat.entity.enums.ApiEnum;
import com.doublecat.entity.mapper.DcMenu;
import com.doublecat.mapper.DcMenuMapper;
import com.doublecat.service.IDoubleCatService;
import com.doublecat.service.MessageService;
import com.doublecat.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuService {
    @Autowired
    private DcMenuMapper dcMenuMapper;
    @Autowired
    private MessageService messageService;


    public IDoubleCatService getTargetService(String message, Long groupId){
        String menuDic = handleMessageMenu(message, groupId);
        ApiEnum apiEnum = ApiEnum.getApiInstanceByValue(menuDic);
        IDoubleCatService doubleCatService = (IDoubleCatService) SpringUtil.getBean(apiEnum.getAClass());
        return doubleCatService;
    }

    /**
     * 处理消息中的菜单请求
     *
     * @param message 请求的消息
     * @return 菜单代码
     */
    private String handleMessageMenu(String message, Long groupId) {
        boolean isQuery = StringUtils.startsWithIgnoreCase(message, "查询");
        DcMenu dcMenu = null;
        String[] menuNames = message.split(" ");
        Map<String, String> menuMap = getMenuMap();
        if (isQuery) {
            String menuName = menuNames[1];
            dcMenu = getDcMenuByName(menuName);
//            if (Objects.isNull(dcMenu)) {
//                messageService.sendGroupMsg(Constant.QUERY_NOT_EXIT, groupId);
//                return "";
//            }
            return menuMap.get(menuName);
        } else {
            return menuMap.get(menuNames[0]);
        }
    }

    private DcMenu getDcMenuByName(String menuName){
        Example menuExample = new Example(DcMenu.class);
        menuExample.createCriteria().andLike(DcMenu.Fields.menu, menuName + "%");
        menuExample.orderBy(DcMenu.Fields.id);
        return dcMenuMapper.selectOneByExample(menuExample);
    }

    private Map<String, String> getMenuMap(){
        List<DcMenu> dcMenus = dcMenuMapper.selectAll();
        return dcMenus.stream().collect(Collectors.toMap(DcMenu::getMenu, DcMenu::getMenuDic));
    }
}
