package com.nastryair.project.hotelmanager.hotel.service;

import com.nastryair.project.hotelmanager.hotel.common.RestMessage;
import com.nastryair.project.hotelmanager.hotel.common.contant.CodeConstant;
import com.nastryair.project.hotelmanager.hotel.entity.Hotel;
import com.nastryair.project.hotelmanager.hotel.entity.Room;
import com.nastryair.project.hotelmanager.hotel.repository.RoomRepository;
import com.nastryair.project.hotelmanager.hotel.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RestMessage getList(String name, String order, String orderColumn, String currentPage, String pageSize) {
        RestMessage message = new RestMessage();

        Sort.Direction direction = "ASC".equals(order.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(
                Integer.valueOf(currentPage),
                Integer.valueOf(pageSize),
                Sort.by(direction, orderColumn));
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Room room = new Room();
        room.setName(name);
        Page page = roomRepository.findAll(Example.of(room, matcher), pageable);
        message.setData(page);
        message.setCode(CodeConstant.SUCCESS.getCode());
        return message;
    }

    public RestMessage getOne(String roomId) {
        RestMessage message = new RestMessage();
        Room room = roomRepository.findByRoomId(roomId);
        if (room != null) {
            message.setData(room);
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        }
        return message;

    }

    public RestMessage add(Room room) {
        RestMessage message = new RestMessage();
        Room sameRoom = roomRepository.findByName(room.getName());
        if (sameRoom == null) {
            room.setRoomId(UUID.randomUUID().toString());
            room.setState("enable");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                hotel.setCreateDate(new Date());
            message.setData(roomRepository.save(room));
            message.setCode(CodeConstant.SUCCESS.getCode());
        } else {
            message.setCode(CodeConstant.RECORD_ALREADY_EXISTS.getCode());
        }
        return message;
    }

    @Transactional
    public RestMessage delete(String roomId) {
        RestMessage message = new RestMessage();
        Room sameRoom = roomRepository.findByRoomId(roomId);
        if (sameRoom == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        } else {
            roomRepository.deleteByRoomId(roomId);
            message.setCode(CodeConstant.SUCCESS.getCode());
        }
        return message;
    }

    public RestMessage edit(String roomId, Room room) {
        RestMessage message = new RestMessage();
        Room sameRoom = roomRepository.findByRoomId(roomId);
        if (sameRoom == null) {
            message.setCode(CodeConstant.RECORD_NOT_FOUND.getCode());
        } else {
            room.setId(sameRoom.getId());
            room.setRoomId(roomId);
            message.setData(roomRepository.save(room));
            message.setCode(CodeConstant.SUCCESS.getCode());
        }
        return message;
    }
}
