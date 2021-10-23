package com.doublecat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doublecat.common.Constant;
import com.doublecat.entity.enums.ItemNameEnum;
import com.doublecat.entity.enums.MenuEnum;
import com.doublecat.entity.mirai.Message;
import com.doublecat.service.IDoubleCatService;
import com.doublecat.service.MessageService;
import javafx.scene.input.DataFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 15:35
 * @Modified By:
 */
@Service
@Slf4j
public class DefaultQueryServiceImpl implements IDoubleCatService {
    @Autowired
    private DcDicService dcDicService;
    @Autowired
    private RestTemplate restTemplate;
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
    public void onMessage(Message message) throws ParseException {
        String[] menuNames = message.getMessage().split(" ");
        String menuName;
        if (menuNames.length == 1) {
            menuName = dcDicService.getNameByAlias(menuNames[0]);
        } else {
            menuName = dcDicService.getNameByAlias(menuNames[1]);
        }
        MenuEnum menuEnum = MenuEnum.getApiInstanceByValue(menuName);
        String simpleQueryJson = sendSimpleQuery(menuEnum, menuName);
        String simpleQueryRes = handleSimpleQueryJson(simpleQueryJson, menuEnum);
        messageService.sendGroupMsg(simpleQueryRes, message.getGroup_id());
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

    /**
     * 简单查询，仅支持一个参数的GET方式查询
     *
     * @param menuEnum
     * @param queryParam
     * @return
     */
    private String sendSimpleQuery(MenuEnum menuEnum, String queryParam) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity(null, headers);
        // 临时使用get方式请求
        String macroUrl = baseUrl + menuEnum.getQueryValue() + "?+" + menuEnum.getQueryCode() + "=" + queryParam;
        ResponseEntity<String> exchange = restTemplate.exchange(macroUrl, HttpMethod.GET, entity, String.class);
        //String forObject = restTemplate.getForObject(macroUrl, String.class);
        String forObject = exchange.getBody();
        return forObject;
    }

    private String handleSimpleQueryJson(String simpleQueryJson, MenuEnum menuEnum) throws ParseException {
        StringBuilder sb = new StringBuilder();
        JSONObject jsonObject = JSON.parseObject(simpleQueryJson).getJSONObject("data");
        Set<String> keySet = jsonObject.keySet();
        sb.append("查询【").append(menuEnum.getQueryName()).append("】\n");
        for (String key : keySet) {
            String value = jsonObject.getString(key);
            if (Constant.DATE_TIME.equalsIgnoreCase(key)) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
                value = Constant.DATE_TIME_NAME + now.format(pattern);
                sb.append(value).append(" ");
                continue;
            }
            if (Constant.WEEK.equalsIgnoreCase(key)) {
                value = Constant.WEEK_NAME + value;
                sb.append(value).append("\n");
                continue;
            }
            ItemNameEnum[] itemNameEnums = ItemNameEnum.values();
            for (ItemNameEnum itemNameEnum : itemNameEnums) {
                if (itemNameEnum.getItemName().equalsIgnoreCase(key)){
                    value = Constant.PRE_FIX + itemNameEnum.getItemValue() + Constant.AFTER_FIX + value;
                    sb.append(value).append("\n");
                    value = "";
                }
            }
        }
        return sb.toString();
    }
}
