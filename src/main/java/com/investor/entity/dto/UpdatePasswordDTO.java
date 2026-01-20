package com.investor.entity.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdatePasswordDTO {
    @NotNull(message = "旧密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String newPassword;
    @NotNull(message = "确认密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String confirmPassword;
}
