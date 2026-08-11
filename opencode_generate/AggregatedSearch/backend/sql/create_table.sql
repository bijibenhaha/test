-- 数据库初始化
create database if not exists my_db;

use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 图片表
create table if not exists picture
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)                        not null comment '图片名称',
    url         varchar(1024)                       not null comment '图片地址',
    description varchar(1024)                       null comment '图片描述',
    createTime  datetime default CURRENT_TIMESTAMP  not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP  not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                  not null comment '是否删除'
) comment '图片' collate = utf8mb4_unicode_ci;

-- ==================== 种子数据 ====================

-- 用户
insert into user (userAccount, userPassword, userName, userAvatar, userProfile, userRole) values
('java_dev', '123456', 'Java开发者', 'https://picsum.photos/seed/user1/80/80', '专注 Java 后端开发，Spring Boot 与 Elasticsearch', 'user'),
('vue_fan', '123456', 'Vue爱好者', 'https://picsum.photos/seed/user2/80/80', '前端 Vue 与 UI 设计爱好者', 'user'),
('photo_master', '123456', '摄影达人', 'https://picsum.photos/seed/user3/80/80', '分享风景与人像摄影作品', 'user'),
('code_newbie', '123456', '编程新手', 'https://picsum.photos/seed/user4/80/80', '正在学习 Java 和 Vue', 'user'),
('es_expert', '123456', '搜索工程师', 'https://picsum.photos/seed/user5/80/80', '深入研究 Elasticsearch 全文检索', 'admin');

-- 帖子（tags 为 json 数组字符串）
insert into post (title, content, tags, thumbNum, favourNum, userId) values
('Java 入门指南', '本文介绍 Java 基础语法与面向对象编程，适合初学者阅读。', '["java","入门"]', 120, 35, 1),
('Spring Boot 实战', '使用 Spring Boot 快速构建 REST API，包含 MyBatis-Plus 与数据库配置。', '["spring","java"]', 230, 88, 1),
('Vue3 组合式 API', 'Vue3 Composition API 让逻辑复用更简单，配合 Pinia 管理状态。', '["vue","前端"]', 180, 60, 2),
('Elasticsearch 全文检索入门', '从索引、分词到查询 DSL，带你理解 ES 全文检索原理。', '["elasticsearch","搜索"]', 320, 150, 5),
('MySQL 索引优化实战', 'MySQL 索引原理与慢查询优化实践，提升查询性能。', '["mysql","数据库"]', 95, 22, 1),
('Docker 部署 Spring Boot 应用', '使用 Dockerfile 一键构建并运行 Spring Boot 服务。', '["docker","部署"]', 76, 18, 5);

-- 图片
insert into picture (name, url, description) values
('Java 咖啡', 'https://picsum.photos/seed/pic1/400/300', '一杯热咖啡与代码'),
('山景', 'https://picsum.photos/seed/pic2/400/300', '清晨的山脉与云雾'),
('城市夜景', 'https://picsum.photos/seed/pic3/400/300', '霓虹灯下的都市'),
('编程桌面', 'https://picsum.photos/seed/pic4/400/300', '双屏开发环境'),
('海边日出', 'https://picsum.photos/seed/pic5/400/300', '朝阳洒在海面上'),
('Java 吉祥物', 'https://picsum.photos/seed/pic6/400/300', 'Duke 吉祥物与代码');
