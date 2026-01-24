package com.investor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.investor.common.Result;
import com.investor.entity.dto.PageQuery;
import com.investor.entity.dto.TradeDTO;
import com.investor.service.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TradeController{
     private TradeService tradeService;



     @PostMapping("/trades/buy")
    public Result buyTrades(@Valid @RequestBody TradeDTO tradeDTO, HttpServletRequest request){
          tradeService.buyTrades(tradeDTO,request);
          return Result.successMsg("买入成功");
    }

    @PostMapping("/trades/sell")
    public  Result<Void> sellTrades(@Valid @RequestBody  TradeDTO tradeDTO, HttpServletRequest request){
         tradeService.sellTrades(tradeDTO,request);
         return  Result.successMsg("卖出成功");

    }
    
}