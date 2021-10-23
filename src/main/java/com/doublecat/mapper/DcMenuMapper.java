package com.doublecat.mapper;

import com.doublecat.entity.mapper.DcMenu;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DcMenuMapper extends tk.mybatis.mapper.common.Mapper<DcMenu> {
}