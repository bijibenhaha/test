# 聚合搜索 Aggregated Search

根据搜索词聚合搜索 **文章（Post）**、**图片（Picture）**、**用户（User）**。

- **后端**：Spring Boot 2.7.2 + MyBatis-Plus + JDK 8
- **存储**：MySQL（user 直接查 MySQL）
- **检索引擎**：Elasticsearch 7.17（post 走 ES 全文检索，MySQL 通过定时任务同步到 ES）
- **图片**：jsoup 爬取必应图片搜索（不依赖数据库，可无限获取实时图片）
- **前端**：Vue 3 + Vue CLI 5 + Ant Design Vue 4 + TypeScript

## 功能

- Tab 顺序：**Post → Picture → User**
- **点击 Search**：重置为 Post Tab，只向后端请求 `type=Post`
- **点击 Picture / User Tab**：首次切换才请求对应类型；同一关键词下已加载过的 Tab 用缓存，不重复请求
- 三类数据各自分页；搜索关键词高亮显示

## 架构

```
SearchController 
──> SearchFacade（门面）
      │──（DataSource 接口）
            ├── PostSourceImpl──> PostService.searchFromEs() ──> Elasticsearch
            ├── PictureSourceImpl ──> PictureService（jsoup 爬必应图片搜索）
            └── UserSourceImpl ──> UserService ──> MySQL
```

新增数据源只需实现 `DataSource<T>` 接口并在 `SearchFacade` 注册即可。

### 图片搜索（爬虫）

- 请求 `https://cn.bing.com/images/search?q=关键词&first=偏移量` 抓取必应图片搜索页
- 用 jsoup 解析 `.iuscp.isv.smallheight` 元素，经 `utils/BingImageUtil` 从 `mediaurl` 参数解码真实图片地址
- 分页通过必应 `first` 偏移量实现；因爬虫无法得知总数，`total` 固定返回 1000 以支持前端翻页
- 数据库中的 `picture` 表已不再参与图片搜索，仅保留历史数据

### MySQL → ES 同步

- **全量**：`job/once/FullSyncPostToEs`（`CommandLineRunner`，应用启动时执行一次）
- **增量**：`job/cycle/IncSyncPostToEs`（`@Scheduled(fixedRate = 60s)`，查近 5 分钟 `updateTime` 变化的 post）
- 写入走 MySQL，检索走 ES，`@Document(indexName = "post")`，`@Id` 与 MySQL 主键一致保证覆盖写

## 启动步骤

### 1. 前置：MySQL + Elasticsearch

确保 MySQL 在 `localhost:3306`（账号密码见 `backend/src/main/resources/application.yml`），ES 在 `localhost:9200`。

### 2. 初始化数据库

```bash
cd backend
mysql -uroot -p < sql/create_table.sql
```

### 3. 启动后端（需 JDK 8）

```bash
cd backend
export JAVA_HOME=/Users/duqingyang/Library/Java/JavaVirtualMachines/corretto-1.8.0_432/Contents/Home
mvn spring-boot:run
```

服务地址：`http://localhost:8101/api`，启动时会自动全量同步 post 到 ES（日志见 `FullSyncPostToEs`）。

### 4. 启动前端

```bash
cd frontend
npm install
npm run serve
```

页面地址：`http://localhost:8080`（dev server 已配置 `/api` 代理到 `8101`）。

## API

```
POST /api/search/all
Content-Type: application/json

// 指定类型（前端每次请求都带 type）
{ "searchText": "java", "type": "Post",    "current": 1, "pageSize": 10 }
{ "searchText": "java", "type": "Picture", "current": 1, "pageSize": 10 }
{ "searchText": "java", "type": "User",    "current": 1, "pageSize": 10 }

// 不传 type：一次返回三类
{ "searchText": "java", "current": 1, "pageSize": 10 }
```

**响应（单类型）：**

```json
{
  "code": 0,
  "message": "ok",
  "data": {
    "dataList": [ ... ],
    "total": 6
  }
}
```

## 目录结构

```
AggregatedSearch/
├── backend/
│   ├── sql/create_table.sql      # 建表 + 种子数据
│   └── src/main/
│       ├── resources/application.yml
│       └── java/com/example/search/
│           ├── controller/SearchController.java
│           ├── manager/SearchFacade.java        # 聚合门面
│           ├── datasource/                      # DataSource<T> 适配器 ×3
│           ├── service/                         # Post(ES)/Picture(必应爬虫)/User(MySQL)
│           ├── utils/BingImageUtil.java         # 必应图片地址解码
│           ├── esdao/PostEsDao.java
│           ├── job/                             # 全量 + 增量同步
│           └── model/                           # entity/dto/vo/enums
└── frontend/
    └── src/
        ├── pages/IndexPage.vue                  # 搜索框 + Tab
        ├── composables/useSearch.ts             # Tab 懒加载逻辑
        └── components/                          # PostList/PictureList/UserList
```

## 试用关键词

种子数据含 `java`、`vue`、`spring`、`elasticsearch`、`docker` 等；若 `my_db` 库已有旧数据也可直接搜到。
