package com.investor.service.impl;

import com.investor.entity.po.Holding;
import com.investor.mapper.HoldingMapper;
import com.investor.service.IHoldingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 持仓表 服务实现类
 * </p>
 *
 * @author yjx
 * @since 2026-01-18
 */
@Service
public class HoldingServiceImpl extends ServiceImpl<HoldingMapper, Holding> implements IHoldingService {

}
