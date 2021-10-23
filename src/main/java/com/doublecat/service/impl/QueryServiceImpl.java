package com.doublecat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doublecat.entity.mapper.DcMenu;
import com.doublecat.entity.mirai.Message;
import com.doublecat.service.IDoubleCatService;
import com.doublecat.service.MessageService;
import com.doublecat.utils.HttpEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Zongmin
 * @Date Create in 2021/8/3 0:08
 * @Modified By:
 */
@Slf4j
@Service
public class QueryServiceImpl implements IDoubleCatService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${jx3api.url}")
    private String jx3apiUrl;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MenuService menuService;

    /**
     * 收到消息
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {

    }

    /**
     * 收到消息
     *
     * @param message
     * @param object  备用参数
     */
    @Override
    public void onMessage(Message message, Object object) {
        // restTemplate.postForObject()
        DcMenu dcMenu = (DcMenu) object;
        Map<String, Object> map = new HashMap<>();
        try {
            String messageStr = message.getMessage().trim();
            String[] params = messageStr.split(" ");
            if (params.length < 2) {
                messageService.sendGroupMsg("查询条件格式有误！", message.getGroup_id());
                return;
            }
            String param = params[1];
            String queryParam = dcMenu.getQueryParam();
            JSONObject jsonObject = JSON.parseObject(queryParam, JSONObject.class);
            if (jsonObject.containsKey("name")) {
                map.put("name", param);
            }
            if (jsonObject.containsKey("server")) {
                map.put("server", param);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (map.isEmpty()) {
            messageService.sendGroupMsg("查询条件为空！", message.getGroup_id());
        }
        String jsonParam = JSONObject.toJSONString(map);
        HttpEntity<Object> entity = HttpEntityUtil.defaultHttpEntity(jsonParam);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://jx3api.com/app/" + dcMenu.getMenuEn(), entity, String.class);

        try {
            JSONObject responseBody = JSON.parseObject(responseEntity.getBody(), JSONObject.class);
            if (Objects.isNull(responseBody)) {
                messageService.sendGroupMsg("查询不到！", message.getGroup_id());
                return;
            }
            String code = responseBody.getString("code");
            if (!Objects.equals(code, "200")) {
                messageService.sendGroupMsg(responseBody.getString("msg"), message.getGroup_id());
                return;
            }
            log.info(responseBody.toJSONString());
            JSONObject resData = responseBody.getJSONObject("data");
            switch (dcMenu.getMenuEn().toLowerCase()) {
                case "macro":
                    this.handleMacroRes(message, resData);
                    break;
                case "strengthen":
                    this.handleStrengthenRes(message, resData);
                    break;
                case "check":
                    this.handleCheckRes(message, resData);
                    break;
                default:
                    messageService.sendGroupMsg("暂不支持该查询！", message.getGroup_id());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMacroRes(Message message, JSONObject macro) {
        // 心法名称
        String name = macro.getString("name");
        // 奇穴
        String plan = macro.getString("plan");
        // 宏命令
        String command = macro.getString("command");
        StringBuilder sb = new StringBuilder();
        sb.append("心法：").append(name).append("\n")
                .append("推荐奇穴：").append(plan).append("\n")
                .append("推荐宏：\n").append(command);
        messageService.sendGroupMsg(sb.toString(), message.getGroup_id());
    }


    private void handleStrengthenRes(Message message, JSONObject strengthen) {
        // 心法名称
        String name = strengthen.getString("name");
        // 增强食品
        String heightenFood = strengthen.getString("heightenFood");
        // 辅助食品
        String auxiliaryFood = strengthen.getString("auxiliaryFood");
        // 增强药品
        String heightenDrug = strengthen.getString("heightenDrug");
        // 辅助药品
        String auxiliaryDrug = strengthen.getString("auxiliaryDrug");
        StringBuilder sb = new StringBuilder();
        sb.append("心法：").append(name).append("\n")
                .append("增强食品：").append(heightenFood).append("\n")
                .append("辅助食品：").append(auxiliaryFood).append("\n")
                .append("增强药品：").append(heightenDrug).append("\n")
                .append("辅助药品：").append(auxiliaryDrug).append("\n");
        messageService.sendGroupMsg(sb.toString(), message.getGroup_id());
    }

    private void handleCheckRes(Message message, JSONObject strengthen) {
        // 区服名称
        String server = strengthen.getString("server");
        // 大区
        String region = strengthen.getString("region");
        // 状态
        String status = strengthen.getString("status");
        status = Objects.equals(status, "1") ? "正常" : "维护中";
        StringBuilder sb = new StringBuilder();
        sb.append("区服：").append(region).append(server)
                .append("\n").append("状态：").append(status);
        messageService.sendGroupMsg(sb.toString(), message.getGroup_id());
    }
}
