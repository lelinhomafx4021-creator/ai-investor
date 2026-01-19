package com.investor.service.impl;

import com.investor.entity.po.Knowledge;
import com.investor.mapper.KnowledgeMapper;
import com.investor.service.IKnowledgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 知识库表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements IKnowledgeService {

}
