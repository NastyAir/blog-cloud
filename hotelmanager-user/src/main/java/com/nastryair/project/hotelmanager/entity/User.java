package com.nastryair.project.hotelmanager.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "user对象", description = "用户对象user")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String userId;
    private String name;
    private String nickname;
    private String password;
    private Date birthday;
    private String gender;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String state;
    private String delFlag;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;
    private String createBy;
    private String updateBy;
    private String description;
}
