package com.investor.controller;

import cn.hutool.core.bean.BeanUtil;
import com.investor.common.Result;
import com.investor.entity.dto.LoginRequestDTO;
import com.investor.entity.dto.RegisterRequestDTO;
import com.investor.entity.po.User;
import com.investor.service.IUserService;
import com.investor.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "用户认证,登录注册",description = "用户认证,登录注册")
public class AuthController {
     private final IUserService userService;
     private final BCryptPasswordEncoder bCryptPasswordEncoder;

     @PostMapping("/login")
     @Operation(description = "用户登录",summary = "用户登录")
     public Result<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
          if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isEmpty()) {
               return Result.failMsg("用户名不能为空");
          }
          User user = userService.lambdaQuery().eq(User::getUsername, loginRequestDTO.getUsername()).one();
          if (user == null) {
               return Result.failMsg("没有此用户，请重新输入");
          }
          if (bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
               String token = JwtUtil.createToken(loginRequestDTO.getUsername(), user.getId(), user.getRole());
               return Result.successMsg("登录成功",token);
          } else {
               return Result.failMsg("账号或者密码错误");
          }
     }

     @PostMapping("/register")
     @Operation(summary = "用户注册",description = "用户注册")
     public Result<Void> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
          User one = userService.lambdaQuery().eq(User::getUsername, registerRequestDTO.getUsername()).one();
          if (one == null) { // 用户不存在，可以注册
               User user = BeanUtil.copyProperties(registerRequestDTO, User.class);
               String encode = bCryptPasswordEncoder.encode(user.getPassword());
               user.setPassword(encode);
               userService.save(user);
               return Result.successMsg("注册成功");
          } else { // 用户已存在
               return Result.failMsg("此用户信息已存在");
          }
     }



}