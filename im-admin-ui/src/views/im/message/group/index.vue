<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="群聊" prop="groupId">
              <im-group-select v-model="queryParams.groupId"></im-group-select>
            </el-form-item>
            <el-form-item label="发送用户" prop="sendId">
              <im-user-select v-model="queryParams.sendId"></im-user-select>
            </el-form-item>
            <el-form-item label="发送时间" prop="createdTime">
              <el-date-picker
                    v-model="dateRange"
                    value-format="YYYY-MM-DD HH:mm:ss"
                    type="daterange"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="[new Date(2000, 1, 1, 0, 0, 0), new Date(2000, 1, 1, 23, 59, 59)]"
                  ></el-date-picker>
            </el-form-item>
            <el-form-item label="内容" prop="content">
              <el-input v-model="queryParams.content"></el-input>
            </el-form-item>
            <el-form-item label="消息类型" prop="type">
              <el-select v-model="queryParams.type"  clearable>
                <el-option v-for="dict in im_message_type" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
              <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </transition>
    <el-card shadow="never">
      <el-table v-loading="loading" :data="groupMessageList" >
        <el-table-column label="群名" align="center" prop="groupName" />
        <el-table-column label="发送用户" align="center" prop="sendUserName" />
        <el-table-column label="发送内容" align="center" prop="content"  width="400">
          <template #default="scope">
            <im-message-content :message="scope.row" ></im-message-content>
          </template>
        </el-table-column>
        <el-table-column label="消息类型" align="center" prop="type" >
          <template #default="scope">
            <dict-tag :options="im_message_type" :value="scope.row.type" />
          </template>
        </el-table-column>
        <el-table-column label="是否撤回" align="center" prop="status" >
          <template #default="scope">
            <dict-tag :options="im_bool" :value="''+(scope.row.status == 2)" />
          </template>
        </el-table-column>
        <el-table-column label="发送时间" align="center" prop="sendTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
              <el-button link type="primary"  @click="handleDetail(scope.row)" v-hasPermi="['im:groupMessage:query']">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改群消息对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="groupMessageFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发送用户" prop="sendUserName">
          <el-input v-model="form.sendUserName" />
        </el-form-item>
        <el-form-item label="发送内容">
          <im-message-content :message="form" ></im-message-content>
        </el-form-item>
        <el-form-item label="发送时间" prop="sendTime">
          <el-date-picker clearable
            v-model="form.sendTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择发送时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item v-if="form.receipt" label="是否撤回" prop="status">
          <dict-tag :options="im_bool" :value="'' + (form.status == 2)" />
        </el-form-item>
        <el-form-item label="是否回执消息" prop="receipt">
          <el-input v-model="form.receipt" placeholder="请输入是否回执消息" />
        </el-form-item>
        <el-form-item v-if="form.receipt" label="回执消息是否完成" prop="receiptOk">
          <dict-tag :options="im_bool" :value="form.receiptOk" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="GroupMessage" lang="ts">
import { listGroupMessage, getGroupMessage } from '@/api/im/groupMessage';
import { GroupMessageVO, GroupMessageQuery, GroupMessageForm } from '@/api/im/groupMessage/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const groupMessageList = ref<GroupMessageVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const groupMessageFormRef = ref<ElFormInstance>();
const dateRange = ref<[DateModelType, DateModelType]>(['', '']);

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: GroupMessageForm = {
  id: undefined,
  groupId: undefined,
  groupName: undefined,
  sendId: undefined,
  sendUserName: undefined,
  sendNickName: undefined,
  atUserIds: undefined,
  content: undefined,
  status: undefined,
  type: undefined,
  sendTime: undefined,
  receiptOk: undefined,
  receipt: undefined
}
const data = reactive<PageData<GroupMessageForm, GroupMessageQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    groupId: undefined,
    sendId: undefined,
    sendNickName: undefined,
    atUserIds: undefined,
    content: undefined,
    status: undefined,
    type: undefined,
    sendTime: undefined,
    receiptOk: undefined,
    receipt: undefined,
    params: {
    }
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);
const { im_message_type,im_bool } = toRefs<any>(proxy?.useDict('im_message_type','im_bool'));

/** 查询群消息列表 */
const getList = async () => {
  loading.value = true;
  const res = await listGroupMessage(proxy?.addDateRange(queryParams.value, dateRange.value));
  groupMessageList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 表单重置 */
const reset = () => {
  form.value = {...initFormData};
  groupMessageFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 确定按钮 */
const submitForm = () => {
  dialog.visible = false;
}

/** 详情按钮操作 */
const handleDetail = async (row?: GroupMessageVO) => {
  reset();
  const _id = row?.id
  const res = await getGroupMessage(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "消息详情";
}

onMounted(() => {
  getList();
});
</script>
