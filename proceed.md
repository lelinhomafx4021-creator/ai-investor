# AI 量化投资平台 - 开发进度规划

> 后端用 Swagger(Knife4j) 测试，前端最后用 AI 生成

---

## 📅 开发周期：25天

---

## 🟢 第一阶段：基础搭建（Day 1-3）✅ 已完成

### Day 1 - 项目初始化 ✅ 已完成

- [x] 创建 Spring Boot 项目
- [x] 配置 pom.xml 依赖
- [x] 配置 application.yml
- [x] 配置 .gitignore
- [x] 推送到 GitHub

### Day 2 - 数据库搭建 ✅ 已完成

- [x] 执行 init.sql 创建数据库
- [x] 创建实体类（Entity）
- [x] 创建 Mapper 接口
- [x] 集成 MyBatis-Plus
- [x] 测试数据库连接

### Day 3 - 通用模块 ✅ 已完成

- [x] 统一返回格式 Result
- [x] 全局异常处理 GlobalExceptionHandler
- [x] Knife4j 接口文档配置
- [x] 测试 Swagger 页面

---

## 🟢 第二阶段：用户模块（Day 4-6）✅ 已完成

### Day 4 - 用户注册登录 ✅ 已完成

- [x] 注册接口 `/api/register`
- [x] 登录接口 `/api/login`
- [x] JWT 工具类 `JwtUtil`（createToken、getTokenInfo、verify）
- [x] DTO 参数校验（@Valid + @NotBlank）
- [x] 校验异常统一处理
- [x] Result 方法重命名（successMsg、failMsg 避免泛型冲突）

### Day 5 - JWT 鉴权 ✅ 已完成

- [x] JWT 拦截器 `AuthInterceptor`
- [x] 配置拦截规则 `WebConfig`
- [x] 放行 Swagger/Knife4j 路径
- [x] 测试登录验证
- [x] 数据库唯一键异常处理 `DuplicateKeyException`

### Day 6 - 用户功能 ✅ 已完成

- [x] 获取当前用户信息 `/api/users/me`
- [x] 修改密码 `/api/users/updatePassword`
- [x] 密码加密（BCryptPasswordEncoder）
- [x] UserVO 脱敏返回（不含密码）
- [ ] 管理员：用户列表 `/api/admin/users`（后续做）

---

## 🟢 第三阶段：股票行情（Day 7-10）

### Day 7 - 股票管理 ✅ 已完成

- [x] 股票 CRUD 接口（StockController）
- [x] 股票列表查询（分页 + 关键字搜索）
- [x] 管理员：添加/编辑/删除股票（AdminInterceptor权限拦截）
- [x] 批量新增股票

### Day 8 - 行情模拟 ✅ 已完成

- [x] 定时任务模拟价格波动（MarketPriceTask，每5秒）
- [x] 价格存入 Redis（RedisConfig + 缓存优先查询）
- [x] 行情查询接口（从缓存获取）

### Day 9 - WebSocket 推送 ✅ 已完成

- [x] WebSocket 配置（WebSocketConfig）
- [x] 实时行情推送（MarketPriceTask 调用 broadcast）
- [x] 连接管理（MarketWebSocketHandler）

### Day 10 - 行情测试 ✅ 已完成

- [x] Swagger 测试所有接口
- [x] WebSocket 测试客户端（piesocket 测试通过）

---

## 🟡 第四阶段：交易系统（Day 11-14）

### Day 11 - 买入功能

- [ ] 买入接口
- [ ] 余额校验
- [ ] 更新持仓
- [ ] 记录交易

### Day 12 - 卖出功能

- [ ] 卖出接口
- [ ] 持仓校验
- [ ] 更新余额
- [ ] 记录交易

### Day 13 - 分布式锁

- [ ] Redisson 配置
- [ ] 买入加锁
- [ ] 卖出加锁
- [ ] 防止超卖

### Day 14 - 持仓和记录

- [ ] 持仓列表接口
- [ ] 盈亏计算
- [ ] 交易记录查询
- [ ] 分页功能

---

## 🟡 第五阶段：AI 功能（Day 15-20）⭐ 核心

### Day 15 - Spring AI 集成

- [ ] AI 配置
- [ ] 基础对话接口
- [ ] 流式输出

### Day 16 - 知识库管理

- [ ] 知识 CRUD
- [ ] 文档上传
- [ ] 文本分块

### Day 17 - RAG 检索

- [ ] Embedding 向量化
- [ ] 向量存储 Redis
- [ ] 相似度检索

### Day 18 - Function Calling（查询）

- [ ] 查询股票工具
- [ ] 查询持仓工具
- [ ] 工具注册

### Day 19 - Function Calling（交易）

- [ ] 买入股票工具
- [ ] 卖出股票工具
- [ ] 安全确认

### Day 20 - 对话完善

- [ ] ChatMemory 持久化
- [ ] 会话管理
- [ ] 对话历史

---

## 🟡 第六阶段：消息通知（Day 21-22）

### Day 21 - 通知系统

- [ ] 通知表 CRUD
- [ ] 发送通知
- [ ] 标记已读

### Day 22 - WebSocket 通知

- [ ] 实时推送通知
- [ ] 未读数量
- [ ] 通知列表

---

## 🟡 第七阶段：前端开发（Day 23-25）

### Day 23 - AI 生成前端

- [ ] 用 AI 生成 Vue 项目
- [ ] 登录注册页
- [ ] 布局组件

### Day 24 - 核心页面

- [ ] 行情页面
- [ ] 交易页面
- [ ] 持仓页面

### Day 25 - AI 对话页

- [ ] 对话界面
- [ ] 流式显示
- [ ] 联调测试

---

## 📊 技术检查清单

### 必须完成

- [x] JWT 登录 ✅
- [x] JWT 拦截器 ✅
- [ ] 增删改查
- [ ] WebSocket 推送
- [ ] Redis 缓存
- [ ] 分布式锁
- [ ] Spring AI 对话
- [ ] Function Calling
- [ ] RAG 知识检索

### 加分项

- [ ] RabbitMQ 削峰
- [ ] Sentinel 限流
- [ ] 排行榜

---

## 📝 每日记录

### 2026-01-17

- ✅ 项目初始化完成
- ✅ pom.xml 依赖配置
- ✅ application.yml 配置
- ✅ 推送到 GitHub
- ✅ 数据库表设计

### 2026-01-18

- ✅ 执行 init.sql 创建数据库
- ✅ 创建实体类 PO
- ✅ 创建 Mapper 接口
- ✅ Knife4j 配置
- ✅ Result 统一返回类
- ✅ GlobalExceptionHandler 异常处理

### 2026-01-19

- ✅ JwtUtil 工具类（createToken、getTokenInfo、verify）
- ✅ LoginRequestDTO、RegisterRequestDTO
- ✅ AuthController（登录、注册接口）
- ✅ @Valid + @NotBlank 参数校验
- ✅ MethodArgumentNotValidException 处理

### 2026-01-20

- ✅ AuthInterceptor JWT 拦截器
- ✅ WebConfig 拦截器配置
- ✅ 放行 Knife4j/Swagger 路径
- ✅ 解决依赖版本冲突（Spring Boot 3.2.5）
- ✅ 修复 Result.success(String) 泛型冲突问题
- ✅ 注册逻辑修复 + DuplicateKeyException 处理
- ✅ 获取用户信息接口 `/api/users/me`
- ✅ UserVO 脱敏返回（BeanUtil.copyProperties）
- ✅ BCrypt 密码加密（SecurityConfig + @Bean）
- ✅ 修改注册/登录逻辑（encode + matches）
- ✅ 修改密码接口 `/api/users/updatePassword`
- ✅ UpdatePasswordDTO 参数校验
- 📌 下一步：Day 7 股票管理

### 2026-01-22

- ✅ StockController 股票CRUD接口
- ✅ 分页搜索股票（PageQuery + lambdaQuery）
- ✅ 根据ID/代码获取股票
- ✅ 管理员新增/批量新增/删除/更新股票
- ✅ AdminInterceptor 管理员权限拦截
- ✅ RedisConfig 配置（JSON序列化 + JavaTimeModule）
- ✅ MarketPriceTask 定时任务（每5秒模拟价格波动）
- ✅ 缓存优先查询策略
- 📌 下一步：Day 9 WebSocket 实时推送

---

## 🎯 当前任务

### Day 9 - WebSocket 推送

1. WebSocket 配置
   - WebSocketConfig 配置类
   - 握手拦截器（可选JWT验证）

2. 实时行情推送
   - WebSocketHandler 处理器
   - 定时任务推送价格到客户端

3. 连接管理
   - Session 管理
   - 断线处理

---

## 📁 已完成的文件

```
src/main/java/com/investor/
├── controller/
│   ├── AuthController.java     ✅ 登录注册接口（BCrypt加密）
│   └── UserController.java     ✅ 用户功能（获取信息、改密码）
├── entity/
│   ├── dto/
│   │   ├── LoginRequestDTO.java    ✅
│   │   ├── RegisterRequestDTO.java ✅
│   │   └── UpdatePasswordDTO.java  ✅ 修改密码参数
│   ├── vo/
│   │   └── UserVO.java             ✅ 用户信息返回（脱敏）
│   └── po/                     ✅ 所有实体类
├── mapper/                     ✅ 所有 Mapper
├── service/                    ✅ 所有 Service
├── util/
│   └── JwtUtil.java            ✅ JWT 工具类
├── common/
│   └── Result.java             ✅ 统一返回
├── exception/
│   └── GlobalExceptionHandler.java ✅ 异常处理
└── config/
    ├── OpenApiConfig.java      ✅ 接口文档配置
    ├── AuthInterceptor.java    ✅ JWT 拦截器
    ├── WebConfig.java          ✅ 拦截器配置
    └── SecurityConfig.java     ✅ BCrypt 密码加密配置
```

---

**加油！🚀**
