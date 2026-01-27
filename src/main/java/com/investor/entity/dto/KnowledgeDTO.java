package com.investor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class KnowledgeDTO {
    @NotBlank(message = "标题不能为空")
    @Schema(description = "标题",example = "什么是市盈率")
    private String title;
    @NotBlank(message = "内容不能为空")
    @Schema(description = "内容",example = "市盈率是股价与每股收益的比值...")
    private String content;
    @NotBlank(message = "分类不能为空")
    @Schema(description = "分类",example = "教程")
    private String category;
}