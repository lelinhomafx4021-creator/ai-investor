package com.investor.service.impl;

import com.investor.entity.po.ChatHistory;
import com.investor.mapper.ChatHistoryMapper;
import com.investor.service.IChatHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 对话记录表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory> implements IChatHistoryService {

}
