package com.doublecat.service.impl;

import com.doublecat.entity.mapper.DcDic;
import com.doublecat.mapper.DcDicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;

/**
 * @Author Zongmin
 * @Date Create in 2021/10/23 10:32
 * @Modified By:
 */
@Service
@Slf4j
public class DcDicService {
    @Autowired
    private DcDicMapper dcDicMapper;

    /**
     * 通过别名查询名称，如果查不到则返回传入的值
     * @param aliasName
     * @return
     */
    public String getNameByAlias(String aliasName){
        Example example = new Example(DcDic.class);
        example.createCriteria().andLike(DcDic.Fields.dicAlias, aliasName);
        DcDic dcDic = dcDicMapper.selectOneByExample(example);
        if (!Objects.isNull(dcDic)){
            return dcDic.getDicName();
        }
        return aliasName;
    }
}
