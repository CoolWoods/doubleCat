package com.doublecat.entity.mapper;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * dc_dic
 * @author Zongm
 * @date 2021-10-23 10:28:29
 */
@Data
@FieldNameConstants
public class DcDic {
    /**
     */
    private Long id;

    /**
     * 名称
     */
    private String dicName;

    /**
     * 编码
     */
    private String dicCode;

    /**
     * 别名
     */
    private String dicAlias;
}