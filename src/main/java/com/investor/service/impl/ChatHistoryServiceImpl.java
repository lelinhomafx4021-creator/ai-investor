package com.investor.service.impl;

import com.investor.entity.po.ChatHistory;
import com.investor.mapper.ChatHistoryMapper;
import com.investor.service.IChatHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 对话记录表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@RequiredArgsConstructor
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory> implements IChatHistoryService {
    private  final  ChatHistoryMapper chatHistoryMapper;

    @Override
    public List<String> getHistoryList(Long userId) {
        return chatHistoryMapper.getHistoryList(userId);

    }
}
