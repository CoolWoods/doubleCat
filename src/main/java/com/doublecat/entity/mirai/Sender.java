package com.doublecat.entity.mirai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 9:46
 * @Modified By:
 */
@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Sender {
    private Long user_id;
    private String nickname;
    private String sex;
    private int age;
    private Long group_id;
}
