package com.investor.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.investor.common.Result;
import com.investor.common.SentinelBlockHandle;
import com.investor.common.UserContents;
import com.investor.entity.dto.ChatDTO;
import com.investor.entity.po.ChatHistory;
import com.investor.service.IChatHistoryService;
import com.investor.service.IKnowledgeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/api/chat")
@RestController

@RequiredArgsConstructor
public class ChatController {
    private  final  ChatMemory chatMemory;
    private  final ChatClient chatClient;
    private  final IChatHistoryService chatHistoryService;
    private final IKnowledgeService knowledgeService;
    @SentinelResource(value = "chat",
            blockHandlerClass = SentinelBlockHandle.class,
            blockHandler = "chathandle")
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@Valid ChatDTO chatDTO){
        Long userId = UserContents.getUserId();
        String message=chatDTO.getMessage();
        String conversationId=userId.toString()+"_"+chatDTO.getConversationId();
        List<Document> docs=knowledgeService.searchKnowledges(chatDTO.getMessage(),5);
        // 2. 拼接知识内容
        String context = docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n---\n"));
        message="用户问的的问题 \n"+message+"\n请参考以下资料回答：\n"+context;
         return   chatClient.prompt(message)
         .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
         .conversationId(conversationId)
         .build())
         .stream()
         .content();
    }

    @GetMapping("/history/{seesion_id}")
    public   Result<List<ChatHistory>> getHostoryById(@PathVariable String seesion_id){
        Long userId = UserContents.getUserId();
        List<ChatHistory> list = chatHistoryService.lambdaQuery().eq(ChatHistory::getUserId, userId).eq(ChatHistory::getSessionId, seesion_id).orderByAsc(ChatHistory::getCreateTime).list();
        return  Result.success(list);
    }
    @GetMapping("/historys")
    public  Result<List<String>> getHistroyList(){
        Long userId = UserContents.getUserId();
        return Result.success(chatHistoryService.getHistoryList(userId));
    }
    
}
