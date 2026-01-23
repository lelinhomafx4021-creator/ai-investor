package com.investor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class TradeDTO {
    @Schema(description = "买入股票的代码")
    @NotBlank(message = "买入股票的代码不能为空")
    private String code;
    @Schema(description = "买入股票的金额")
    @NotBlank(message = "买入股票数量不为空")
    private Integer quantity;

}
