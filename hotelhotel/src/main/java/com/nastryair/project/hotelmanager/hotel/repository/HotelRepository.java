package com.nastryair.project.hotelmanager.hotel.repository;

import com.nastryair.project.hotelmanager.hotel.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Integer> {
    Hotel findByHotelId(String hotelId);

    Hotel findByName(String name);

    Page findAll(Specification specification, Pageable pageable);
}
