package com.investor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.investor.common.Result;
import com.investor.entity.dto.PageQuery;
import com.investor.entity.po.Stock;
import com.investor.service.IStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
@Tag(name = "股票功能模块")
public class StockController {
   private final IStockService stockService;
   
   @Operation(summary="根据股票代码获得股票信息")
   @GetMapping("/stocks")
   public Result getstocks(PageQuery pageQuery){
      Page<Stock> stockPage = new Page<>(pageQuery.getPage(),pageQuery.getSize());
      List<Stock> stocksList = stockService.page(stockPage).getRecords();
      if (pageQuery.getKeyword()==null){
         return Result.success(stocksList);
      }
      Page<Stock> stockList = stockService.lambdaQuery()
              .like(Stock::getCode,pageQuery.getKeyword())
              .or().like(Stock::getName, pageQuery.getKeyword())
              .page(stockPage);
      return Result.success(stockList.getRecords());
   }

   @Operation(summary ="获取单个股票信息")
   @GetMapping("/stocks/{id}")
   public Result getById(@PathVariable Long id){
      Stock stock = stockService.getById(id);
      return  Result.success(stock);
   }

   @Operation(summary = "管理员新增股票信息")
   @PostMapping("/admin/poststocks")
   public Result savestocks(Stock){

   }


}