package com.investor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.investor.common.Result;
import com.investor.common.UserContents;
import com.investor.entity.dto.PageQuery;
import com.investor.entity.po.TradeRecord;
import com.investor.service.ITradeRecordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TradeRecordController {

    private final ITradeRecordService tradeRecordService;
    @GetMapping("/traderecords")
    public Result<List<TradeRecord>> getTradeRecords(PageQuery pageQuery){
        Long userId = UserContents.getUserId();
        Page<TradeRecord> page = tradeRecordService.lambdaQuery().eq(TradeRecord::getUserId,userId).orderByDesc(TradeRecord::getCreateTime).page(new Page(pageQuery.getPage(), pageQuery.getSize()));
        return Result.success(page.getRecords());

    }
}
