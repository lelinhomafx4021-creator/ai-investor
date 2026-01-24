package com.investor.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class HoldingVO {
    private Long id;
    private String stockCode;
    private String stockName;
    private Integer quantity;
    private BigDecimal avgCost;
    private BigDecimal currentPrice;

    public BigDecimal getTotalCost() {
        if (avgCost == null || quantity == null) return BigDecimal.ZERO;
        return avgCost.multiply(new BigDecimal(quantity));
    }

    public BigDecimal getMarketValue() {
        if (currentPrice == null || quantity == null) return BigDecimal.ZERO;
        return currentPrice.multiply(new BigDecimal(quantity));
    }

    public BigDecimal getProfit() {
        return getMarketValue().subtract(getTotalCost());
    }

    public BigDecimal getProfitRate() {
        if (getTotalCost().compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return getProfit()
                .divide(getTotalCost(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }
}