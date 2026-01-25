package com.investor.controller;

import com.investor.common.Result;
import com.investor.entity.dto.ChatDTO;
import com.investor.entity.po.ChatHistory;
import com.investor.service.IChatHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/chat")
@RestController

@RequiredArgsConstructor
public class ChatController {
    private  final  ChatMemory chatMemory;
    private  final ChatClient chatClient;
    private  final IChatHistoryService chatHistoryService;
    @PostMapping
    public Result<String> chat(@Valid @RequestBody ChatDTO chatDTO, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        String message=chatDTO.getMessage();
        String conversationId=userId.toString()+"_"+chatDTO.getConversationId();
        String response = chatClient.prompt(message).advisors(MessageChatMemoryAdvisor.builder(chatMemory).conversationId(conversationId).build()).call().content();
         return  Result.success(response);
    }

    @GetMapping("/history/{seesion_id}")
    public   Result<List<ChatHistory>> getHostoryById(HttpServletRequest request,@PathVariable String seesion_id){
        Long userId=  (Long)request.getAttribute("userId");
        List<ChatHistory> list = chatHistoryService.lambdaQuery().eq(ChatHistory::getUserId, userId).eq(ChatHistory::getSessionId, seesion_id).orderByAsc(ChatHistory::getCreateTime).list();
        return  Result.success(list);
    }
    @GetMapping("/historys")
    public  Result<List<String>> getHistroyList(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(chatHistoryService.getHistoryList(userId));
    }
    
}
