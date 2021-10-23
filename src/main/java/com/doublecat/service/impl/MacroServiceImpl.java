package com.doublecat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doublecat.entity.mirai.Message;
import com.doublecat.service.IDoubleCatService;
import com.doublecat.service.MessageService;
import com.doublecat.utils.HttpEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询宏
 *
 * @Author Zongmin
 * @Date Create in 2021/10/23 10:11
 * @Modified By:
 */
@Slf4j
@Service
public class MacroServiceImpl implements IDoubleCatService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DcDicService dcDicService;
    @Autowired
    private MessageService messageService;

    @Value("${jx3api.url}")
    private String baseUrl;

    /**
     * 收到消息
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        Map<String, String> queryMap = getQueryParam(message.getMessage());
        String macroResJson = sendMacroQuery(queryMap);
        String macroResponse = handleMacroRes(macroResJson);
        messageService.sendGroupMsg(macroResponse, message.getGroup_id());
    }

    /**
     * 收到消息
     *
     * @param message
     * @param obejct  备用参数
     */
    @Override
    public void onMessage(Message message, Object obejct) {

    }

    private Map<String, String> getQueryParam(String message){
        Map<String, String> queryMap = new HashMap<>();
        String macroName = message.split(" ")[1];
        String nameByAlias = dcDicService.getNameByAlias(macroName);
        queryMap.put("name", nameByAlias);
        return queryMap;
    }

    private String sendMacroQuery(Map<String, String> queryMap) {
        String jsonParam = JSONObject.toJSONString(queryMap);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity(null, headers);
        // 临时使用get方式请求
        String macroUrl = baseUrl + "macro?name=" + queryMap.get("name");
        ResponseEntity<String> exchange = restTemplate.exchange(macroUrl, HttpMethod.GET, entity, String.class);
        //String forObject = restTemplate.getForObject(macroUrl, String.class);
        String forObject = exchange.getBody();
        return forObject;
    }

    private String handleMacroRes(String macroRes){
        JSONObject jsonObject = JSON.parseObject(macroRes).getJSONObject("data");
        // 心法名称
        String name = jsonObject.getString("name");
        // 奇穴
        String holes = jsonObject.getString("holes");
        if (StringUtils.isEmpty(holes)){
            holes = jsonObject.getString("qixue");
        }
        // 宏命令
        String command = jsonObject.getString("command");
        if (StringUtils.isEmpty(command)){
            command = jsonObject.getString("macro");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("【宏●").append(name).append("】\n")
                .append("推荐宏：\n").append(command).append("\n")
                .append("推荐奇穴：\n").append(holes).append("\n")
                .append("仅作参考！");
        return sb.toString();
    }
}
