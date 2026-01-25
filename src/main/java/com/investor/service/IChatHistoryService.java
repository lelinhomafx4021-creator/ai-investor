package com.investor.service;

import com.investor.entity.po.ChatHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 对话记录表 服务类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
public interface IChatHistoryService extends IService<ChatHistory> {

    List<String> getHistoryList(Long userId);
}
