package com.nastryair.project.hotelmanager.hotel.controller;

import com.nastryair.project.hotelmanager.hotel.entity.Room;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

@Api(value = "room api", tags = "room", description = "房间相关接口")
public interface RoomApi {

    @ApiOperation(value = "获取房间列表", notes = "获取房间分页列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity getList(
//            @ApiParam(value = "title") String title,
//            @ApiParam(value = "priceRange") String priceRange,
//            @ApiParam(value = "star") String star,
            @ApiParam(value = "name") String name,
            @ApiParam(value = "order") String order,
            @ApiParam(value = "orderColumn") String orderColumn,
            @ApiParam(value = "currentPage") String currentPage,
            @ApiParam(value = "pageSize") String pageSize
    );

    @ApiOperation(value = "获取房间详情", notes = "获取房间详情信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity getOne(
            @ApiParam(value = "roomId") String roomId
    );

    @ApiOperation(value = "添加房间", notes = "添加房间")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity add(
            @ApiParam(value = "room") Room room
    );

    @ApiOperation(value = "删除房间", notes = "删除房间")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity delete(
            @ApiParam(value = "roomId") String roomId
    );

    @ApiOperation(value = "修改房间", notes = "修改房间")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity edit(
            @ApiParam(value = "roomId") String roomId,
            @ApiParam(value = "room") Room room
    );
}
