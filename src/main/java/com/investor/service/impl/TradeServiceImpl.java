package com.investor.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import com.investor.common.UserContents;
import com.investor.entity.po.TradeRecord;
import com.investor.entity.po.User;

import com.investor.service.*;
import lombok.RequiredArgsConstructor;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investor.entity.dto.TradeDTO;
import com.investor.entity.po.Holding;
import com.investor.entity.po.Stock;

import jakarta.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {
    private final IUserService userService;
    private final IStockService stockService;
    private final ITradeRecordService tradeRecordService;
    private final IHoldingService holdingService;
    private final RedissonClient redissonClient;

    @Transactional
    @Override
    public void buyTrades(TradeDTO tradeDTO) throws InterruptedException {
        Long userId = UserContents.getUserId();
        User user = userService.getById(userId);
        RLock rLock = redissonClient.getLock("lock:trade:" + userId);
        if (rLock.tryLock(3, TimeUnit.SECONDS)) {
            try {
                Stock stock = stockService.lambdaQuery().eq(Stock::getCode, tradeDTO.getCode()).one();
                if (stock == null) {
                    throw new RuntimeException("股票不存在");
                }
                BigDecimal currentPrice = stock.getCurrentPrice();
                BigDecimal quantity = new BigDecimal(tradeDTO.getQuantity());
                if (user.getBalance().compareTo(quantity.multiply(currentPrice)) < 0) {
                    throw new RuntimeException("用户余额不足");
                }
                Holding holding = holdingService.lambdaQuery().eq(Holding::getUserId, userId)
                        .eq(Holding::getStockCode, tradeDTO.getCode()).one();
                if (holding == null) {
                    holding = new Holding().setUserId(userId).setStockCode(stock.getCode())
                            .setQuantity(tradeDTO.getQuantity())
                            .setAvgCost(stock.getCurrentPrice());
                    holdingService.save(holding);
                } else {
                    BigDecimal oldAvg = holding.getAvgCost();
                    BigDecimal oldquantity = new BigDecimal(holding.getQuantity());
                    BigDecimal totalquantity = oldquantity.add(quantity);
                    BigDecimal currentBuyPrice = quantity.multiply(currentPrice);
                    BigDecimal totalAmount = oldAvg.multiply(oldquantity).add(currentBuyPrice);
                    BigDecimal newAvg = totalAmount.divide(totalquantity, 2, RoundingMode.HALF_UP);
                    Integer totalQuantity = (oldquantity.add(quantity)).intValue();
                    holding.setAvgCost(newAvg);
                    holding.setQuantity(totalQuantity);
                    holdingService.updateById(holding);
                }
                user.setBalance(user.getBalance().subtract(quantity.multiply(currentPrice)));
                userService.updateById(user);
                TradeRecord tradeRecord = new TradeRecord().setUserId(userId).setStockCode(stock.getCode())
                        .setType("BUY")
                        .setQuantity(tradeDTO.getQuantity()).setPrice(currentPrice)
                        .setAmount(quantity.multiply(currentPrice))
                        .setRemark("买入股票信息").setStatus("SUCCESS");
                tradeRecordService.save(tradeRecord);

            } finally {
                if (rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
        } else {
            throw new RuntimeException("请稍后再试");
        }

    }

    @Transactional
    @Override
    public void sellTrades(TradeDTO tradeDTO) throws InterruptedException {
        Long userId = UserContents.getUserId();
        String stockCode = tradeDTO.getCode();
        RLock rLock = redissonClient.getLock("lock:trade:" + userId);
        if (rLock.tryLock(3, TimeUnit.SECONDS)) {
            try {
                User user = userService.getById(userId);
                Stock stock = stockService.lambdaQuery().eq(Stock::getCode, stockCode).one();
                if (stock == null) {
                    throw new RuntimeException("此股票不存在");
                }
                Holding holding = holdingService.lambdaQuery().eq(Holding::getUserId, userId)
                        .eq(Holding::getStockCode, stock.getCode()).one();
                if (holding == null) {
                    throw new RuntimeException("你没有持有此股票");
                }
                if (holding.getQuantity() < tradeDTO.getQuantity()) {
                    throw new RuntimeException("你持有的股票数量不足");
                }
                BigDecimal currentPrice = stock.getCurrentPrice();
                BigDecimal quantity = new BigDecimal(tradeDTO.getQuantity());
                BigDecimal sellAmount = currentPrice.multiply(quantity);
                BigDecimal newAmount = user.getBalance().add(sellAmount);
                user.setBalance(newAmount);
                userService.updateById(user);

                BigDecimal oldTotalQty = new BigDecimal(holding.getQuantity());
                BigDecimal newTotalQty = oldTotalQty.subtract(quantity);
                if (newTotalQty.compareTo(BigDecimal.ZERO) <= 0) {
                    holdingService.removeById(holding);
                } else {
                    holding.setQuantity(newTotalQty.intValue());
                    holdingService.updateById(holding);
                }

                TradeRecord tradeRecord = new TradeRecord();
                tradeRecord.setUserId(userId)
                        .setStockCode(stock.getCode())
                        .setType("SELL")
                        .setQuantity(quantity.intValue())
                        .setPrice(stock.getCurrentPrice())
                        .setAmount(sellAmount)
                        .setStatus("SUCCESS")
                        .setRemark("卖出股票");
                tradeRecordService.save(tradeRecord);
            } finally {
                if (rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
        } else {
            throw new RuntimeException("请稍后再试");
        }

    }
}