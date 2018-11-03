package com.nastryair.project.hotelmanager.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SystemController class
 *
 * @author QWL
 * @date 2018/7/11
 */
@Api(value = "/system", description = "system Api Trace Interface")
public interface SystemApi {

    @ApiOperation(value = "登录", notes = "", response = ResponseEntity.class, tags = {"system"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity authentication(
            @ApiParam(value = "username", required = true) String username,
            @ApiParam(value = "password", required = true) String password
    );
//
//    @ApiOperation(value = "第三方登录认证", response = ResponseEntity.class, tags = {"system"})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
//    void authentication(@ApiParam(value = "request", required = true) HttpServletRequest request,
//                        @ApiParam(value = "response", required = true) HttpServletResponse response);
//
//    @ApiOperation(value = "第三方登录", response = ResponseEntity.class, tags = {"system"})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
//    void authLogin(@ApiParam(value = "request", required = true) HttpServletRequest request,
//                   @ApiParam(value = "response", required = true) HttpServletResponse response) throws IOException;

    @ApiOperation(value = "权限验证", notes = "", response = ResponseEntity.class, tags = {"system"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity authorization(
            @ApiParam(value = "code", required = true) String code,
            @ApiParam(value = "param", required = false) String param,
            @ApiParam(value = "token", required = true) String token
    );
}
