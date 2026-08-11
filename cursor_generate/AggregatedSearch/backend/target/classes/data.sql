INSERT INTO posts (title, content, author_name, cover_url, created_at) VALUES
('Java 入门指南', '本文介绍 Java 基础语法与面向对象编程，适合初学者阅读。', '张三', 'https://picsum.photos/seed/post1/400/240', CURRENT_TIMESTAMP),
('Spring Boot 实战', '使用 Spring Boot 快速构建 REST API，包含 JPA 与数据库配置。', '李四', 'https://picsum.photos/seed/post2/400/240', CURRENT_TIMESTAMP),
('Vue3 组合式 API', 'Vue3 Composition API 让逻辑复用更简单，配合 Pinia 管理状态。', '王五', 'https://picsum.photos/seed/post3/400/240', CURRENT_TIMESTAMP),
('数据库索引优化', 'MySQL 索引原理与慢查询优化实践，提升搜索性能。', '赵六', 'https://picsum.photos/seed/post4/400/240', CURRENT_TIMESTAMP);

INSERT INTO pictures (title, description, tags, thumbnail_url, width, height, created_at) VALUES
('Java 咖啡', '一杯热咖啡与代码', 'java,coffee', 'https://picsum.photos/seed/pic1/300/300', 300, 300, CURRENT_TIMESTAMP),
('山景', '清晨的山脉与云雾', 'nature,landscape', 'https://picsum.photos/seed/pic2/300/300', 300, 300, CURRENT_TIMESTAMP),
('城市夜景', '霓虹灯下的都市', 'city,night', 'https://picsum.photos/seed/pic3/300/300', 300, 300, CURRENT_TIMESTAMP),
('编程桌面', '双屏开发环境', 'code,desk', 'https://picsum.photos/seed/pic4/300/300', 300, 300, CURRENT_TIMESTAMP);

INSERT INTO app_users (username, nickname, bio, avatar_url, follower_count, created_at) VALUES
('java_dev', 'Java开发者', '专注 Java 后端开发', 'https://picsum.photos/seed/user1/80/80', 1200, CURRENT_TIMESTAMP),
('vue_fan', 'Vue爱好者', '前端 Vue 与 UI 设计', 'https://picsum.photos/seed/user2/80/80', 856, CURRENT_TIMESTAMP),
('photo_master', '摄影达人', '分享风景与人像摄影', 'https://picsum.photos/seed/user3/80/80', 2340, CURRENT_TIMESTAMP),
('code_newbie', '编程新手', '正在学习 Java 和 Vue', 'https://picsum.photos/seed/user4/80/80', 42, CURRENT_TIMESTAMP);
