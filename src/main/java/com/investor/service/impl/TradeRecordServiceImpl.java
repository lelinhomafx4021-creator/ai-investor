package com.investor.service.impl;

import com.investor.entity.po.TradeRecord;
import com.investor.mapper.TradeRecordMapper;
import com.investor.service.ITradeRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易记录表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Service
public class TradeRecordServiceImpl extends ServiceImpl<TradeRecordMapper, TradeRecord> implements ITradeRecordService {

}
