# RAG 知识库开发计划

> 创建时间：2026-01-26
> 目标：实现 RAG（检索增强生成）知识问答功能

---

## 📌 架构设计

### 存储方案

- **MySQL（knowledge 表）**：存原文（title, content, category）
- **Redis**：存向量数据，key 格式 `vector:knowledge:{id}`

### 核心流程

```
【入库流程】
管理员添加知识 → 文本分块(500字) → 调用 Embedding API → 向量存 Redis + 原文存 MySQL

【检索流程】
用户提问 → 问题向量化 → 遍历 Redis 计算相似度 → 取 Top3 → 拼入 Prompt → AI 回答
```

---

## ✅ 已完成

- [x] Spring AI 依赖配置（spring-ai-alibaba-starter-dashscope）
- [x] 通义千问 API 配置（api-key, chat model, embedding model）
- [x] ChatClient 基础对话配置
- [x] Function Calling（StockTools：查股票、买卖、查持仓）
- [x] ChatController 流式对话接口
- [x] ChatMemory 对话记忆
- [x] Knowledge 实体类 + MySQL 表

---

## 🚀 待开发任务

### Step 1: EmbeddingService（向量化服务）

- [ ] 创建 `service/EmbeddingService.java`
- [ ] 注入 Spring AI 的 `EmbeddingModel`
- [ ] 实现 `float[] embed(String text)` 方法
- [ ] 测试：验证能把文本转成向量

**知识点**：

- EmbeddingModel 是 Spring AI 提供的接口
- 通义千问返回的向量维度是 1536 维
- 一段文字变成一个 float[1536] 数组

---

### Step 2: VectorStoreService（向量存储服务）

- [ ] 创建 `service/VectorStoreService.java`
- [ ] 实现 `save(Long knowledgeId, float[] vector)` - 存入 Redis
- [ ] 实现 `List<Long> searchSimilar(float[] queryVector, int topK)` - 相似度检索
- [ ] 实现余弦相似度计算

**知识点**：

- Redis 存向量用 String 类型，value 是 JSON 格式的 float 数组
- Key 设计：`vector:knowledge:{id}`
- 余弦相似度公式：cos(A,B) = (A·B) / (|A| * |B|)

---

### Step 3: 修改 KnowledgeServiceImpl（入库逻辑）

- [ ] 注入 EmbeddingService 和 VectorStoreService
- [ ] 重写 `save()` 方法：
  - 保存原文到 MySQL
  - 调用 EmbeddingService 向量化
  - 调用 VectorStoreService 存入 Redis
- [ ] 实现文本分块（长文档拆成 500 字的 chunk）

**知识点**：

- 为什么要分块？因为 Embedding 模型有 token 限制，长文档需要拆分
- 一个 Knowledge 可能对应多个向量（每个 chunk 一个）

---

### Step 4: RagService（RAG 检索服务）

- [ ] 创建 `service/RagService.java`
- [ ] 实现 `List<String> search(String query, int topK)` 方法：
  - 问题向量化
  - 调用 VectorStoreService 检索相似向量
  - 根据 ID 从 MySQL 查出原文
  - 返回相关文档内容

---

### Step 5: 修改 AiConfig（添加 RAG Advisor）

- [ ] 创建 RagAdvisor 或使用 QuestionAnswerAdvisor
- [ ] 在 ChatClient 中添加 RAG advisor
- [ ] 对话时自动检索知识库

---

### Step 6: KnowledgeController（管理端接口）

- [ ] 创建 `controller/admin/KnowledgeController.java`
- [ ] 实现 CRUD 接口
- [ ] 添加知识时自动向量化

---

## 📝 application.yml 配置（已完成）

```yaml
spring:
  ai:
    dashscope:
      api-key: sk-14db90eb230a4863968ec1ecb27092e4
      chat:
        options:
          model: qwen-vl-max
      embedding:
        options:
          model: tongyi-embedding-vision-flash  # 向量模型
```

---

## 🗂️ 文件结构（目标）

```
src/main/java/com/investor/
├── service/
│   ├── EmbeddingService.java      ← Step 1
│   ├── VectorStoreService.java    ← Step 2
│   ├── RagService.java            ← Step 4
│   └── impl/
│       └── KnowledgeServiceImpl.java  ← Step 3（修改）
├── config/
│   └── AiConfig.java              ← Step 5（修改）
└── controller/
    └── admin/
        └── KnowledgeController.java  ← Step 6
```

---

## 🎯 当前进度

**正在进行**：Step 1 - EmbeddingService

**下一步**：创建 EmbeddingService.java，调用通义千问的 Embedding API

---

## 💡 面试要点

1. **RAG 是什么？**
   - Retrieval-Augmented Generation，检索增强生成
   - 先检索相关知识，再让 AI 基于知识回答

2. **为什么用 RAG？**
   - 解决 AI 知识过时问题
   - 让 AI 能回答你的私有知识

3. **向量检索原理？**
   - 文本 → Embedding → 高维向量
   - 语义相似的文本，向量距离近
   - 用余弦相似度计算距离

4. **为什么向量存 Redis？**
   - 快！内存级读取
   - 项目规模小，不需要专业向量库
