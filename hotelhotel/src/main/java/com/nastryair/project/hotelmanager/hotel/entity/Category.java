package com.nastryair.project.hotelmanager.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by D_Air on 2016/12/7.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String categoryId;
    private String name;
    private Integer bedNum;
    private Double price;
    private Integer hasAC;
    private Integer hasTV;
    private Integer hasPC;
    private Integer hasWiFi;
    private Integer status;
    private String userId;
    private String picturePath;


}
