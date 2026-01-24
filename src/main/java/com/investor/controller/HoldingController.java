package com.investor.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.investor.common.Result;
import com.investor.entity.dto.PageQuery;
import com.investor.entity.po.Holding;
import com.investor.entity.po.Stock;
import com.investor.entity.vo.HoldingVO;
import com.investor.service.IHoldingService;
import com.investor.service.IStockService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HoldingController {
    private final IHoldingService holdingService;
    private final IStockService stockService;
    @GetMapping("/holdings")
    public Result<List<HoldingVO>> getHoldingList(HttpServletRequest request, PageQuery pageQuery){
        Long userId = (Long) request.getAttribute("userId");
        Page page=new Page(pageQuery.getPage(), pageQuery.getSize());
        Page holdingsPage = holdingService.lambdaQuery().eq(Holding::getUserId, userId).page(page);
        if(holdingsPage.getRecords().isEmpty()){
            return Result.success(new ArrayList<>());
        }
        List<Holding> holdingList= holdingsPage.getRecords();
        List<HoldingVO> holdingVOList= BeanUtil.copyToList(holdingList,HoldingVO.class);
        for (HoldingVO holdingVO:holdingVOList){
            Stock stock=stockService.lambdaQuery().eq(Stock::getCode,holdingVO.getStockCode()).one();
            holdingVO.setStockName(stock.getName());
            holdingVO.setCurrentPrice(stock.getCurrentPrice());
        }
        return  Result.success(holdingVOList);
    }
}
// Page<HoldingVO> resultPage = holdingsPage.convert(holding -> {
//     HoldingVO vo = BeanUtil.copyProperties(holding, HoldingVO.class);
//     Stock stock = stockService.lambdaQuery()
//         .eq(Stock::getCode, holding.getStockCode()).one();
//     if (stock != null) {
//         vo.setStockName(stock.getName());
//         vo.setCurrentPrice(stock.getCurrentPrice());
//     }
//     return vo;
// });