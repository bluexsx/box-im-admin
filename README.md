# 盒子IM 后台管理

本项目为盒子IM 后台管理，主要为盒子IM 提供用户管理、群组管理、消息管理、敏感词管理等后台功能。

建议先把业务服务跑起来，再回来搭建后台服务。

| 资源     | 地址 |
|--------|------|
| 业务源码仓库 | [Gitee](https://gitee.com/bluexsx/box-im) \| [GitHub](https://github.com/bluexsx/box-im) |
| 详细技术文档 | https://www.yuque.com/u1475064/mufu2a |

## 基于 RuoYi-Vue-Plus 框架

为了减少重复工作、达到快速开发目的，本项目基于优秀的开源脚手架 RuoYi-Vue-Plus 进行二次开发。

|  | 仓库 |
|---|------|
| 后端 | https://gitee.com/dromara/RuoYi-Vue-Plus |
| 前端 | https://gitee.com/JavaLionLi/plus-ui |

## 框架改造说明

为了更好地与盒子IM 业务相结合，同时保持代码简洁，对 RuoYi-Vue-Plus 做了以下改造：

1. 移除了定时任务、监控、工作流模块
2. 添加 MinIO 模块替代原先的 OSS 模块
3. 加入 `ruoyi-im` 模块，此模块即为盒子IM 的核心后台模块
4. 为兼容历史数据，逻辑删除值由 `'2'` 修改为 `'1'`

## 本地快速启动

### 1. 安装运行环境

- Node：v18.19.0
- JDK：17
- Maven：3.9.6
- MySQL：8.0（账号密码均为 `root` / `root`），创建名为 `im_admin` 的数据库，并执行 `db/im-admin.sql`
- Redis：6.2
- MinIO：RELEASE.2024-xx，使用默认账号、密码、端口

> **注意：** 盒子IM 后台服务同时还依赖 `im-platform` 的数据库，请在启动前先初始化该数据库。

### 2. 启动后端服务

进入 `im-admin` 目录：

```bash
mvn clean package
java -jar ./ruoyi-admin/target/im-admin.jar
```

### 3. 启动前端

进入 `im-admin-ui` 目录：

```bash
npm install
npm run dev
```

访问 http://localhost:3000

默认管理员账号：`admin` / `admin123`

## 界面截图

![截图1](%E6%88%AA%E5%9B%BE/1.jpg)

![截图2](%E6%88%AA%E5%9B%BE/2.jpg)

![截图3](%E6%88%AA%E5%9B%BE/3.jpg)

![截图4](%E6%88%AA%E5%9B%BE/4.jpg)

![截图5](%E6%88%AA%E5%9B%BE/5.jpg)

## 点下 star 吧

如果项目对您有帮助，请点亮右上方的 star，支持一下作者吧！
