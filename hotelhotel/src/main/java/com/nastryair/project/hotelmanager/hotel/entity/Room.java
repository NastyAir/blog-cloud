package com.nastryair.project.hotelmanager.hotel.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by D_Air on 2016/12/7.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Integer id;
    private String roomId;
    private String cid;
    private String name;
    private String position;
    private String uid;
    private String state;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;
    private String createBy;
    private String updateBy;
    private String description;
}
