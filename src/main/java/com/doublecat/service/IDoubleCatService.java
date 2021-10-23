package com.doublecat.service;

import com.doublecat.entity.mirai.Message;

import java.text.ParseException;

/**
 * @Author Zongmin
 * @Date Create in 2021/8/1 10:04
 * @Modified By:
 */
public interface IDoubleCatService {
    /**
     * 收到消息
     * @param message
     */
    void onMessage(Message message) throws ParseException;

    /**
     * 收到消息
     * @param message
     * @param obejct 备用参数
     */
    void onMessage(Message message, Object obejct);
}
