package com.investor.service.impl;

import com.investor.entity.po.Stock;
import com.investor.mapper.StockMapper;
import com.investor.service.IStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
