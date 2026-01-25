package com.investor.component;

import com.investor.service.IChatHistoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.investor.entity.po.ChatHistory;

@Component
@RequiredArgsConstructor
public class MychatMemory implements ChatMemory {
  private final IChatHistoryService chatHistoryService;

  @Override
  public void add(String conversationId, List<Message> messages) {
    String parts[] = conversationId.split("_");
    Long userId = Long.parseLong(parts[0]);
    String sessionId = parts[1];
    for (Message message : messages) {
      ChatHistory chatHistory = new ChatHistory();
      chatHistory.setUserId(userId)
          .setSessionId(sessionId)
          .setRole(message.getMessageType().name().toLowerCase())
          .setContent(message.getText());
      chatHistoryService.save(chatHistory);
    }

  }

  @Override
  public List<Message> get(String conversationId) {
    String parts[] = conversationId.split("_");
    Long userId = Long.parseLong(parts[0]);
    String sessionId = parts[1];
    List<ChatHistory> list = chatHistoryService.lambdaQuery().eq(ChatHistory::getUserId, userId)
        .eq(ChatHistory::getSessionId, sessionId).orderByDesc(ChatHistory::getCreateTime).last("limit 20").list();
    Collections.reverse(list);
    return list.stream().map(this::toMessage).collect(Collectors.toList());

  }

  public Message toMessage(ChatHistory chatHistory) {
    String role = chatHistory.getRole();
    String content = chatHistory.getContent();
    if ("user".equals(role)) {
      return new UserMessage(content);
    } else {
      return new AssistantMessage(content);
    }
  }

  @Override
  public void clear(String conversationId) {
    String parts[] = conversationId.split("_");
    Long userId = Long.parseLong(parts[0]);
    String sessionId = parts[1];
    chatHistoryService.lambdaUpdate().eq(ChatHistory::getUserId, userId).eq(ChatHistory::getSessionId, sessionId)
        .remove();
   
  }
}
