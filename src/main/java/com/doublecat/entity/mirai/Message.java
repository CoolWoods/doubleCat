package com.doublecat.entity.mirai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @Author Zongmin
 * @Date Create in 2021/7/31 9:45
 * @Modified By:
 */
@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String request_type;
    private String comment;
    private String flag;

    private String type;
    private Long self_id;
    private String sub_type;
    private Long message_id;
    private Long user_id;
    private Long group_id;
    private String message;
    private String raw_message;
    private Long font;
    private Sender sender;
    private Long time;
    private String post_type;
    private String message_type;
}

