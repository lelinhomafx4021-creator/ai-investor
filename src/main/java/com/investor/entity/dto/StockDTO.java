package com.investor.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    @NotBlank(message = "股票代码不能为空")
    private  String code;

    @NotBlank(message="股票名称不能为空")
    private  String name;

    @NotNull(message = "当前价格不能为空")
    private BigDecimal currentPrice;

    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
}
