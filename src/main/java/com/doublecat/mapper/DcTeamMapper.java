package com.doublecat.mapper;

import com.doublecat.entity.mapper.DcTeam;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DcTeamMapper extends tk.mybatis.mapper.common.Mapper<DcTeam> {
    @Update("UPDATE dc_team SET is_delete=1")
    void deleteTeam();
}