package com.investor.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Configuration
public class SentinelConfig {
    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){
        return new SentinelResourceAspect();
    }

    @PostConstruct
    public void initRules(){
        initFlowRules();
    }

    public  void initFlowRules(){
        List<FlowRule> list=new ArrayList<>();
        FlowRule flow=new FlowRule();
        flow.setResource("buyStock");
        flow.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flow.setCount(10);
        list.add(flow);
        FlowRule flow1=new FlowRule();
        flow1.setResource("sellStock");
        flow1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flow1.setCount(10);
        list.add(flow1);
        FlowRule flow2=new FlowRule();
        flow2.setResource("chat");
        flow2.setCount(10);
        flow2.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        list.add(flow2);
        FlowRuleManager.loadRules(list);
        log.info("Sentinel 规则加载成功{}",list);

    }
}
