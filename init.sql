DROP DATABASE IF EXISTS ai_investor;
CREATE DATABASE ai_investor;
use ai_investor;
CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '用户密码',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像',
    balance DECIMAL(12, 2) DEFAULT 100000.00 COMMENT '账户余额',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '角色',
    status VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态:NORMAL/BANNED',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) COMMENT '用户表';
CREATE TABLE stock (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    code VARCHAR(20) NOT NULL COMMENT '股票代码',
    name VARCHAR(50) NOT NULL COMMENT '股票名称',
    current_price DECIMAL(12, 2) NOT NULL COMMENT '当前价格',
    open_price DECIMAL(12, 2) COMMENT '今日开盘价',
    high_price DECIMAL(12, 2) COMMENT '今日最高价',
    low_price DECIMAL(12, 2) COMMENT '今日最低价',
    close_price DECIMAL(12, 2) COMMENT '昨日收盘价',
    volume BIGINT DEFAULT 0 COMMENT '成交量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code)
) COMMENT '股票表';
CREATE TABLE holding (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    stock_code VARCHAR(20) NOT NULL COMMENT '股票代码',
    quantity INT NOT NULL DEFAULT 0 COMMENT '持有数量',
    avg_cost DECIMAL(10, 2) NOT NULL COMMENT '平均成本价',
    frozen_quantity INT DEFAULT 0 COMMENT '冻结数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_stock (user_id, stock_code),
    KEY idx_user_id (user_id)
) COMMENT '持仓表';
CREATE TABLE trade_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    stock_code VARCHAR(20) NOT NULL COMMENT '股票代码',
    type VARCHAR(10) NOT NULL COMMENT '交易类型:BUY/SELL',
    quantity INT NOT NULL COMMENT '交易数量',
    price DECIMAL(10, 2) NOT NULL COMMENT '成交价格',
    amount DECIMAL(12, 2) NOT NULL COMMENT '成交金额',
    status VARCHAR(20) DEFAULT 'SUCCESS' COMMENT '状态:SUCCESS/FAILED/PENDING',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_create_time (create_time)
) COMMENT '交易记录表';
CREATE TABLE knowledge (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    category VARCHAR(50) COMMENT '分类:教程/策略/新闻/风险',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_category (category)
) COMMENT '知识库表';
CREATE TABLE chat_history (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    session_id VARCHAR(50) NOT NULL COMMENT '会话ID',
    role VARCHAR(20) NOT NULL COMMENT '角色:user/assistant',
    content TEXT NOT NULL COMMENT '消息内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    PRIMARY KEY (id),
    KEY idx_user_session (user_id, session_id),
    KEY idx_create_time (create_time)
) COMMENT '对话记录表';
CREATE TABLE notification (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    type VARCHAR(20) NOT NULL COMMENT '类型:TRADE/PRICE/SYSTEM',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content VARCHAR(500) COMMENT '内容',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读:0未读/1已读',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_is_read (is_read)
) COMMENT '消息通知表';