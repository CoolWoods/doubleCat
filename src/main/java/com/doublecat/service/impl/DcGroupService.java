package com.doublecat.service.impl;

import com.doublecat.entity.mapper.DcGroup;
import com.doublecat.mapper.DcGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 9:15
 * @Modified By:
 */
@Service
@Slf4j
public class DcGroupService {
    @Autowired
    private DcGroupMapper dcGroupMapper;

    /**
     * 查询开启的群组id
     * @return
     */
    private List<Long>  listAllGroupIds(Long selfId){
        Example example = new Example(DcGroup.class);
        example.createCriteria().andEqualTo(DcGroup.Fields.goupStatus, 1)
                .andEqualTo(DcGroup.Fields.createUserId, selfId);
        List<DcGroup> dcGroups = dcGroupMapper.selectByExample(example);
        List<Long> groupIds = dcGroups.stream().map(DcGroup::getGroupId).collect(Collectors.toList());
        log.info("启用的群组id：" + groupIds);
        return groupIds;
    }

    /**
     * 判断是否是开启的群组
     * @param groupId
     * @param selfId
     * @return
     */
    public boolean isOpenGroups(Long groupId, Long selfId){
        List<Long> groupIds = listAllGroupIds(selfId);
        return groupIds.contains(groupId);
    }
}
