package com.doublecat.entity.mapper;

import java.util.Date;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * dc_team
 * @author Zongm
 * @date 2021-08-01 09:17:08
 */
@Data
@FieldNameConstants
public class DcTeam {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String modifyUser;

    /**
     * 更新人id
     */
    private String modifyUserId;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 是否删除，0-否
     */
    private Boolean isDelete;
}