package com.investor.service;

import com.investor.entity.dto.TradeDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface TradeService {
     void buyTrades(TradeDTO tradeDTO, HttpServletRequest request);
}
