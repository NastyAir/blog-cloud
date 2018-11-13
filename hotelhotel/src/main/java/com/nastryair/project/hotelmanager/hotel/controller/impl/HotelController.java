package com.nastryair.project.hotelmanager.hotel.controller.impl;

import com.nastryair.project.hotelmanager.hotel.common.RestMessage;
import com.nastryair.project.hotelmanager.hotel.controller.HotelApi;
import com.nastryair.project.hotelmanager.hotel.entity.Hotel;
import com.nastryair.project.hotelmanager.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/hotel")
public class HotelController implements HotelApi {
    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, value = "price") String priceRange,
            @RequestParam(required = false) String star,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false, defaultValue = "ASC") String order,
            @RequestParam(required = false, defaultValue = "id") String orderColumn,
            @RequestParam(required = false, defaultValue = "0") String currentPage,
            @RequestParam(required = false, defaultValue = "10") String pageSize) {
        RestMessage restMessage = hotelService.getList(title, priceRange, star, theme, order, orderColumn, currentPage, pageSize);
        return ResponseEntity.ok(restMessage);
    }

    @Override
    @RequestMapping(value = "/{hotelId}", method = RequestMethod.GET)
    public ResponseEntity getOne(@PathVariable String hotelId) {
        RestMessage restMessage = hotelService.getOne(hotelId);
        return ResponseEntity.ok(restMessage);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Hotel hotel) {
        RestMessage restMessage = hotelService.add(hotel);
        return ResponseEntity.ok(restMessage);
    }

}
