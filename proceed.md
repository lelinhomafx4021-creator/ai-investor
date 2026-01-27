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

## � 第四阶段：交易系统（Day 11-14）✅ 已完成

### Day 11 - 买入功能 ✅ 已完成

- [x] 买入接口（TradeController + TradeService）
- [x] 余额校验（compareTo + 异常处理）
- [x] 更新持仓（新用户save / 老用户计算均价update）
- [x] 记录交易（TradeRecord）
- [x] @Transactional 事务管理
- [x] 空值检查（股票不存在异常）

### Day 12 - 卖出功能 ✅ 已完成

- [x] 卖出接口
- [x] 持仓校验
- [x] 更新余额
- [x] 记录交易
- [x] 交易锁机制（同买入）

### Day 13 - 分布式锁 ✅ 已完成

- [x] Redisson 依赖配置 (3.27.0)
- [x] RedissonClient 自动装配
- [x] 买入加锁 (lock:trade:userId)
- [x] 卖出加锁
- [x] 防止超卖/并发冲突 (tryLock + 看门狗)

### Day 14 - 持仓和记录 ✅ 已完成

- [x] 持仓列表接口 (HoldingController + VO)
- [x] 盈亏计算 (VO getter)
- [x] 交易记录查询 (TradeRecordController)
- [x] 分页功能 (PageQuery)

---

## 🟢 第五阶段：AI 功能（Day 15-20）⭐ 核心

### Day 15 - Spring AI 集成 ✅ 已完成

- [x] AI 配置（AiConfig + ChatClient）
- [x] 基础对话接口（ChatController）
- [ ] 流式输出（进行中）

### Day 16 - 知识库管理

- [ ] 知识 CRUD
- [ ] 文档上传
- [ ] 文本分块

### Day 17 - RAG 检索

- [ ] Embedding 向量化
- [ ] 向量存储 Redis
- [ ] 相似度检索

### Day 18 - Function Calling（查询）✅ 已完成

- [x] 查询股票工具（getStockByName、getStockByCode）
- [x] 查询持仓工具（getUserHoldings）
- [x] 查询所有股票（getAllStocks）
- [x] 推荐股票（recommendStocks）
- [x] 工具注册（AiConfig.defaultTools）

### Day 19 - Function Calling（交易）✅ 已完成

- [x] 买入股票工具（buyStockByName）
- [x] 卖出股票工具（sellStockByName）
- [x] 支持名称或代码买入卖出
- [x] UserContents ThreadLocal 传递用户ID

### Day 20 - 对话完善 ✅ 已完成

- [x] ChatMemory 持久化（MychatMemory）
- [x] 会话管理（会话列表接口）
- [x] 对话历史（历史记录接口）

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

### 2026-01-23

- ✅ TradeController 交易控制器
- ✅ TradeService + TradeServiceimpl 买入逻辑
- ✅ 余额校验（BigDecimal.compareTo）
- ✅ 持仓处理（新用户save / 老用户计算均价update）
- ✅ @Transactional 事务管理
- ✅ 空值检查（股票不存在异常）
- ✅ 方法引用语法（Stock::getCode）
- 📌 下一步：Day 12 卖出功能

### 2026-01-25

- ✅ AiConfig 配置（ChatClient + System Prompt）
- ✅ MychatMemory 实现（add/get/clear）
- ✅ ChatController 完成（发消息、历史、会话列表）
- ✅ ChatHistoryMapper 自定义 SQL
- ✅ Builder 模式、Advisor 机制学习
- ✅ Message 接口和实现类理解
- ✅ 流式输出（WebFlux + Flux + SSE）
- ✅ Spring AI 架构理解（OpenAI vs 阿里通义）
- 📌 明天继续：StockTools (Function Calling)

### 2026-01-26

- ✅ UserContents 工具类（ThreadLocal 存储用户信息）
- ✅ AuthInterceptor 添加 afterCompletion 清理 ThreadLocal
- ✅ Controller 改用 UserContents 获取用户ID（不再用 request.getAttribute）
- ✅ StockTools 完成：
  - getStockByName - 根据名称查股票
  - getStockByCode - 根据代码查股票
  - getAllStocks - 查询所有股票
  - recommendStocks - 推荐十只股票
  - buyStockByName - 买入股票（支持名称或代码）
  - sellStockByName - 卖出股票（支持名称或代码）
  - getUserHoldings - 查询用户持仓
- ✅ Function Calling 工具注册到 AiConfig

#### RAG 知识库入库功能 ✅

- ✅ pom.xml 添加 spring-ai-redis-store 依赖
- ✅ VectorStoreConfig.java 配置 Redis VectorStore Bean
  - 属性注入（@Value 读取 Redis 配置）
  - 创建 JedisPooled 连接
  - 创建 RedisVectorStore（面向接口编程）
- ✅ KnowledgeDTO.java 请求参数类
- ✅ IKnowledgeService.java 声明 addKnowledge/deleteKnowledge 方法
- ✅ KnowledgeServiceImpl.java 入库逻辑
  - 数据双写：MySQL + VectorStore
  - Document 对象（content + id + metadata）
  - 删除同步：removeById + vectorStore.delete
- ✅ KnowledgeController.java 管理端接口
  - POST /api/knowledge - 添加知识
  - DELETE /api/knowledge/{id} - 删除知识
  - GET /api/knowledge - 知识列表

#### 今日学习知识点

- Spring AI VectorStore 接口和 RedisVectorStore 实现
- RediSearch 向量索引原理（为什么需要 indexName）
- Document 类结构（id + content + metadata + embedding）
- 面向接口编程 + 依赖注入
- Maven 版本冲突原理和解决方案

#### ⚠️ 待修复

- VectorStoreConfig.java 的 @Value 需要改成 ${spring.data.redis.xxx}
- KnowledgeController.java 的 addKnowledge 需要加 @RequestBody

- 📌 下一步：RagService（RAG 检索）+ 修改 ChatController（对话时使用 RAG）

---

## 🎯 下一步任务

### RAG 知识库

1. **知识库管理**
   - 知识 CRUD 接口
   - 文档分块（chunking）

2. **向量化存储**
   - 配置 VectorStore（SimpleVectorStore 或 Redis）
   - Embedding 向量化

3. **RAG 检索**
   - QuestionAnswerAdvisor 整合到 ChatClient
   - 测试知识问答

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
    ├── SecurityConfig.java     ✅ BCrypt 密码加密配置
    └── RedissonConfig          ✅ (自动装配，无需手动)
├── controller/
│   ├── HoldingController.java  ✅ 持仓查询
│   ├── TradeRecordController.java ✅ 交易记录查询
│   └── ChatController.java     ✅ AI对话（发消息、历史、会话列表）
├── component/
│   └── MychatMemory.java       ✅ ChatMemory 持久化实现
├── common/
│   └── UserContents.java       ✅ ThreadLocal 用户信息传递
├── config/
│   ├── AiConfig.java           ✅ ChatClient 配置（含 Tools 注册）
│   └── StockTools.java         ✅ AI 工具函数（查询、买卖、持仓）
```

---

**加油！🚀**
