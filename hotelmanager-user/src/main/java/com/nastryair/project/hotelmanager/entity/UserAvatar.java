package com.nastryair.project.hotelmanager.entity;

import javax.persistence.*;

/**
 * UserAvatar class
 *
 * @author QWL
 * @date 2018/7/30
 */
@Entity
public class UserAvatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 100000)
    private byte[] avatar;
    private String avatarFormat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarFormat() {
        return avatarFormat;
    }

    public void setAvatarFormat(String avatarFormat) {
        this.avatarFormat = avatarFormat;
    }
}
