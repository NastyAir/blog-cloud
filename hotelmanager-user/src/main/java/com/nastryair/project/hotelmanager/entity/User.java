package com.nastryair.project.hotelmanager.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user")
@Data
public class User {
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Id
    private String userId;
    private String name;
    private String nickName;
    private String password;
    private Date birthday;
    private String gender;
    private String email;
    private String phone;
    private String avatar;
    private String state;
    private String delFlag;
    private Date createDate;
    private Date updateDate;
    private String createBy;
    private String updateBy;
    private String description;
}
