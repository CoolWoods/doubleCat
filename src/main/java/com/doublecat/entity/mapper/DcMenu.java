package com.doublecat.entity.mapper;

import java.util.Date;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * dc_menu
 * @author Zongm
 * @date 2021-08-01 09:18:05
 */
@Data
@FieldNameConstants
public class DcMenu {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 菜单名称
     */
    private String menu;

    /**
     * 菜单英文名称
     */
    private String menuEn;

    /**
     * 菜单键
     */
    private String menuDic;

    /**
     * 查询参数
     */
    private String queryParam;

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
    private Date creaeteDate;

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