package com.investor.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.investor.common.Result;
import com.investor.entity.dto.PageQuery;
import com.investor.entity.dto.StockDTO;
import com.investor.entity.po.Stock;
import com.investor.service.IStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
@Tag(name = "股票功能模块")
public class StockController {
   private final IStockService stockService;
   @Operation(summary = "分页搜索股票")
   @GetMapping("/stocks")
   public Result getStocks(PageQuery pageQuery) {
    String keyword = pageQuery.getKeyword();
    boolean hasKeyword = keyword != null && !keyword.isBlank();

    Page<Stock> page = stockService.lambdaQuery()
            .and(hasKeyword, w -> w
                .like(Stock::getCode, keyword)
                .or()
                .like(Stock::getName, keyword)
            )
            .page(new Page<>(pageQuery.getPage(), pageQuery.getSize()));
    
    return Result.success(page.getRecords());
}
   @Operation(summary ="根据id获取单个股票信息")
   @GetMapping("/stocks/{id}")
   public Result getById(@PathVariable Long id){
      Stock stock = stockService.getById(id);
      return  Result.success(stock);
   }
   @Operation(summary = "根据股票代码获得股票信息")
   @GetMapping("/stocks/code/{code}")
   public Result getByCode(@PathVariable String code) {
      Stock stock = stockService.lambdaQuery().eq(Stock::getCode, code).one();
      return Result.success(stock);
   }

   @Operation(summary = "管理员新增股票信息")
   @PostMapping("/admin/stocks")
   public Result saveStocks( @Valid @RequestBody StockDTO stockDTO){
       Stock stock=BeanUtil.copyProperties(stockDTO,Stock.class);
       stockService.save(stock);
       return Result.success(stock);
   }
    @Operation(summary = "管理员批量新增股票信息")
    @PostMapping("/admin/addbatchstocks")
    public Result saveBatchStocks( @Valid @RequestBody List<StockDTO> stockDTOList){
        List<Stock> stockList=BeanUtil.copyToList(stockDTOList,Stock.class);
        stockService.saveBatch(stockList);
        return Result.success(stockList);
    }

    @Operation(summary = "管理员删除股票信息")
    @DeleteMapping("/admin/stocks")
    public Result deleteStocks(@RequestBody List<Long> ids){
        stockService.removeByIds(ids);
        return Result.success("删除成功");
    }

    @Operation(summary = "管理员更新股票信息")
    @PutMapping("/admin/stocks")
    public Result updateStocks(@Valid @RequestBody StockDTO stockDTO){
        Stock stock=BeanUtil.copyProperties(stockDTO,Stock.class);
        stockService.updateById(stock);
        return Result.success(stock);
    }


}