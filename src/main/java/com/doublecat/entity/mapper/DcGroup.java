package com.doublecat.entity.mapper;

import java.util.Date;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * dc_group
 * @author Zongm
 * @date 2021-08-01 09:15:59
 */
@Data
@FieldNameConstants
public class DcGroup {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 群组名称
     */
    private String groupName;

    /**
     * 群组状态，0-未启用，1-启用
     */
    private Byte goupStatus;

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