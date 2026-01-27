package com.investor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.ToolParam;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeDTO {
    @Schema(description = "买入股票的代码")
    @NotBlank(message = "买入股票的代码不能为空")
    @ToolParam(description = "需要买入的股票代码")
    private String code;


    @Schema(description = "买入股票的数量")
    @NotNull(message = "买入股票数量不为空")
    @ToolParam(description = "要买入的股票数量")
    private Integer quantity;

}
