package com.nastryair.project.hotelmanager.repository;

import com.nastryair.project.hotelmanager.entity.UserAvatar;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * UserAvatarRepository class
 *
 * @author QWL
 * @date 2018/7/30
 */
public interface UserAvatarRepository extends PagingAndSortingRepository<UserAvatar, Integer> {
    @Modifying
    @Query("UPDATE UserAvatar SET avatar=:avatar,avatarFormat=:avatarFormat WHERE userId=:userId")
    void updateAvatar(@Param("avatar") byte[] avatarBytes,
                      @Param("avatarFormat") String avatarFormat,
                      @Param("userId") String userId);

    @Query("SELECT avatar FROM UserAvatar WHERE userId=:userId")
    byte[] findAvatarByUserId(@Param("userId") String userId);

    UserAvatar findByUserId(String userId);

    int countByUserId(String userId);

    void deleteByUserId(String userId);
}
