package com.investor.mapper;

import com.investor.entity.po.ChatHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 对话记录表 Mapper 接口
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {
    @Select("SELECT session_id FROM chat_history WHERE user_id = #{userId} GROUP BY session_id ORDER BY MAX(create_time) DESC")
    List<String> getHistoryList(Long userId);
}
