package com.nastryair.project.hotelmanager.hotel.repository;

import com.nastryair.project.hotelmanager.hotel.entity.Room;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {

    Room findByRoomId(String roomId);

    Room findByName(String name);

    void deleteByRoomId(String roomId);

    <T> Page findAll(Example<T> of, Pageable pageable);
}
