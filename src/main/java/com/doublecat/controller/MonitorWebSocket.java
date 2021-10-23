package com.doublecat.controller;

import com.doublecat.service.BaseDoubleCatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 9:17
 * @Modified By:
 * ServerEndpoint是每调用一次，创建一个对象，Spring是单例模式。
 */
@Slf4j
@Controller
@ServerEndpoint(value = "/doubleCat")
public class MonitorWebSocket {

    private static BaseDoubleCatService baseDoubleCatService;

    @Resource
    public void setMiraiService(BaseDoubleCatService baseDoubleCatService) {
        MonitorWebSocket.baseDoubleCatService = baseDoubleCatService;
    }
    @OnOpen
    public void onOpen(){
        log.info("已连接go-cqhttp");
    }
    @OnClose
    public void onClose(){
        log.info("已断开go-cqhttp");
    }
    @OnError
    public void onError(Throwable error){
        log.info("go-cqhttp意外掉线" + error);
    }

    @OnMessage
    public void onMessage(String message){
        try {
            baseDoubleCatService.onMessage(message);
        } catch (Exception ignore) {
            log.error("处理消息时发生了异常！", ignore);
        }
    }
}
