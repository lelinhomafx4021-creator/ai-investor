package com.investor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    @NotNull(message = "消息不能为空")
    @Schema(description = "消息",example = "你好")

    private String message;
    @NotBlank(message = "会话ID不能为空")
    @Schema(description = "会话ID",example = "1")
    private String conversationId;
}
