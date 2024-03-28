package org.lin.controller;

import io.swagger.annotations.Api;
import lombok.Data;
import org.lin.entity.bo.User;
import org.lin.entity.vo.R;
import org.lin.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author LvWei
 * @Date 2024/3/14 15:27
 */
@RestController
@RequestMapping("/user")
@Api
public class UserController {

    @Resource
    private IUserService userService;


    @PostMapping("/login")
    public R<String> login(@RequestBody @Valid User user){
        return new R<String>().data(userService.login(user));
    }


}
