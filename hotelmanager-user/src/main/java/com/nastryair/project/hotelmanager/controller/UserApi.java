package com.nastryair.project.hotelmanager.controller;

import com.nastryair.project.hotelmanager.entity.User;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author zhh [zhouhaihong@youedata.com].
 * @Date 2018/4/8
 */
@Api(value = "/user", description = "user Api Trace Interface")
public interface UserApi {

    @ApiOperation(value = "创建用户", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity create(@ApiParam(value = "user", required = true) User user);

    @ApiOperation(value = "用户关联角色", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity createRole(@ApiParam(value = "userRole", required = true) String userRole);

    @ApiOperation(value = "删除用户", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity drop(@ApiParam(value = "userId", required = true) String userId);

    @ApiOperation(value = "更新用户信息", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity alter(@ApiParam(value = "user", required = true) String user);

    @ApiOperation(value = "更新用户密码", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity alterPassword(@ApiParam(value = "user", required = true) String user);

    @ApiOperation(value = "更新用户头像", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity alterAvatar(
            @ApiParam(value = "multipartFile", required = true) MultipartFile multipartFile,
            @ApiParam(value = "userId", required = true) String userId);

    @ApiOperation(value = "获取用户头像", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    void getAvatar(
            @ApiParam(value = "userId", required = true) String userId,
            HttpServletResponse response
    );

    @ApiOperation(value = "获取用户列表", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity list(
            @ApiParam(value = "userName", required = false) String userName,
            @ApiParam(value = "order", required = false) String order,
            @ApiParam(value = "orderColumn", required = false) String orderColumn,
            @ApiParam(value = "currentPage", required = false) String currentPage,
            @ApiParam(value = "pageSize", required = false) String pageSize
    );

    @ApiOperation(value = "根据用户id获取用户详细信息", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity userInfo(@ApiParam(value = "userId", required = true) String userId);

    @ApiOperation(value = "根据角色id获取用户列表", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity userInfoByRoleId(@ApiParam(value = "roleId", required = true) String roleId);

    @ApiOperation(value = "根据角色名称获取用户列表", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity userInfoByRoleName(@ApiParam(value = "roleId", required = true) String roleId);

    @ApiOperation(value = "根据用户名获取用户详细信息", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity findByName(@ApiParam(value = "username", required = true) String username);

    @ApiOperation(value = "获取用户角色列表", notes = "", response = ResponseEntity.class, tags = {"user"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity roleInfo(@ApiParam(value = "userId", required = true) String userId);

//    @ApiOperation(value = "根据AccessId获取用户", notes = "", response = ResponseEntity.class, tags = {"user"})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
//    ResponseEntity findByAccessId(
//            @ApiParam(value = "accessId", required = true) String accessId
//    );
}
