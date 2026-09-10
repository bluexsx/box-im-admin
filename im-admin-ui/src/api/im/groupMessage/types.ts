export interface GroupMessageVO {
  /**
   * id
   */
  id: string | number;

  /**
   * 群id
   */
  groupId: number;

  /**
 * 群名
 */
  groupName: string,

  /**
   * 发送用户id
   */
  sendId: number;

  /**
 * 发送用户
 */
  sendUserName: string;


  /**
   * 发送用户昵称
   */
  sendNickName: string;

  /**
   * 被@用户id列表，逗号分隔
   */
  atUserIds: string | number;

  /**
   * 发送内容
   */
  content: string;

  /**
   * 
   */
  status: number;

  /**
   * 消息类型 0:文字 1:图片 2:文件
   */
  type: number;

  /**
   * 发送时间
   */
  sendTime: string;

  /**
   * 回执消息是否完成
   */
  receiptOk: number;

  /**
   * 是否回执消息
   */
  receipt: number;

}

export interface GroupMessageForm extends BaseEntity {
  /**
   * id
   */
  id?: string | number;

  /**
   * 群id
   */
  groupId?: string | number;

  /**
* 群名
*/
  groupName: string,

  /**
   * 发送用户id
   */
  sendId?: string | number;

  /**
   * 发送用户
   */
  sendUserName: string;
  /**
   * 发送用户昵称
   */
  sendNickName?: string;

  /**
   * 被@用户id列表，逗号分隔
   */
  atUserIds?: string | number;

  /**
   * 发送内容
   */
  content?: string;

  /**
   * 
   */
  status?: number;

  /**
   * 消息类型 0:文字 1:图片 2:文件
   */
  type?: number;

  /**
   * 发送时间
   */
  sendTime?: string;

  /**
   * 回执消息是否完成
   */
  receiptOk?: number;

  /**
   * 是否回执消息
   */
  receipt?: number;

}

export interface GroupMessageQuery extends PageQuery {

  /**
   * 群id
   */
  groupId?: string | number;

  /**
   * 发送用户id
   */
  sendId?: string | number;

  /**
   * 发送用户昵称
   */
  sendNickName?: string;

  /**
   * 被@用户id列表，逗号分隔
   */
  atUserIds?: string | number;

  /**
   * 发送内容
   */
  content?: string;

  /**
   * 
   */
  status?: number;

  /**
   * 消息类型 0:文字 1:图片 2:文件
   */
  type?: number;

  /**
   * 发送时间
   */
  sendTime?: string;

  /**
   * 回执消息是否完成
   */
  receiptOk?: number;

  /**
   * 是否回执消息
   */
  receipt?: number;

  /**
   * 日期范围参数
   */
  params?: any;
}



