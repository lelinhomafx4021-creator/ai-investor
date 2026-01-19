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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
     private final IUserService userService;
     @PostMapping("/login")
     public Result<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
          if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isEmpty()) {
               return Result.fail("用户名不能为空");
          }
          User user = userService.lambdaQuery().eq(User::getUsername, loginRequestDTO.getUsername()).one();
          if (user==null){
               return Result.fail("没有此用户，请重新输入");
          }
          if (user.getPassword().equals(loginRequestDTO.getPassword())){
               String token = JwtUtil.createToken(loginRequestDTO.getUsername(), user.getId(), user.getRole());
               return Result.success(token);
          }else{
               return Result.fail("账号或者密码错误");
          }
     }

     @PostMapping("/register")
     public Result<Void> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){
          User user = BeanUtil.copyProperties(registerRequestDTO, User.class);
          userService.save(user);
          return Result.success("注册成功");
     }


} 