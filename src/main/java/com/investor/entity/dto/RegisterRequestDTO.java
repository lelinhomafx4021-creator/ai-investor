package com.investor.entity.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名",example = "admin")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码",example = "123456")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码",example = "123456")
    private String confirmPassword; 
    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号",example = "13800138000")
    private String phone;
    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱",example = "test@163.com")
    private String email;
}