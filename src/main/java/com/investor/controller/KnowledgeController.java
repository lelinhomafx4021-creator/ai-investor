package com.investor.controller;

import com.investor.common.Result;
import com.investor.entity.dto.KnowledgeDTO;
import com.investor.entity.po.Knowledge;
import com.investor.service.IKnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {
    private final IKnowledgeService knowledgeService;
    @PostMapping
    public Result<Void> addKnowledge(@RequestBody KnowledgeDTO knowledgeDTO){
        knowledgeService.addKnowledge(knowledgeDTO);
        return Result.successMsg("添加资料成功");
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteKnowledge(@PathVariable Long id){
        knowledgeService.deleteKnowledge(id);
        return Result.successMsg("删除资料成功");
    }

    @GetMapping
    public Result<List<Knowledge>> getKnowlegeList(){
        List<Knowledge> list = knowledgeService.list();
        return Result.success(list);
    }


    
}
