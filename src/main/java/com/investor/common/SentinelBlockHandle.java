package com.investor.common;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import reactor.core.publisher.Flux;

public class SentinelBlockHandle {
    public static Result<Void> buyhandle(BlockException ex){
        return Result.failMsg("太多人买入，请稍后再试");
    }
     public static Result<Void> sellhandle(BlockException ex){
        return Result.failMsg("太多人卖出，请稍后再试");
    }
     public static Flux<String> chathandle(BlockException ex){
        return Flux.just("与AI聊天人数过多，请稍后再试");
    }
}