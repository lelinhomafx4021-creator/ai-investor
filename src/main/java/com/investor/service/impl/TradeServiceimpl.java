package com.investor.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.investor.entity.po.TradeRecord;
import com.investor.entity.po.User;

import com.investor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.investor.entity.dto.TradeDTO;
import com.investor.entity.po.Holding;
import com.investor.entity.po.Stock;

import jakarta.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class TradeServiceimpl implements TradeService {
    private final IUserService userService;
    private final IStockService stockService;
    private final ITradeRecordService tradeRecordService;
    private final IHoldingService holdingService;

    @Transactional
    @Override
    public void buyTrades(TradeDTO tradeDTO, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
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
            holding = new Holding().setUserId(userId).setStockCode(stock.getCode()).setQuantity(tradeDTO.getQuantity())
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
        TradeRecord tradeRecord = new TradeRecord().setUserId(userId).setStockCode(stock.getCode()).setType("BUY")
                .setQuantity(tradeDTO.getQuantity()).setPrice(currentPrice)
                .setAmount(quantity.multiply(currentPrice))
                .setRemark("买入股票信息");
        tradeRecordService.save(tradeRecord);

    }
}