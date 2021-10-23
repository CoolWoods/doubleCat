package com.doublecat.service;

import com.alibaba.fastjson.JSON;
import com.doublecat.entity.mirai.MessageType;
import com.doublecat.entity.mirai.SendMessageReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @Author Zongmin
 * @Date Create in 2021/8/1 10:12
 * @Modified By:
 */
@Slf4j
@Service
public class MessageService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${proxy-server.url}")
    private String proxyUrl;

    private void sendMsg(String msg, String type, long userId, long groupId) {
        SendMessageReq sendMessageReq = new SendMessageReq();
        sendMessageReq.setMessage(msg);
        sendMessageReq.setUser_id(userId);
        sendMessageReq.setGroup_id(groupId);
        String reqJson = JSON.toJSONString(sendMessageReq);
        log.info("发送消息请求参数：" + reqJson);
        String resJson = restTemplate.postForObject(proxyUrl + "/send_" + type + "_msg", sendMessageReq, String.class);
        log.info("发送消息响应内容:" + resJson);
    }

    public void sendPrivateMsg(String msg, long userId, long groupId) {
        sendMsg(msg, MessageType.PRIVATE, userId, groupId);
    }

    public void sendGroupMsg(String msg, long groupId) {
        sendMsg(msg, MessageType.GROUP, -1, groupId);
    }

}
