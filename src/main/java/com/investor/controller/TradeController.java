package com.investor.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.investor.common.Result;
import com.investor.common.SentinelBlockHandle;
import com.investor.common.UserContents;
import com.investor.entity.dto.TradeDTO;
import com.investor.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TradeController{
     private TradeService tradeService;
     @SentinelResource(value = "buyStock",
     blockHandlerClass = SentinelBlockHandle.class,
     blockHandler = "buyhandle")
     @PostMapping("/trades/buy")
    public Result buyTrades(@Valid @RequestBody TradeDTO tradeDTO) throws InterruptedException {
         Long userId = UserContents.getUserId();
          tradeService.buyTrades(tradeDTO);
          return Result.successMsg("买入成功");
    }
    @SentinelResource(value = "sellStock",
            blockHandlerClass = SentinelBlockHandle.class,
            blockHandler = "sellhandle")
    @PostMapping("/trades/sell")
    public  Result<Void> sellTrades(@Valid @RequestBody TradeDTO tradeDTO) throws InterruptedException {
         tradeService.sellTrades(tradeDTO);
         return  Result.successMsg("卖出成功");

    }
    
}