package com.nastryair.project.hotelmanager.respository;

import com.nastryair.project.hotelmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    //    User findByName(String userName);
    User findByUserId(String userId);

    @Query(value = "SELECT * FROM `user` WHERE id = :id", nativeQuery = true)
    User getById(@Param("id") Integer id);

    User findByName(String username);

    @Query("SELECT u FROM User u,UserRole ur WHERE u.userId=ur.userId AND ur.roleId=:roleId")
    List<User> findByRoleId(@Param("roleId") String roleId);

    @Query("SELECT u FROM User u,UserRole ur,Role r WHERE u.userId=ur.userId AND ur.roleId=r.roleId AND r.name=:name")
    List<User> getByRoleName(@Param("name") String name);


    @Modifying
    @Query("UPDATE User SET password=:password,updateTime=:updateTime WHERE userId=:userId")
    void updatePassword(
            @Param("password") String password,
            @Param("updateTime") String updateTime,
            @Param("userId") String userId);

//    User findByAccessId(String accessId);

    @Modifying
    @Query("UPDATE User SET enable=:enable,updateTime=:updateTime WHERE id=:id")
    void updateEnableByUserId(
            @Param("enable") String enable,
            @Param("updateTime") String updateTime,
            @Param("id") Integer id);

    Page findAll(Specification specification, Pageable pageable);
}
