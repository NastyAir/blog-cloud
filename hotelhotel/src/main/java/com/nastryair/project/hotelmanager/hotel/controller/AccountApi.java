package com.nastryair.project.hotelmanager.hotel.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

@Api(value = "用户信息管理", tags = "用户信息管理", description = "用户信息管理")
public interface AccountApi {

    @ApiOperation(value = "登陆", notes = "用户密码登陆")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity login(
            @ApiParam(name = "username", value = "用户名") String userName,
            @ApiParam(name = "password", value = "密码") String password,
            @ApiParam(name = "rememberMe", value = "记住我") boolean rememberMe
    );
}
