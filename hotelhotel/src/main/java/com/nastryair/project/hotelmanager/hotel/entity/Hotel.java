package com.nastryair.project.hotelmanager.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by D_Air on 2017/3/9.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String provinceId;
    private String cityId;
    private String districtId;
    private String province;
    private String city;
    private String district;
    private String address;
    private String longitude;
    private String latitude;
    private String starRated;
    private String price;
    private String theme;
    private String picture;
    private String priceRange;
    private String userId;
    private String status;

}
