package com.investor.service;

import com.investor.entity.dto.KnowledgeDTO;
import com.investor.entity.po.Knowledge;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.ai.document.Document;

import java.util.List;

/**
 * <p>
 * 知识库表 服务类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
public interface IKnowledgeService extends IService<Knowledge> {
    void addKnowledge(KnowledgeDTO knowledgeDTO) ;

    void  deleteKnowledge(Long id);

    List<Document> searchKnowledges(String message,int topx);

}
