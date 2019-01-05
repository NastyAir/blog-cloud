package com.nastryair.project.hotelmanager.hotel.controller;

import com.nastryair.project.hotelmanager.hotel.entity.Hotel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "hotel api", tags = "hotel", description = "酒店相关接口")
public interface HotelApi {

    @ApiOperation(value = "获取酒店列表", notes = "获取酒店分页列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity getList(
            @ApiParam(value = "title") String title,
            @ApiParam(value = "priceRange") String priceRange,
            @ApiParam(value = "star") String star,
            @ApiParam(value = "theme") String theme,
            @ApiParam(value = "order") String order,
            @ApiParam(value = "orderColumn") String orderColumn,
            @ApiParam(value = "currentPage") String currentPage,
            @ApiParam(value = "pageSize") String pageSize
    );

    @ApiOperation(value = "获取酒店详情", notes = "获取酒店详情信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity getOne(
            @ApiParam(value = "hotelId") String hotelId
    );

    @ApiOperation(value = "添加酒店", notes = "添加酒店")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    public ResponseEntity add(
            @ApiParam(value = "hotel") Hotel hotel
    );

    @ApiOperation(value = "上传图片文件", notes = "上传酒店图片")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作成功", response = ResponseEntity.class)})
    ResponseEntity uploadFile(
            @ApiParam(value = "file") MultipartFile file);
}
