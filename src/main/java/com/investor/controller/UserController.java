package com.investor.controller;

import cn.hutool.core.bean.BeanUtil;
import com.investor.common.UserContents;
import com.investor.entity.dto.UpdatePasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.investor.common.Result;
import com.investor.config.AuthInterceptor;
import com.investor.entity.po.User;
import com.investor.entity.vo.UserVO;
import com.investor.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "用户功能模块")
public class UserController {
    private final IUserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public Result<UserVO> getUserInfo() {
        Long userId = UserContents.getUserId();
        String username=UserContents.getUserName();
        if (username== null) {
            return Result.failMsg("请先登录");
        }
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        if (user != null) {
            UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
            return Result.success(userVO);
        }
        return Result.failMsg("信息获取失败");
    }
    @Operation(summary = "修改密码")
    @PostMapping("/updatePassword")
    public Result<Void> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        Long userId = UserContents.getUserId();
        User user = userService.getById(userId);
        if (!bCryptPasswordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            return Result.failMsg("原密码错误");
        } else {
            if (updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword()));
                userService.updateById(user);
                return Result.successMsg("密码修改成功");
            } else {
                return Result.failMsg("两次密码不一致");
            }
        }

    }
    @Operation(summary = "修改个人资料")
    @PutMapping("/ban")
public Result<Void> banById(@RequestBody List<Long> userids) {
    userService.lambdaUpdate()
        .in(User::getId, userids)
        .set(User::getStatus, "BANNED")
        .update();
    return Result.successMsg("封禁成功");
}

    

}