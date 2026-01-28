package com.investor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.investor.common.Result;
import com.investor.entity.dto.PageQuery;
import com.investor.entity.po.User;
import com.investor.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private IUserService userService;

    @GetMapping
    public Result<List<User>> getUserList(PageQuery pageQuery){
        Page<User> page = userService.lambdaQuery().page(new Page<>(pageQuery.getPage(), pageQuery.getSize()));
        return Result.success(page.getRecords());
    }

    @DeleteMapping("/{userid}")
    public  Result<Void> deleteById(@PathVariable List<Long> userids){
        userService.removeByIds(userids);
        return Result.successMsg("删除成功");
    }

    @PutMapping("/ban")
    public Result<Void> banById(@RequestBody List<Long> userids) {
        userService.lambdaUpdate()
                .in(User::getId, userids)
                .set(User::getStatus, "BANNED")
                .update();
        return Result.successMsg("封禁成功");
    }
}
