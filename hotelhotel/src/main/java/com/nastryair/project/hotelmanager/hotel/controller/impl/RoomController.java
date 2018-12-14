package com.nastryair.project.hotelmanager.hotel.controller.impl;

import com.nastryair.project.hotelmanager.hotel.common.RestMessage;
import com.nastryair.project.hotelmanager.hotel.controller.RoomApi;
import com.nastryair.project.hotelmanager.hotel.entity.Room;
import com.nastryair.project.hotelmanager.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/room")
public class RoomController implements RoomApi {
    @Autowired
    private RoomService roomService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getList(

            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "ASC") String order,
            @RequestParam(required = false, defaultValue = "id") String orderColumn,
            @RequestParam(required = false, defaultValue = "0") String currentPage,
            @RequestParam(required = false, defaultValue = "10") String pageSize) {
        RestMessage restMessage = roomService.getList(name, order, orderColumn, currentPage, pageSize);
        return ResponseEntity.ok(restMessage);
    }

    @Override
    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET)
    public ResponseEntity getOne(@PathVariable String roomId) {
        RestMessage restMessage = roomService.getOne(roomId);
        return ResponseEntity.ok(restMessage);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Room room) {
        RestMessage restMessage = roomService.add(room);
        return ResponseEntity.ok(restMessage);
    }

    @Override
    @RequestMapping(value = "/{roomId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String roomId) {
        RestMessage restMessage = roomService.delete(roomId);
        return ResponseEntity.ok(restMessage);
    }

    @Override
    @RequestMapping(value = "/{roomId}", method = RequestMethod.PUT)
    public ResponseEntity edit(
            @PathVariable String roomId,
            @RequestBody Room room) {
        RestMessage restMessage = roomService.edit(roomId, room);
        return ResponseEntity.ok(restMessage);
    }
}
