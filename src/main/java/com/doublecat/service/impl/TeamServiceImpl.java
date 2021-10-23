package com.doublecat.service.impl;

import com.doublecat.entity.mapper.DcTeam;
import com.doublecat.entity.mirai.Message;
import com.doublecat.mapper.DcTeamMapper;
import com.doublecat.service.IDoubleCatService;
import com.doublecat.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @Author Zongmin
 * @Date Create in 2021/8/1 10:05
 * @Modified By:
 */
@Slf4j
@Service
public class TeamServiceImpl implements IDoubleCatService {
    @Autowired
    private MessageService messageService;
    @Autowired
    private DcTeamMapper dcTeamMapper;
    /**
     * 收到消息
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        Long userId = message.getUser_id();
        if (!Objects.equals(81128780L, userId)){
            messageService.sendGroupMsg("您没有权限创建团队", message.getGroup_id());
            return;
        }
        String messageStr = message.getMessage().trim();
        String[] split = messageStr.split(" ");
        if (split.length < 2){
            messageService.sendGroupMsg("团队名称不能为空", message.getGroup_id());
            return;
        }
        StringBuilder teamNameSb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == 0){
                continue;
            }
            teamNameSb.append(split[i]);
        }
        String teamName = teamNameSb.toString();
        Example example = new Example(DcTeam.class);
        example.createCriteria()
                .andEqualTo(DcTeam.Fields.teamName, teamName)
                .andEqualTo(DcTeam.Fields.isDelete, false);
        List<DcTeam> dcTeams = dcTeamMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(dcTeams)){
            // 将所有团重置为删除状态
            dcTeamMapper.deleteTeam();
        }
        DcTeam dcTeam = new DcTeam();
        dcTeam.setTeamName(teamName);
        dcTeam.setCreateUser(message.getUser_id().toString());
        dcTeam.setCreateUserId(message.getUser_id().toString());
        dcTeam.setCreateDate(new Date());
        dcTeam.setIsDelete(false);
        dcTeamMapper.insertSelective(dcTeam);
        messageService.sendGroupMsg("添加团队：【" + teamName + "】成功！", message.getGroup_id());
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
}
