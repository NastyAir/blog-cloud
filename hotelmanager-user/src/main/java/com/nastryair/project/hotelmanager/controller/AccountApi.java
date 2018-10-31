package com.nastryair.project.hotelmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Api("用户信息管理")
public interface AccountApi {

    @ApiOperation(value = "登陆", notes = "用户密码登陆")
    public ResponseEntity login(
            @ApiParam(value = "username", name = "用户名") String userName,
            @ApiParam(value = "password", name = "密码") String password,
            @ApiParam(value = "rememberMe", name = "记住我") boolean rememberMe
    );
}
