package org.dromara.im.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 群消息对象 im_group_message
 *
 * @author Blue
 * @date 2024-12-22
 */
@Data
@TableName("im_group_message")
public class ImGroupMessage {

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 群id
     */
    private Long groupId;

    /**
     * 发送用户id
     */
    private Long sendId;

    /**
     * 发送用户昵称
     */
    private String sendNickName;

    /**
     * 被@用户id列表，逗号分隔
     */
    private String atUserIds;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 
     */
    private Long status;

    /**
     * 消息类型 0:文字 1:图片 2:文件
     */
    private Long type;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 回执消息是否完成
     */
    private Boolean receiptOk;

    /**
     * 是否回执消息
     */
    private Boolean receipt;

}
