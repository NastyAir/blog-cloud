package com.nastryair.project.hotelmanager.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

/**
 * Created by QWL on 2018/4/12.
 */
@Api(value = "/permission", description = "Permission Api Trace Interface")
public interface PermissionApi {
    @ApiOperation(value = "获取单个权限", notes = "", response = ResponseEntity.class, tags = {"permissions"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity info(@ApiParam(value = "permissionId", required = true) String permissionId);

    @ApiOperation(value = "获取权限列表", notes = "", response = ResponseEntity.class, tags = {"permissions"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity list(
            @ApiParam(value = "search", required = false) String search,
            @ApiParam(value = "order", required = false) String order,
            @ApiParam(value = "orderColumn", required = false) String orderColumn,
            @ApiParam(value = "currentPage", required = false) String currentPage,
            @ApiParam(value = "pageSize", required = false) String pageSize
    );

    @ApiOperation(value = "根据角色id获取权限", notes = "", response = ResponseEntity.class, tags = {"permissions"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity getPermByRoleId(@ApiParam(value = "roleId", required = true) String roleId);

    @ApiOperation(value = "根据用户id获取权限", notes = "", response = ResponseEntity.class, tags = {"permissions"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity getPermByUserId(@ApiParam(value = "userId", required = true) String userId);

    @ApiOperation(value = "获取权限名称列表", notes = "", response = ResponseEntity.class, tags = {"permissions"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity nameList();
}
