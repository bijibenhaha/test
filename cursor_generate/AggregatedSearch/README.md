# 聚合搜索

Vue 3 前端 + Spring Boot 后端，按 Tab 懒加载搜索文章（Post）、图片（Picture）、用户（User）。

## 功能说明

- Tab 顺序：**Post → Picture → User**
- 点击 **Search**：重置为 Post Tab，仅请求 `type=post`
- 点击 **Picture / User Tab**：首次切换时才请求对应类型；同一关键词下已加载过的 Tab 使用缓存，不重复请求

## 项目结构

```
cursor_generate/
├── backend/          # Spring Boot 3 + JPA + H2
└── frontend/         # Vue 3 + Vite
```

## 启动方式

### 后端

```bash
cd backend
mvn spring-boot:run
```

服务地址：`http://localhost:8080`

### 前端

```bash
cd frontend
npm install
npm run dev
```

页面地址：`http://localhost:5173`

## API

```http
GET /api/search?keyword=java&type=post&page=1&size=10
GET /api/search?keyword=java&type=picture&page=1&size=10
GET /api/search?keyword=java&type=user&page=1&size=10
```

**参数：**

| 参数 | 说明 |
|------|------|
| keyword | 搜索关键词（必填） |
| type | `post` / `picture` / `user`（必填） |
| page | 页码，默认 1 |
| size | 每页条数，默认 10 |

**响应示例：**

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "type": "post",
    "keyword": "java",
    "total": 2,
    "list": [...]
  }
}
```

## 试用关键词

内置示例数据，可搜索：`java`、`vue`、`photo`、`code` 等。
