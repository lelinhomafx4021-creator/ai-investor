package com.investor.task;

import cn.hutool.json.JSONUtil;
import com.investor.entity.po.Stock;
import com.investor.service.IStockService;
import com.investor.websocket.MarketWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MarketPriceTask {
    private final MarketWebSocketHandler marketWebSocketHandler;
    private final IStockService stockService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedRate = 5000)
    public void updatePrices() {
        List<Stock> stockList = getStocksFromcaches();
        if (stockList == null) {
            return;
        }
        for (Stock stock : stockList) {
            double rate = (Math.random() - 0.5) * 0.04;
            BigDecimal newPrice = stock.getCurrentPrice().multiply(new BigDecimal(1 + rate)).setScale(2,RoundingMode.HALF_UP);
            stock.setCurrentPrice(newPrice);
            redisTemplate.opsForValue().set("stocks:" + stock.getCode(), stock);
        }
        redisTemplate.opsForValue().set("stocks:list", stockList);
        log.info("redis更新股票价格");
        marketWebSocketHandler.broadcast(JSONUtil.toJsonStr(stockList));
        stockService.updateBatchById(stockList);
        log.info("数据库更新股票价格");

    }

    public List<Stock> getStocksFromcaches() {
        Object cached = redisTemplate.opsForValue().get("stocks:list");
        if (cached != null) {
            return (List<Stock>) cached;
        }
        List<Stock> stockList = stockService.list();
        redisTemplate.opsForValue().set("stocks:list", stockList);
        log.info("缓存没有查到，从数据库里面查找");
        return stockList;
    }
}
