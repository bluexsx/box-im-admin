package org.dromara.im.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.im.constant.ImConstant;
import org.dromara.im.domain.ImGroup;
import org.dromara.im.domain.ImGroupMessage;
import org.dromara.im.domain.ImUser;

import java.util.Date;



/**
 * 群消息视图对象 im_group_message
 *
 * @author Blue
 * @date 2024-12-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ImGroupMessage.class)
public class ImGroupMessageVo implements TransPojo {

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 群id
     */
    @Trans(type = TransType.SIMPLE,dataSource = ImConstant.DS_IM_PLATFORM,target = ImGroup.class, fields = "name", ref = "groupName")
    private Long groupId;

    @ExcelProperty(value = "群名称")
    private String groupName;

    /**
     * 发送用户id
     */
    @Trans(type = TransType.SIMPLE,dataSource = ImConstant.DS_IM_PLATFORM,target = ImUser.class, fields = "userName", ref = "sendUserName")
    private Long sendId;

    @ExcelProperty(value = "发送用户")
    private String sendUserName;


    /**
     * 发送用户昵称
     */
    @ExcelProperty(value = "发送用户昵称")
    private String sendNickName;

    /**
     * 被@用户id列表，逗号分隔
     */
    @ExcelProperty(value = "被@用户id列表，逗号分隔")
    private String atUserIds;

    /**
     * 发送内容
     */
    @ExcelProperty(value = "发送内容")
    private String content;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long status;

    /**
     * 消息类型 0:文字 1:图片 2:文件
     */
    @ExcelProperty(value = "消息类型 0:文字 1:图片 2:文件")
    private Long type;

    /**
     * 发送时间
     */
    @ExcelProperty(value = "发送时间")
    private Date sendTime;

    /**
     * 回执消息是否完成
     */
    @ExcelProperty(value = "回执消息是否完成")
    private Boolean receiptOk;

    /**
     * 是否回执消息
     */
    @ExcelProperty(value = "是否回执消息")
    private Boolean receipt;

}
