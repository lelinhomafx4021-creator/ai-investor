package com.investor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.investor.entity.dto.KnowledgeDTO;
import com.investor.entity.po.Knowledge;
import com.investor.mapper.KnowledgeMapper;
import com.investor.service.IKnowledgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 知识库表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@RequiredArgsConstructor
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements IKnowledgeService {
    private final VectorStore vectorStore;


    @Override
    public void addKnowledge(KnowledgeDTO knowledgeDTO) {
        Knowledge knowledge=new Knowledge();
        BeanUtil.copyProperties(knowledgeDTO,knowledge);
        save(knowledge);
        Document doc=new Document(
              knowledge.getId().toString(),
                knowledgeDTO.getContent(),
                Map.of(
                    "title",knowledgeDTO.getTitle(),
                    "category",knowledgeDTO.getCategory()!=null?knowledgeDTO.getCategory():"")
        );
        vectorStore.add(List.of(doc));



    }
    @Override
    public void deleteKnowledge(Long id) {
        removeById(id);
        vectorStore.delete(List.of(id.toString()));

    }

    @Override
    public List<Document> searchKnowledges(String message,int topx) {
        return  vectorStore.similaritySearch(SearchRequest.builder().query(message).topK(topx).similarityThreshold(0.7).build());
    }
}


