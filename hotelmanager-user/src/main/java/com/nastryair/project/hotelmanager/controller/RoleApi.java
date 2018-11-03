package com.nastryair.project.hotelmanager.controller;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

/**
 * Created by QWL on 2018/4/12.
 */
@Api(value = "/role", description = "Role Api Trace Interface")
public interface RoleApi {
    @ApiOperation(value = "获取单个角色", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity info(@ApiParam(value = "roleId", required = true) String roleId);

    @ApiOperation(value = "获取角色列表", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity list(
            @ApiParam(value = "search", required = false) String search,
            @ApiParam(value = "order", required = false) String order,
            @ApiParam(value = "orderColumn", required = false) String orderColumn,
            @ApiParam(value = "currentPage", required = false) String currentPage,
            @ApiParam(value = "pageSize", required = false) String pageSize
    );

    @ApiOperation(value = "创建角色", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity create(@ApiParam(value = "role", required = true) String role);

    @ApiOperation(value = "更新角色", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity alter(@ApiParam(value = "role", required = true) String role);


    @ApiOperation(value = "删除角色", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity drop(@ApiParam(value = "roleId", required = true) String roleId);

    @ApiOperation(value = "批量修改角色权限关联", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity addRolePerms(@ApiParam(value = "json", required = true) String json);

    @ApiOperation(value = "获取角色名称列表", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity nameList();

    @ApiOperation(value = "根据用户id获取角色", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity getByUserId(@ApiParam(value = "userId", required = true) String userId);

    @ApiOperation(value = "根据角色名称获取角色", notes = "", response = ResponseEntity.class, tags = {"role"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity getByRoleName(@ApiParam(value = "userId", required = true) String userId);
}
