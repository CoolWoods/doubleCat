package com.doublecat.entity.mirai;

import lombok.Data;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 10:05
 * @Modified By:
 */
@Data
public class SendMessageReq {
    private String message;
    private Long user_id;
    private Long group_id;
}
