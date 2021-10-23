package com.doublecat.service;

import com.alibaba.fastjson.JSON;
import com.doublecat.entity.enums.ApiEnum;
import com.doublecat.entity.mapper.DcMenu;
import com.doublecat.entity.mirai.Message;
import com.doublecat.mapper.DcGroupMapper;
import com.doublecat.mapper.DcMenuMapper;
import com.doublecat.service.impl.DcGroupService;
import com.doublecat.service.impl.MenuService;
import com.doublecat.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 9:48
 * @Modified By:
 */
@Slf4j
@Service
public class BaseDoubleCatService {
    @Autowired
    private DcGroupMapper dcGroupMapper;
    @Autowired
    private DcMenuMapper dcMenuMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DcGroupService dcGroupService;
    @Autowired
    private MenuService menuService;

    /**
     * 处理接收到的消息
     *
     * @param message
     */
    public void onMessage(String message) throws ParseException {
        Message miraiMessage = JSON.parseObject(message, Message.class);
        // 复读机
        if (Objects.nonNull(miraiMessage.getMessage_type())) {
            log.info("go-cqhttp发来消息：{}", message);
            // QQ群号
            Long groupId = miraiMessage.getGroup_id();
            // 接收消息的QQ号
            Long selfId = miraiMessage.getSelf_id();
            if (dcGroupService.isOpenGroups(groupId, selfId)) {
                String userMessage = miraiMessage.getMessage().trim();
                if (!StringUtils.isEmpty(userMessage)) {
                    // 复读机
                    // this.repeatChat(groupId, miraiMessage.getMessage());
                    IDoubleCatService doubleCatService = menuService.getTargetService(userMessage, groupId);
                    doubleCatService.onMessage(miraiMessage);
                    return;
                } else {
                    log.info("消息体为空！");
                }

            } else {
                log.info("未添加的群组：" + groupId);
            }
            return;
        }
        log.info("WebSocket heartbeat request + " + new Date().toString());
    }

    public void repeatChat(Long groupId, String message) {
        Random random = new Random(System.currentTimeMillis());
        int nextInt = random.nextInt(10);
        // 打印日志
        log.info("go-cqhttp发来消息：{}，nextInt: {}", message, nextInt);
        if (nextInt >= 4) {
            messageService.sendGroupMsg(message, groupId);
        }
    }
}
